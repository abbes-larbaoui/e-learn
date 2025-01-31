package dz.kyrios.core.entity;

import dz.kyrios.core.statics.SubscriptionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_subscription")
public class StudentSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subscription_plan_id", referencedColumnName = "id", nullable = false)
    private SubscriptionPlan subscriptionPlan;

    @NotNull
    @Column(name = "subscription_creation_time", nullable = false)
    private LocalDateTime subscriptionCreationTime;

    // TODO: add sessions schedule (day/hour)

    @Column(name = "subscription_start_time")
    private LocalDateTime subscriptionStartTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL")
    private SubscriptionStatus status;
}
