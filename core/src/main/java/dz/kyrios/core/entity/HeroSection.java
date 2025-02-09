package dz.kyrios.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hero_section")
public class HeroSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "title", columnDefinition = "TEXT not null")
    private String title;

    @NotBlank
    @Column(name = "description", columnDefinition = "TEXT not null")
    private String description;

    @ManyToMany
    private Set<Field> fields;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;
}
