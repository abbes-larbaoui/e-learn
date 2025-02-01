package dz.kyrios.core.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.OffsetTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "session_starting_time")
public class SessionStartingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "starting_time", nullable = false)
    private LocalTime startingTime;

    public SessionStartingTime(Long id) {
        this.id = id;
    }
}
