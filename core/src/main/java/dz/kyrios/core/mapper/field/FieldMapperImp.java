package dz.kyrios.core.mapper.field;

import dz.kyrios.core.dto.field.FieldRequest;
import dz.kyrios.core.dto.field.FieldResponse;
import dz.kyrios.core.entity.Field;
import org.springframework.stereotype.Component;

@Component
public class FieldMapperImp implements FieldMapper {
    @Override
    public Field requestToEntity(FieldRequest request) {
        return new Field(request.id(), request.name(), request.status());
    }

    @Override
    public FieldResponse entityToResponse(Field field) {
        return new FieldResponse(field.getId(), field.getName(), field.getStatus());
    }
}
