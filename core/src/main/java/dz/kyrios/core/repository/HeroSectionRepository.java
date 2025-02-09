package dz.kyrios.core.repository;

import dz.kyrios.core.entity.HeroSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeroSectionRepository extends JpaRepository<HeroSection, Long>, JpaSpecificationExecutor<HeroSection> {

    Optional<HeroSection> findByActive(Boolean active);
}
