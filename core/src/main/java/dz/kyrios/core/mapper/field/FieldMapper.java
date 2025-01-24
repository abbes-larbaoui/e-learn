package dz.kyrios.core.mapper.field;

import dz.kyrios.core.dto.field.FieldRequest;
import dz.kyrios.core.dto.field.FieldResponse;
import dz.kyrios.core.entity.Field;

public interface FieldMapper {

    Field requestToEntity(FieldRequest request);

    FieldResponse entityToResponse(Field field);
}
