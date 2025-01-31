package dz.kyrios.core.entity;

import dz.kyrios.core.statics.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotBlank
    @Column(name = "currency", nullable = false)
    private String currency;

    @NotBlank
    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    @NotNull
    @OneToOne
    @JoinColumn(name = "student_subscription_id", referencedColumnName = "id", nullable = false)
    private StudentSubscription studentSubscription;

    @NotNull
    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL")
    private PaymentStatus status;
}
