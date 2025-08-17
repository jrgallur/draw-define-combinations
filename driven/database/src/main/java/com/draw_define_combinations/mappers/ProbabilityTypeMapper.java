package com.draw_define_combinations.mappers;

import com.draw_define_combinations.domain.ProbabilityType;
import com.draw_define_combinations.domain.ProbabilityTypeMO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProbabilityTypeMapper {
    ProbabilityType toDomain(ProbabilityTypeMO probabilityTypeMO);
}