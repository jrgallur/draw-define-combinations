package com.draw_define_combinations.mappers;

import com.draw_define_combinations.domain.ProbabilityTypeByDraw;
import com.draw_define_combinations.domain.ProbabilityTypeByDrawMO;
import com.draw_define_combinations.domain.ProbabilityType;
import com.draw_define_combinations.domain.types.TDateInteger;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProbabilityTypeByDrawMapper {
    default ProbabilityTypeByDraw toDomain(ProbabilityTypeByDrawMO probabilityTypeByDrawMO) {
        ProbabilityTypeByDraw probabilityTypeByDraw = new ProbabilityTypeByDraw();
        probabilityTypeByDraw.setType(ProbabilityType.builder().id(probabilityTypeByDrawMO.getProbabilityTypeId()).build());
        probabilityTypeByDraw.setCalculateDrawDate(new TDateInteger(probabilityTypeByDrawMO.getDate()));
        probabilityTypeByDraw.setNumberList(stringToNumberList(probabilityTypeByDrawMO.getValues()));

        return probabilityTypeByDraw;
    }

    default List<BigDecimal> stringToNumberList(String numberString) {
        return Arrays.stream(numberString.split("#")).map(BigDecimal::new).toList();
    }
}