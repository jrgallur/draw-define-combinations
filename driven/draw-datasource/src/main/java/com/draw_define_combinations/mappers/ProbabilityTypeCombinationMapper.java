package com.draw_define_combinations.mappers;

import com.draw_define_combinations.models.ProbabilityTypeCombination;
import com.draw_define_combinations.models.ProbabilityTypeCombinationMO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProbabilityTypeCombinationWeightMapper.class})
public interface ProbabilityTypeCombinationMapper {
    ProbabilityTypeCombinationMO toModel(ProbabilityTypeCombination domain);

    ProbabilityTypeCombination toDomain(ProbabilityTypeCombinationMO mo);
}