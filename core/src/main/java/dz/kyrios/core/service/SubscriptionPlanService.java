package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.BadRequestException;
import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.config.filter.clause.Clause;
import dz.kyrios.core.config.filter.specification.GenericSpecification;
import dz.kyrios.core.config.security.SecurityService;
import dz.kyrios.core.dto.subscriptionplan.SubscriptionPlanRequest;
import dz.kyrios.core.dto.subscriptionplan.SubscriptionPlanResponse;
import dz.kyrios.core.entity.Subject;
import dz.kyrios.core.entity.SubscriptionPlan;
import dz.kyrios.core.entity.Teacher;
import dz.kyrios.core.mapper.subscriptionplan.SubscriptionPlanMapper;
import dz.kyrios.core.repository.SubjectRepository;
import dz.kyrios.core.repository.SubscriptionPlanRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionPlanService {

    private final TeacherService teacherService;

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    private final SubjectRepository subjectRepository;

    private final SubscriptionPlanMapper subscriptionPlanMapper;

    @Autowired
    public SubscriptionPlanService(TeacherService teacherService,
                                   SubscriptionPlanRepository subscriptionPlanRepository,
                                   SubjectRepository subjectRepository,
                                   SubscriptionPlanMapper subscriptionPlanMapper) {
        this.teacherService = teacherService;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.subjectRepository = subjectRepository;
        this.subscriptionPlanMapper = subscriptionPlanMapper;
    }

    public PageImpl<SubscriptionPlanResponse> findAllFilter(PageRequest pageRequest, List<Clause> filter) {

        Specification<SubscriptionPlan> specification = new GenericSpecification<>(filter);
        List<SubscriptionPlanResponse> subjectResponseList;
        Page<SubscriptionPlan> page;
        page = subscriptionPlanRepository.findAll(specification, pageRequest);

        subjectResponseList = page.getContent().stream()
                .map(subscriptionPlanMapper::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(subjectResponseList, pageRequest, page.getTotalElements());
    }


    public SubscriptionPlanResponse getOne(Long id) {
        SubscriptionPlan entity = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Subscription Plan not found with id: "));
        return subscriptionPlanMapper.entityToResponse(entity);
    }

    public SubscriptionPlanResponse create(SubscriptionPlanRequest request) {

        Teacher teacher = teacherService.getTeacherFromCurrentProfile();
        if (teacher == null) {
            throw new ForbiddenException("The current user is not a Teacher");
        }
        if (teacher.getWorkingDays()== null || teacher.getWorkingDays().isEmpty()) {
            throw new BadRequestException("The teacher must have working days");
        }

        SubscriptionPlan entity = subscriptionPlanMapper.requestToEntity(request);
        entity.setTeacher(teacher);

        SubscriptionPlan created = subscriptionPlanRepository.save(entity);
        return subscriptionPlanMapper.entityToResponse(created);
    }

    @Transactional
    public SubscriptionPlanResponse update(SubscriptionPlanRequest request, Long id) {
        SubscriptionPlan entity = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Subscription Plan not found with id: "));
        Subject subject = subjectRepository.findById(request.subjectId())
                .orElseThrow(() -> new NotFoundException(request.subjectId(), "Subject not found with id: "));
        if (entity.getTeacher() != teacherService.getTeacherFromCurrentProfile()) {
            throw new ForbiddenException("You are not allowed to update this Subscription Plan");
        }
        entity.setSubject(subject);
        entity.setMonths(request.months());
        entity.setSessionsPerWeek(request.sessionsPerWeek());
        entity.setPrice(request.price());
        entity.setStatus(request.status());

        return subscriptionPlanMapper.entityToResponse(entity);
    }

    public void delete(Long id) {
        SubscriptionPlan entity = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Subscription Plan not found with id: "));
        if (entity.getTeacher() != teacherService.getTeacherFromCurrentProfile()) {
            throw new ForbiddenException("You are not allowed to delete this Subscription Plan");
        }
        subscriptionPlanRepository.delete(entity);
    }

    public Long getTotalPlans() {
        return subscriptionPlanRepository.count();
    }
}
