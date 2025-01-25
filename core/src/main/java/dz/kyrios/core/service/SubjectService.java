package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.config.filter.clause.Clause;
import dz.kyrios.core.config.filter.specification.GenericSpecification;
import dz.kyrios.core.dto.subject.SubjectRequest;
import dz.kyrios.core.dto.subject.SubjectResponse;
import dz.kyrios.core.entity.Field;
import dz.kyrios.core.entity.Subject;
import dz.kyrios.core.mapper.subject.SubjectMapper;
import dz.kyrios.core.repository.FieldRepository;
import dz.kyrios.core.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    private final FieldRepository fieldRepository;

    private final SubjectMapper subjectMapper;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, FieldRepository fieldRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.fieldRepository = fieldRepository;
        this.subjectMapper = subjectMapper;
    }

    public PageImpl<SubjectResponse> findAllFilter(PageRequest pageRequest, List<Clause> filter) {

        Specification<Subject> specification = new GenericSpecification<>(filter);
        List<SubjectResponse> subjectResponseList;
        Page<Subject> page;
        page = subjectRepository.findAll(specification, pageRequest);

        subjectResponseList = page.getContent().stream()
                .map(subjectMapper::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(subjectResponseList, pageRequest, page.getTotalElements());
    }


    public SubjectResponse getOne(Long id) {
        Subject entity = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Subject not found with id: "));
        return subjectMapper.entityToResponse(entity);
    }

    public SubjectResponse create(SubjectRequest request) {
        Subject created = subjectRepository.save(subjectMapper.requestToEntity(request));
        return subjectMapper.entityToResponse(created);
    }

    public SubjectResponse update(SubjectRequest request, Long id) {
        Subject entity = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Subject not found with id: "));
        Field field = fieldRepository.findById(request.fieldId())
                .orElseThrow(() -> new NotFoundException(request.fieldId(), "Field not found with id: "));
        entity.setName(request.name());
        entity.setDescription(request.name());
        entity.setField(field);
        entity.setStatus(request.status());

        return subjectMapper.entityToResponse(entity);
    }

    public void delete(Long id) {
        Subject entity = subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Subject not found with id: "));
        subjectRepository.delete(entity);
    }
}
