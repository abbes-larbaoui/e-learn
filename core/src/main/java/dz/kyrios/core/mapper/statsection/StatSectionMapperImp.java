package dz.kyrios.core.mapper.statsection;

import dz.kyrios.core.dto.statsection.StatSectionResponse;
import dz.kyrios.core.entity.StatSection;
import dz.kyrios.core.service.StudentService;
import dz.kyrios.core.service.SubscriptionPlanService;
import dz.kyrios.core.service.TeacherService;
import org.springframework.stereotype.Component;

@Component
public class StatSectionMapperImp implements StatSectionMapper {

    private final StudentService studentService;
    private final TeacherService teacherService;
    private final SubscriptionPlanService subscriptionPlanService;

    public StatSectionMapperImp(StudentService studentService,
                                TeacherService teacherService,
                                SubscriptionPlanService subscriptionPlanService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.subscriptionPlanService = subscriptionPlanService;
    }

    @Override
    public StatSectionResponse entityToResponse(StatSection entity) {

        return new StatSectionResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                studentService.getTotalStudents(),
                teacherService.getTotalTeachers(),
                subscriptionPlanService.getTotalPlans(),
                entity.getSupport()
        );
    }
}
