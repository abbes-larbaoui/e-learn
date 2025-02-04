package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.entity.SessionPlan;
import dz.kyrios.core.entity.SessionSchedule;
import dz.kyrios.core.entity.StudentSubscription;
import dz.kyrios.core.entity.SubscriptionPlan;
import dz.kyrios.core.repository.SessionScheduleRepository;
import dz.kyrios.core.repository.StudentSubscriptionRepository;
import dz.kyrios.core.statics.SessionScheduleStatus;
import jakarta.validation.ValidationException;
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

    public SessionScheduleService(StudentSubscriptionRepository studentSubscriptionRepository,
                                  SessionScheduleRepository sessionScheduleRepository) {
        this.studentSubscriptionRepository = studentSubscriptionRepository;
        this.sessionScheduleRepository = sessionScheduleRepository;
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
}
