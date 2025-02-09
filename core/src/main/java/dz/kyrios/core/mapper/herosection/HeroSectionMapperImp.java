package dz.kyrios.core.mapper.herosection;

import dz.kyrios.core.dto.herosection.HeroSectionResponse;
import dz.kyrios.core.entity.HeroSection;
import dz.kyrios.core.mapper.field.FieldMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HeroSectionMapperImp implements HeroSectionMapper {

    private final FieldMapper fieldMapper;

    public HeroSectionMapperImp(FieldMapper fieldMapper) {
        this.fieldMapper = fieldMapper;
    }

    @Override
    public HeroSectionResponse entityToResponse(HeroSection entity) {

        return new HeroSectionResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getFields().stream().map(fieldMapper::entityToResponse).collect(Collectors.toSet()),
                entity.getActive()
        );
    }
}
