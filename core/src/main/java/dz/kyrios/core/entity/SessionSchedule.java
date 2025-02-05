package dz.kyrios.core.entity;

import dz.kyrios.core.statics.ScheduleStatus;
import dz.kyrios.core.statics.SessionScheduleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "session_schedule")
public class SessionSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "student_subscription_id", referencedColumnName = "id", nullable = false)
    private StudentSubscription studentSubscription;

    @NotNull
    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @NotNull
    @Column(name = "starting_time", nullable = false)
    private LocalTime startingTime;

    @NotBlank
    @Column(name = "meeting_url", nullable = false)
    private String meetingUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(20) DEFAULT 'SCHEDULED' NOT NULL")
    private SessionScheduleStatus status;
}
