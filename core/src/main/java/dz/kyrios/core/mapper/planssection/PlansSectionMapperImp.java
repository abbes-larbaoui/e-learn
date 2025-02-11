package dz.kyrios.core.mapper.planssection;

import dz.kyrios.core.dto.fieldssection.FieldResponse;
import dz.kyrios.core.dto.fieldssection.FieldsSectionResponse;
import dz.kyrios.core.dto.planssection.PlanResponse;
import dz.kyrios.core.dto.planssection.PlansSectionResponse;
import dz.kyrios.core.entity.FieldsSection;
import dz.kyrios.core.entity.PlansSection;
import dz.kyrios.core.mapper.field.FieldMapper;
import dz.kyrios.core.mapper.fieldssection.FieldsSectionMapper;
import dz.kyrios.core.mapper.subject.SubjectMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlansSectionMapperImp implements PlansSectionMapper {

    private final SubjectMapper subjectMapper;
    private final FieldMapper fieldMapper;

    public PlansSectionMapperImp(SubjectMapper subjectMapper, FieldMapper fieldMapper) {
        this.subjectMapper = subjectMapper;
        this.fieldMapper = fieldMapper;
    }

    @Override
    public PlansSectionResponse entityToResponse(PlansSection entity) {

        return new PlansSectionResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPlans().stream().map(plan ->
                    new PlanResponse(
                            plan.getId(),
                            plan.getSubject().getName(),
                            plan.getSubject().getField().getName(),
                            plan.getTeacher().getFirstName() + " " + plan.getTeacher().getLastName(),
                            plan.getMonths(),
                            plan.getSessionsPerWeek(),
                            plan.getPrice()
                            )
                ).collect(Collectors.toSet()),
                entity.getActive()
        );
    }
}