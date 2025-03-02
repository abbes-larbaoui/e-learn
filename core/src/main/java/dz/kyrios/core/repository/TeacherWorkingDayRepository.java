package dz.kyrios.core.repository;

import dz.kyrios.core.entity.TeacherWorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherWorkingDayRepository extends JpaRepository<TeacherWorkingDay, Long>, JpaSpecificationExecutor<TeacherWorkingDay> {
}
