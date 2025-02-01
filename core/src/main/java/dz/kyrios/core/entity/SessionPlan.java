package dz.kyrios.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.OffsetTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "session_plan")
public class SessionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "session_day", nullable = false)
    private DayOfWeek sessionDay;

    @NotNull
    @Column(name = "session_start_time", nullable = false)
    private OffsetTime sessionStartTime;
}
