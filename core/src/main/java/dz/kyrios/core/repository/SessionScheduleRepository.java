package dz.kyrios.core.repository;

import dz.kyrios.core.entity.SessionSchedule;
import dz.kyrios.core.entity.Student;
import dz.kyrios.core.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SessionScheduleRepository extends JpaRepository<SessionSchedule, Long>, JpaSpecificationExecutor<SessionSchedule> {

    List<SessionSchedule> findByStudentSubscription_SubscriptionPlan_TeacherAndSessionDateBetween(Teacher teacher, LocalDate startDate, LocalDate endDate);

    List<SessionSchedule> findByStudentSubscription_StudentAndSessionDateBetween(Student student, LocalDate startDate, LocalDate endDate);
}
