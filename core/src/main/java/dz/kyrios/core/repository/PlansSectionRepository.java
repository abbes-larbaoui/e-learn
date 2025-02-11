package dz.kyrios.core.repository;

import dz.kyrios.core.entity.PlansSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlansSectionRepository extends JpaRepository<PlansSection, Long>, JpaSpecificationExecutor<PlansSection> {

    Optional<PlansSection> findOneByActive(Boolean active);
}
