package dz.kyrios.core.entity;

import dz.kyrios.core.statics.SubscriptionPlanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription_plan")
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    private Teacher teacher;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private Subject subject;

    @NotNull
    @Column(name = "months", nullable = false)
    private Integer months;

    @NotNull
    @Column(name = "sessions_per_week", nullable = false)
    private Integer sessionsPerWeek;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL")
    private SubscriptionPlanStatus status;


    public SubscriptionPlan(Long id, Subject subject, Integer months, Integer sessionsPerWeek, Double price, SubscriptionPlanStatus status) {
        this.id = id;
        this.subject = subject;
        this.months = months;
        this.sessionsPerWeek = sessionsPerWeek;
        this.price = price;
        this.status = status;
    }
}
