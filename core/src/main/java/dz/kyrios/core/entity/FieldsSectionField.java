package dz.kyrios.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fields_section_field")
public class FieldsSectionField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "field_id", referencedColumnName = "id", nullable = false)
    private Field field;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fields_section_id", referencedColumnName = "id", nullable = false)
    private FieldsSection section;

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
        FieldsSectionField that = (FieldsSectionField) o;
        return Objects.equals(id, that.id); // Compare only by ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
