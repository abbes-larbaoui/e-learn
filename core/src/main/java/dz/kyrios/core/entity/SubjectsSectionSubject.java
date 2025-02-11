package dz.kyrios.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subjects_section_subject")
public class SubjectsSectionSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id", nullable = false)
    private Subject subject;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subjects_section_id", referencedColumnName = "id", nullable = false)
    private SubjectsSection section;

    @NotBlank
    @Column(name = "image", columnDefinition = "TEXT NOT NULL")
    private String image;

    @NotBlank
    @Column(name = "icon", columnDefinition = "TEXT NOT NULL")
    private String icon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectsSectionSubject that = (SubjectsSectionSubject) o;
        return Objects.equals(id, that.id); // Compare only by ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
