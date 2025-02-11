package dz.kyrios.core.repository;

import dz.kyrios.core.entity.SubjectsSection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectsSectionRepository extends JpaRepository<SubjectsSection, Long>, JpaSpecificationExecutor<SubjectsSection> {

    Optional<SubjectsSection> findOneByActive(Boolean active);
}
