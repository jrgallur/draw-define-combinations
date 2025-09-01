package com.draw_define_combinations.mappers;

import com.draw_define_combinations.domain.ProbabilityTypeCombination;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationMO;
import com.draw_define_combinations.domain.projections.ProbabilityTypeCombinationProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProbabilityTypeCombinationWeightMapper.class})
public interface ProbabilityTypeCombinationMapper {
    ProbabilityTypeCombinationMO toModel(ProbabilityTypeCombination domain);

    ProbabilityTypeCombination toDomain(ProbabilityTypeCombinationMO mo);

    ProbabilityTypeCombination toDomain(ProbabilityTypeCombinationProjection ptcp);
}