package com.draw_define_combinations.mappers;

import com.draw_define_combinations.models.NumberProbabilityType;
import com.draw_define_combinations.models.NumberProbabilityTypeMO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NumberProbabilityTypeMapper {
    NumberProbabilityTypeMO toModel(NumberProbabilityType numberProbabilityType);

    NumberProbabilityType toDomain(NumberProbabilityTypeMO numberProbabilityTypeMO);
}