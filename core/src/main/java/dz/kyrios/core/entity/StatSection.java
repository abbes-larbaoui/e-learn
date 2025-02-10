package dz.kyrios.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stat_section")
public class StatSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "title", columnDefinition = "TEXT not null")
    private String title;

    @NotBlank
    @Column(name = "description", columnDefinition = "TEXT not null")
    private String description;

    @NotBlank
    @Column(name = "support", columnDefinition = "TEXT not null")
    private String support;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;
}
