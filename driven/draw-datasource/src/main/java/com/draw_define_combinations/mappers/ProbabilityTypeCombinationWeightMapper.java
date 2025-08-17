package com.draw_define_combinations.mappers;

import com.draw_define_combinations.models.ProbabilityTypeCombinationWeight;
import com.draw_define_combinations.models.ProbabilityTypeCombinationWeightMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProbabilityTypeCombinationWeightMapper {
    @Mapping(source = "probabilityTypeId", target = "probabilityType.id")
    @Mapping(source = "probabilityTypeCode", target = "probabilityType.code")
    ProbabilityTypeCombinationWeight toDomain(ProbabilityTypeCombinationWeightMO mo);

    @Mapping(source = "probabilityType.id", target = "probabilityTypeId")
    @Mapping(source = "probabilityType.code", target = "probabilityTypeCode")
    ProbabilityTypeCombinationWeightMO toModel(ProbabilityTypeCombinationWeight domain);
}