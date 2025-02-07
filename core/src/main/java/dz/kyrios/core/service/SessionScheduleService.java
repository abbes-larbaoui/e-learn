package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.sessionschedule.SessionScheduleResponse;
import dz.kyrios.core.entity.*;
import dz.kyrios.core.mapper.sessionschedule.SessionScheduleMapper;
import dz.kyrios.core.repository.SessionScheduleRepository;
import dz.kyrios.core.service.meeting.MeetingService;
import dz.kyrios.core.statics.SessionScheduleStatus;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class SessionScheduleService {

    private final SessionScheduleRepository sessionScheduleRepository;
    private final StudentSubscriptionService studentSubscriptionService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final MeetingService meetingService;
    private final SessionScheduleMapper sessionScheduleMapper;

    @Autowired
    public SessionScheduleService(SessionScheduleRepository sessionScheduleRepository,
                                  StudentSubscriptionService studentSubscriptionService,
                                  TeacherService teacherService,
                                  StudentService studentService,
                                  MeetingService meetingService,
                                  SessionScheduleMapper sessionScheduleMapper) {
        this.sessionScheduleRepository = sessionScheduleRepository;
        this.studentSubscriptionService = studentSubscriptionService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.meetingService = meetingService;
        this.sessionScheduleMapper = sessionScheduleMapper;
    }

    @Transactional
    public void createSessionsForSubscription(Long subscriptionId) {
        StudentSubscription subscription = studentSubscriptionService.getById(subscriptionId);

        SubscriptionPlan plan = subscription.getSubscriptionPlan();

        // validate that plan sessions number and subscription sessions number are equal
        if (plan.getSessionsPerWeek() != subscription.getSessionPlans().size())
            throw new ValidationException("Sessions number selected is not equal to plan session per week number");

        Set<SessionPlan> sessionPlans = subscription.getSessionPlans();

        LocalDate startDate = LocalDate.now();  // Adjust if needed
        int totalWeeks = plan.getMonths() * 4;  // Approximate total weeks

        List<SessionSchedule> sessions = new ArrayList<>();

        for (int week = 0; week < totalWeeks; week++) {
            for (SessionPlan sessionPlan : sessionPlans) {
                LocalDate sessionDate = startDate.with(TemporalAdjusters.nextOrSame(sessionPlan.getSessionDay()));
                sessionDate = sessionDate.plusWeeks(week);

                SessionSchedule session = new SessionSchedule();
                session.setStudentSubscription(subscription);
                session.setSessionDate(sessionDate);
                session.setStartingTime(sessionPlan.getStartingTime().getStartingTime());
                session.setStatus(SessionScheduleStatus.SCHEDULED);

                // Merge LocalDate and LocalTime into LocalDateTime
                LocalDateTime sessionDateTime = LocalDateTime.of(session.getSessionDate(), session.getStartingTime());

                // Convert LocalDateTime to UTC formatted string
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String utcTime = sessionDateTime.atZone(TimeZone.getDefault().toZoneId())
                        .withZoneSameInstant(ZoneOffset.UTC)
                        .toLocalDateTime()
                        .format(formatter);
                String topic = subscription.getSubscriptionPlan().getSubject().getName() + " by E-learning";

                // Generate Zoom meeting link
                String zoomMeetingUrl = meetingService.createMeeting(topic, utcTime, 60);

                session.setMeetingUrl(zoomMeetingUrl);


                sessions.add(session);
            }
        }

        sessionScheduleRepository.saveAll(sessions);
    }

    public List<SessionScheduleResponse> getTeacherScheduleForMonth(int year, int month) {
        Teacher teacher = teacherService.getTeacherFromCurrentProfile();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return sessionScheduleRepository.findByStudentSubscription_SubscriptionPlan_TeacherAndSessionDateBetween(teacher, startDate, endDate)
                .stream().map(sessionScheduleMapper::entityToResponse).toList();
    }

    public List<SessionScheduleResponse> getStudentScheduleForMonth(int year, int month) {
        Student student = studentService.getStudentFromCurrentProfile();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return sessionScheduleRepository.findByStudentSubscription_StudentAndSessionDateBetween(student, startDate, endDate)
                .stream().map(sessionScheduleMapper::entityToResponse).toList();
    }

    public SessionScheduleResponse changeStatusSessionSchedule(Long sessionScheduleId, SessionScheduleStatus status) {
        SessionSchedule sessionSchedule = sessionScheduleRepository.findById(sessionScheduleId)
                .orElseThrow(() -> new NotFoundException(sessionScheduleId, "Session Schedule not found with id: "));

        if (sessionSchedule.getStudentSubscription().getSubscriptionPlan().getTeacher() != teacherService.getTeacherFromCurrentProfile()) {
            throw new ForbiddenException("You are not allowed to update this Session Schedule");
        }

        // Calculate session end time and check if the session has ended
        LocalDateTime sessionEndTime = LocalDateTime.of(sessionSchedule.getSessionDate(), sessionSchedule.getStartingTime())
                .plusHours(1);

        if (LocalDateTime.now().isBefore(sessionEndTime)) {
            throw new IllegalStateException("Session has not ended yet");
        }
        sessionSchedule.setStatus(status);

        if (SessionScheduleStatus.CANCELED.equals(status)) {
            createNewSessionAfterLast(sessionSchedule.getStudentSubscription());
        }

        return sessionScheduleMapper.entityToResponse(sessionScheduleRepository.save(sessionSchedule));
    }

    public SessionScheduleResponse completeSessionSchedule(Long sessionScheduleId) {
        SessionSchedule sessionSchedule = sessionScheduleRepository.findById(sessionScheduleId)
                .orElseThrow(() -> new NotFoundException(sessionScheduleId, "Session Schedule not found with id: "));

        if (sessionSchedule.getStudentSubscription().getSubscriptionPlan().getTeacher() != teacherService.getTeacherFromCurrentProfile()) {
            throw new ForbiddenException("You are not allowed to update this Session Schedule");
        }

        // Calculate session end time and check if the session has ended
        LocalDateTime sessionEndTime = LocalDateTime.of(sessionSchedule.getSessionDate(), sessionSchedule.getStartingTime())
                .plusHours(1);

        if (LocalDateTime.now().isBefore(sessionEndTime)) {
            throw new IllegalStateException("Session has not ended yet");
        }
        sessionSchedule.setStatus(SessionScheduleStatus.COMPLETED);

        if (Objects.equals(sessionSchedule.getId(), getLastSessionScheduleForSubscription(sessionSchedule.getStudentSubscription()).getId())) {
            studentSubscriptionService.expireStudentSubscription(sessionSchedule.getStudentSubscription());
        }

        return sessionScheduleMapper.entityToResponse(sessionScheduleRepository.save(sessionSchedule));
    }

    public SessionScheduleResponse missSessionSchedule(Long sessionScheduleId) {
        SessionSchedule sessionSchedule = sessionScheduleRepository.findById(sessionScheduleId)
                .orElseThrow(() -> new NotFoundException(sessionScheduleId, "Session Schedule not found with id: "));

        if (sessionSchedule.getStudentSubscription().getSubscriptionPlan().getTeacher() != teacherService.getTeacherFromCurrentProfile()) {
            throw new ForbiddenException("You are not allowed to update this Session Schedule");
        }

        // Calculate session end time and check if the session has ended
        LocalDateTime sessionEndTime = LocalDateTime.of(sessionSchedule.getSessionDate(), sessionSchedule.getStartingTime())
                .plusHours(1);

        if (LocalDateTime.now().isBefore(sessionEndTime)) {
            throw new IllegalStateException("Session has not ended yet");
        }
        sessionSchedule.setStatus(SessionScheduleStatus.MISSED);
        if (Objects.equals(sessionSchedule.getId(), getLastSessionScheduleForSubscription(sessionSchedule.getStudentSubscription()).getId())) {
            studentSubscriptionService.expireStudentSubscription(sessionSchedule.getStudentSubscription());
        }

        return sessionScheduleMapper.entityToResponse(sessionScheduleRepository.save(sessionSchedule));
    }

    public SessionScheduleResponse cancelSessionSchedule(Long sessionScheduleId) {
        SessionSchedule sessionSchedule = sessionScheduleRepository.findById(sessionScheduleId)
                .orElseThrow(() -> new NotFoundException(sessionScheduleId, "Session Schedule not found with id: "));

        if (sessionSchedule.getStudentSubscription().getSubscriptionPlan().getTeacher() != teacherService.getTeacherFromCurrentProfile()) {
            throw new ForbiddenException("You are not allowed to update this Session Schedule");
        }

        // Calculate session end time and check if the session has ended
        LocalDateTime sessionEndTime = LocalDateTime.of(sessionSchedule.getSessionDate(), sessionSchedule.getStartingTime())
                .plusHours(1);

        if (LocalDateTime.now().isBefore(sessionEndTime)) {
            throw new IllegalStateException("Session has not ended yet");
        }
        sessionSchedule.setStatus(SessionScheduleStatus.CANCELED);
        createNewSessionAfterLast(sessionSchedule.getStudentSubscription());

        return sessionScheduleMapper.entityToResponse(sessionScheduleRepository.save(sessionSchedule));
    }

    public SessionSchedule getLastSessionScheduleForSubscription(StudentSubscription subscription) {
        return sessionScheduleRepository.findByStudentSubscription(subscription)
                .stream()
                .max(Comparator
                        .comparing(session -> LocalDateTime.of(session.getSessionDate(), session.getStartingTime())))
                .orElseThrow(() -> new NotFoundException("Last session not found"));
    }

    public void createNewSessionAfterLast(StudentSubscription subscription) {

        // Get last session for this subscription
        SessionSchedule lastSessionOpt = getLastSessionScheduleForSubscription(subscription);

        if (lastSessionOpt == null) {
            throw new IllegalStateException("No valid sessions found for rescheduling.");
        }

        // Get session plans from student subscription
        Set<SessionPlan> sessionPlans = subscription.getSessionPlans();

        // Sort session plans by day of the week (Sunday → Monday → Wednesday)
        List<SessionPlan> sortedPlans = sessionPlans.stream()
                .sorted(Comparator.comparingInt(plan -> plan.getSessionDay().getValue()))
                .toList();

        // Find the next session plan after the last session date
        SessionPlan nextSessionPlan = null;
        for (SessionPlan plan : sortedPlans) {
            if (plan.getSessionDay().getValue() > lastSessionOpt.getSessionDate().getDayOfWeek().getValue()) {
                nextSessionPlan = plan;
                break;
            }
        }

        // If no next session was found, loop back to the first session of the next week
        if (nextSessionPlan == null) {
            nextSessionPlan = sortedPlans.get(0);
        }

        // Compute the next session date
        LocalDate nextSessionDate = lastSessionOpt.getSessionDate().with(TemporalAdjusters.next(nextSessionPlan.getSessionDay()));

        // Create the new session
        SessionSchedule newSession = new SessionSchedule();
        newSession.setStudentSubscription(subscription);
        newSession.setSessionDate(nextSessionDate);
        newSession.setStartingTime(nextSessionPlan.getStartingTime().getStartingTime());
        newSession.setStatus(SessionScheduleStatus.RESCHEDULED);

        // Merge LocalDate and LocalTime into LocalDateTime
        LocalDateTime sessionDateTime = LocalDateTime.of(newSession.getSessionDate(), newSession.getStartingTime());

        // Convert LocalDateTime to UTC formatted string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String utcTime = sessionDateTime.atZone(TimeZone.getDefault().toZoneId())
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime()
                .format(formatter);

        String topic = subscription.getSubscriptionPlan().getSubject().getName() + " by E-learning";

        // Generate Zoom meeting link
        String zoomMeetingUrl = meetingService.createMeeting(topic, utcTime, 60);
        newSession.setMeetingUrl(zoomMeetingUrl);

        sessionScheduleRepository.save(newSession);
    }
}
