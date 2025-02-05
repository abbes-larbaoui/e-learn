package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.sessionschedule.SessionScheduleResponse;
import dz.kyrios.core.entity.*;
import dz.kyrios.core.mapper.sessionschedule.SessionScheduleMapper;
import dz.kyrios.core.repository.SessionScheduleRepository;
import dz.kyrios.core.repository.StudentSubscriptionRepository;
import dz.kyrios.core.statics.SessionScheduleStatus;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SessionScheduleService {

    private final StudentSubscriptionRepository studentSubscriptionRepository;
    private final SessionScheduleRepository sessionScheduleRepository;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final SessionScheduleMapper sessionScheduleMapper;

    @Autowired
    public SessionScheduleService(StudentSubscriptionRepository studentSubscriptionRepository,
                                  SessionScheduleRepository sessionScheduleRepository,
                                  TeacherService teacherService,
                                  StudentService studentService,
                                  SessionScheduleMapper sessionScheduleMapper) {
        this.studentSubscriptionRepository = studentSubscriptionRepository;
        this.sessionScheduleRepository = sessionScheduleRepository;
        this.teacherService = teacherService;
        this.studentService = studentService;
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
