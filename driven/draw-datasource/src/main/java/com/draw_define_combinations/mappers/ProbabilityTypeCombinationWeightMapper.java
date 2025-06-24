package com.draw_define_combinations.mappers;

import com.draw_define_combinations.models.ProbabilityTypeCombinationWeight;
import com.draw_define_combinations.models.ProbabilityTypeCombinationWeightMO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProbabilityTypeMapper.class})
public interface ProbabilityTypeCombinationWeightMapper {
    ProbabilityTypeCombinationWeight toDomain(ProbabilityTypeCombinationWeightMO mo);
}