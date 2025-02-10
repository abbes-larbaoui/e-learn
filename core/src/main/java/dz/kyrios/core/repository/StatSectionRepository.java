package dz.kyrios.core.repository;

import dz.kyrios.core.entity.StatSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatSectionRepository extends JpaRepository<StatSection, Long>, JpaSpecificationExecutor<StatSection> {

    Optional<StatSection> findByActive(Boolean active);
}
