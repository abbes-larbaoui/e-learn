package dz.kyrios.core.repository;

import dz.kyrios.core.entity.SessionStartingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionStartingTimeRepository extends JpaRepository<SessionStartingTime, Long>, JpaSpecificationExecutor<SessionStartingTime> {
}
