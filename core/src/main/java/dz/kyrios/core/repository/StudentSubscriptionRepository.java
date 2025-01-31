package dz.kyrios.core.repository;

import dz.kyrios.core.entity.StudentSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentSubscriptionRepository extends JpaRepository<StudentSubscription, Long>, JpaSpecificationExecutor<StudentSubscription> {
}
