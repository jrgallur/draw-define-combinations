package com.draw_define_combinations.mappers;

import com.draw_define_combinations.domain.ProbabilityTypeCombinationMO;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationWeight;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationWeightMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProbabilityTypeCombinationWeightMapper {
    @Mapping(source = "probabilityTypeId", target = "probabilityType.id")
    @Mapping(source = "probabilityTypeCode", target = "probabilityType.code")
    @Mapping(source = "probabilityTypeCombination.id", target = "probabilityTypeCombinationId")
    ProbabilityTypeCombinationWeight toDomain(ProbabilityTypeCombinationWeightMO mo);

    @Mapping(source = "probabilityType.id", target = "probabilityTypeId")
    @Mapping(source = "probabilityType.code", target = "probabilityTypeCode")
    @Mapping(source = "probabilityTypeCombinationId", target = "probabilityTypeCombination", qualifiedByName = "mapToCombinationMO")
    ProbabilityTypeCombinationWeightMO toModel(ProbabilityTypeCombinationWeight domain);

    @Named("mapToCombinationMO")
    default ProbabilityTypeCombinationMO mapToCombinationMO(Integer id) {
        if (id == null) {
            return null;
        }
        ProbabilityTypeCombinationMO mo = new ProbabilityTypeCombinationMO();
        mo.setId(id);
        return mo;
    }
}