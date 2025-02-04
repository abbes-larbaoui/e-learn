package dz.kyrios.core.repository;

import dz.kyrios.core.entity.SessionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionScheduleRepository extends JpaRepository<SessionSchedule, Long>, JpaSpecificationExecutor<SessionSchedule> {
}
