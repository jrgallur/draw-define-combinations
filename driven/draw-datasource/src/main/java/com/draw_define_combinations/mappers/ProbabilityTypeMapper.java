package com.draw_define_combinations.mappers;

import com.draw_define_combinations.models.ProbabilityType;
import com.draw_define_combinations.models.ProbabilityTypeMO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProbabilityTypeMapper {
    ProbabilityType toDomain(ProbabilityTypeMO probabilityTypeMO);
}