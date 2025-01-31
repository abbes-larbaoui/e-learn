package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.BadRequestException;
import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.entity.Payment;
import dz.kyrios.core.entity.Student;
import dz.kyrios.core.entity.StudentSubscription;
import dz.kyrios.core.entity.SubscriptionPlan;
import dz.kyrios.core.repository.PaymentRepository;
import dz.kyrios.core.repository.StudentSubscriptionRepository;
import dz.kyrios.core.repository.SubscriptionPlanRepository;
import dz.kyrios.core.service.payment.PaymentService;
import dz.kyrios.core.statics.PaymentStatus;
import dz.kyrios.core.statics.SubscriptionPlanStatus;
import dz.kyrios.core.statics.SubscriptionStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class StudentSubscriptionService {

    private final PaymentService paymentService;

    private final StudentSubscriptionRepository studentSubscriptionRepository;

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    private final PaymentRepository paymentRepository;

    private final StudentService studentService;

    public StudentSubscriptionService(PaymentService paymentService,
                                      StudentSubscriptionRepository studentSubscriptionRepository,
                                      SubscriptionPlanRepository subscriptionPlanRepository,
                                      PaymentRepository paymentRepository,
                                      StudentService studentService) {
        this.paymentService = paymentService;
        this.studentSubscriptionRepository = studentSubscriptionRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.paymentRepository = paymentRepository;
        this.studentService = studentService;
    }

    @Transactional
    public String subscribeStudentToPlan(Long subscriptionPlanId) {

        /* fetch the selected subscription plan from the db */
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new NotFoundException(subscriptionPlanId, "Subscription Plan not found with id :"));

        /* check that the status of subscriptionPlan is PUBLISHED */
        if (!SubscriptionPlanStatus.PUBLISHED.equals(subscriptionPlan.getStatus())) {
            throw new BadRequestException("Subscription plan is not published");
        }

        /* get the connected student */
        Student student = studentService.getStudentFromCurrentProfile();

        /* Create new student subscription */
        StudentSubscription studentSubscription = new StudentSubscription();
        studentSubscription.setStudent(student);
        studentSubscription.setSubscriptionPlan(subscriptionPlan);
        studentSubscription.setSubscriptionCreationTime(LocalDateTime.now());
        studentSubscription.setStatus(SubscriptionStatus.PENDING);

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
}
