package dz.kyrios.core.mapper.subscriptionplan;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.dto.planssection.PlanResponse;
import dz.kyrios.core.dto.subject.SubjectResponse;
import dz.kyrios.core.dto.subscriptionplan.SubscriptionPlanRequest;
import dz.kyrios.core.dto.subscriptionplan.SubscriptionPlanResponse;
import dz.kyrios.core.dto.teacher.TeacherResponseForSubscriptionPlan;
import dz.kyrios.core.entity.Subject;
import dz.kyrios.core.entity.SubscriptionPlan;
import dz.kyrios.core.entity.Teacher;
import dz.kyrios.core.mapper.subject.SubjectMapper;
import dz.kyrios.core.repository.SubjectRepository;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPlanMapperImp implements SubscriptionPlanMapper {

    private final SubjectRepository subjectRepository;

    private final SubjectMapper subjectMapper;

    public SubscriptionPlanMapperImp(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public SubscriptionPlan requestToEntity(SubscriptionPlanRequest request) {
        Subject subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new NotFoundException(request.subjectId(), "Subject not found with id: "));

        return new SubscriptionPlan(request.id(),
                subject,
                request.months(),
                request.sessionsPerWeek(),
                request.price(),
                request.status());
    }

    @Override
    public SubscriptionPlanResponse entityToResponse(SubscriptionPlan subscriptionPlan) {
        TeacherResponseForSubscriptionPlan teacher =
                new TeacherResponseForSubscriptionPlan(subscriptionPlan.getTeacher().getId(),
                        subscriptionPlan.getTeacher().getFirstName(),
                        subscriptionPlan.getTeacher().getLastName(),
                        subscriptionPlan.getTeacher().getBio(),
                        subscriptionPlan.getTeacher().getStatus());

        SubjectResponse subject = subjectMapper.entityToResponse(subscriptionPlan.getSubject());

        return new SubscriptionPlanResponse(subscriptionPlan.getId(),
                teacher,
                subject,
                subscriptionPlan.getMonths(),
                subscriptionPlan.getSessionsPerWeek(),
                subscriptionPlan.getPrice(),
                subscriptionPlan.getStatus());
    }

    @Override
    public PlanResponse entityToPlanResponse(SubscriptionPlan entity) {

        return new PlanResponse(
                entity.getId(),
                entity.getSubject().getName(),
                entity.getSubject().getField().getName(),
                entity.getTeacher().getFirstName() + " " + entity.getTeacher().getLastName(),
                entity.getMonths(),
                entity.getSessionsPerWeek(),
                entity.getPrice());
    }
}
