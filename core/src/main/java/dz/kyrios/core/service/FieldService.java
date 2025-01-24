package dz.kyrios.core.service;

import dz.kyrios.core.config.exception.NotFoundException;
import dz.kyrios.core.config.filter.clause.Clause;
import dz.kyrios.core.config.filter.specification.GenericSpecification;
import dz.kyrios.core.dto.field.FieldRequest;
import dz.kyrios.core.dto.field.FieldResponse;
import dz.kyrios.core.entity.Field;
import dz.kyrios.core.mapper.field.FieldMapper;
import dz.kyrios.core.repository.FieldRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;

    private final FieldMapper fieldMapper;

    public FieldService(FieldRepository fieldRepository, FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.fieldMapper = fieldMapper;
    }

    public PageImpl<FieldResponse> findAllFilter(PageRequest pageRequest, List<Clause> filter) {

        Specification<Field> specification = new GenericSpecification<>(filter);
        List<FieldResponse> fieldResponseList;
        Page<Field> page;
        page = fieldRepository.findAll(specification, pageRequest);

        fieldResponseList = page.getContent().stream()
                .map(fieldMapper::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(fieldResponseList, pageRequest, page.getTotalElements());
    }


    public FieldResponse getOne(Long id) {
        Field entity = fieldRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Field not found with id: "));
        return fieldMapper.entityToResponse(entity);
    }

    public FieldResponse create(FieldRequest request) {
        Field created = fieldRepository.save(fieldMapper.requestToEntity(request));
        return fieldMapper.entityToResponse(created);
    }

    public FieldResponse update(FieldRequest request, Long id) {
        Field entity = fieldRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Field not found with id: "));
        entity.setName(request.name());
        entity.setStatus(request.status());

        return fieldMapper.entityToResponse(entity);
    }

    public void delete(Long id) {
        Field entity = fieldRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Field not found with id: "));
        fieldRepository.delete(entity);
    }
}
