package dz.kyrios.core.repository;

import dz.kyrios.core.entity.FieldsSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldsSectionRepository extends JpaRepository<FieldsSection, Long>, JpaSpecificationExecutor<FieldsSection> {

    Optional<FieldsSection> findOneByActive(Boolean active);
}
