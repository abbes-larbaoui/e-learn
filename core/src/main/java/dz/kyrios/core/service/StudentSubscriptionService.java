package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.BadRequestException;
import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.entity.*;
import dz.kyrios.core.repository.PaymentRepository;
import dz.kyrios.core.repository.SessionStartingTimeRepository;
import dz.kyrios.core.repository.StudentSubscriptionRepository;
import dz.kyrios.core.repository.SubscriptionPlanRepository;
import dz.kyrios.core.service.payment.PaymentService;
import dz.kyrios.core.statics.PaymentStatus;
import dz.kyrios.core.statics.SubscriptionPlanStatus;
import dz.kyrios.core.statics.SubscriptionStatus;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Service
public class StudentSubscriptionService {

    private final PaymentService paymentService;
    private final StudentSubscriptionRepository studentSubscriptionRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final PaymentRepository paymentRepository;
    private final SessionStartingTimeRepository sessionStartingTimeRepository;
    private final StudentService studentService;

    public StudentSubscriptionService(PaymentService paymentService,
                                      StudentSubscriptionRepository studentSubscriptionRepository,
                                      SubscriptionPlanRepository subscriptionPlanRepository,
                                      PaymentRepository paymentRepository,
                                      SessionStartingTimeRepository sessionStartingTimeRepository,
                                      StudentService studentService) {
        this.paymentService = paymentService;
        this.studentSubscriptionRepository = studentSubscriptionRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.paymentRepository = paymentRepository;
        this.sessionStartingTimeRepository = sessionStartingTimeRepository;
        this.studentService = studentService;
    }

    @Transactional
    public String subscribeStudentToPlan(Long subscriptionPlanId, Set<SessionPlan> sessionPlans) {

        // Fetch the selected subscription plan from the database
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new NotFoundException(subscriptionPlanId, "Subscription Plan not found with id:"));

        // Validate that the plan is published
        if (!SubscriptionPlanStatus.PUBLISHED.equals(subscriptionPlan.getStatus())) {
            throw new BadRequestException("Subscription plan is not published");
        }

        // Get the connected student
        Student student = studentService.getStudentFromCurrentProfile();

        // Validate the session plans before proceeding
        validateSubscriptionRequest(subscriptionPlan, sessionPlans);

        // Create a new student subscription
        StudentSubscription studentSubscription = new StudentSubscription();
        studentSubscription.setStudent(student);
        studentSubscription.setSubscriptionPlan(subscriptionPlan);
        studentSubscription.setSubscriptionCreationTime(LocalDateTime.now());
        studentSubscription.setStatus(SubscriptionStatus.PENDING);
        studentSubscription.setSessionPlans(sessionPlans); // Assign validated session plans

        StudentSubscription saved = studentSubscriptionRepository.save(studentSubscription);

        // Redirect user to payment provider
        return paymentService.createCheckoutSession(saved.getId(), "USD", subscriptionPlan.getPrice());
    }

    @Transactional
    public void updateSubscriptionStatus(Long subscriptionId, Payment payment) {

        StudentSubscription studentSubscription = studentSubscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException(subscriptionId, "Subscription not found with id: "));

        payment.setStudentSubscription(studentSubscription);

        if (PaymentStatus.SUCCESS.equals(payment.getStatus())) {
            studentSubscription.setSubscriptionStartTime(LocalDateTime.now());
            studentSubscription.setStatus(SubscriptionStatus.ACTIVE);
        } else {
            studentSubscription.setStatus(SubscriptionStatus.PENDING);
        }

        paymentRepository.save(payment);
        studentSubscriptionRepository.save(studentSubscription);
    }

    private void validateSubscriptionRequest(SubscriptionPlan plan, Set<SessionPlan> sessionPlans) {
        Teacher teacher = plan.getTeacher();
        List<TeacherWorkingDay> teacherWorkingDays = teacher.getWorkingDays();

        // Validate sessions number selected
        if (plan.getSessionsPerWeek() != sessionPlans.size()) {
            throw new ValidationException("Sessions number selected is not equal to plan session per week number");
        }

        for (SessionPlan sessionPlan : sessionPlans) {
            DayOfWeek requestedDay = sessionPlan.getSessionDay();
            Long startingTimeId = sessionPlan.getStartingTime().getId(); // Get the ID

            // Retrieve the actual SessionStartingTime entity
            SessionStartingTime sessionStartingTime = sessionStartingTimeRepository.findById(startingTimeId)
                    .orElseThrow(() -> new ValidationException("Invalid session starting time ID: " + startingTimeId));

            // Validate teacher's working day
            TeacherWorkingDay workingDay = teacherWorkingDays.stream()
                    .filter(wd -> wd.getDayOfWeek().equals(requestedDay))
                    .findFirst()
                    .orElseThrow(() -> new ValidationException("Teacher does not work on " + requestedDay));

            // Validate teacher's session time
            boolean sessionTimeExists = workingDay.getSessionStartingTimes().stream()
                    .anyMatch(time -> time.getId().equals(startingTimeId));

            if (!sessionTimeExists) {
                throw new ValidationException("Teacher is not available at " + sessionStartingTime.getStartingTime() +
                        " on " + requestedDay);
            }

            // Validate no active student subscription for the same plan, day, and time
            boolean isSessionBooked = studentSubscriptionRepository.existsBySubscriptionPlanAndStatusAndSessionPlans_SessionDayAndSessionPlans_StartingTime(
                    plan, SubscriptionStatus.ACTIVE, requestedDay, sessionStartingTime);

            if (isSessionBooked) {
                throw new ValidationException("Session at " + sessionStartingTime.getStartingTime() + " on " +
                        requestedDay + " is already booked by another student.");
            }
        }
    }
}
