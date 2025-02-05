package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.sessionschedule.SessionScheduleResponse;
import dz.kyrios.core.entity.*;
import dz.kyrios.core.mapper.sessionschedule.SessionScheduleMapper;
import dz.kyrios.core.repository.SessionScheduleRepository;
import dz.kyrios.core.repository.StudentSubscriptionRepository;
import dz.kyrios.core.service.meeting.MeetingService;
import dz.kyrios.core.statics.SessionScheduleStatus;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

@Service
public class SessionScheduleService {

    private final StudentSubscriptionRepository studentSubscriptionRepository;
    private final SessionScheduleRepository sessionScheduleRepository;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final MeetingService meetingService;
    private final SessionScheduleMapper sessionScheduleMapper;

    @Autowired
    public SessionScheduleService(StudentSubscriptionRepository studentSubscriptionRepository,
                                  SessionScheduleRepository sessionScheduleRepository,
                                  TeacherService teacherService,
                                  StudentService studentService,
                                  MeetingService meetingService,
                                  SessionScheduleMapper sessionScheduleMapper) {
        this.studentSubscriptionRepository = studentSubscriptionRepository;
        this.sessionScheduleRepository = sessionScheduleRepository;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.meetingService = meetingService;
        this.sessionScheduleMapper = sessionScheduleMapper;
    }

    @Transactional
    public void createSessionsForSubscription(Long subscriptionId) {
        StudentSubscription subscription = studentSubscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException(subscriptionId, "Subscription not found with id: "));

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
}
