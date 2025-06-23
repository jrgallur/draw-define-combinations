package com.draw_define_combinations.mappers;

import com.draw_define_combinations.models.ProbabilityType;
import com.draw_define_combinations.models.NumberProbabilityTypeMO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NumberProbabilityTypeMapper {
    NumberProbabilityTypeMO toModel(ProbabilityType probabilityType);

    ProbabilityType toDomain(NumberProbabilityTypeMO numberProbabilityTypeMO);
}