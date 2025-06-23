package com.draw_define_combinations.mappers;

import com.draw_define_combinations.models.DrawProbabilityType;
import com.draw_define_combinations.models.DrawProbabilityTypeMO;
import com.draw_define_combinations.models.ProbabilityType;
import com.draw_define_combinations.models.types.TDateInteger;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DrawProbabilityTypeMapper {

    default DrawProbabilityTypeMO toModel(DrawProbabilityType drawProbabilityType) {
        return DrawProbabilityTypeMO.builder()
                .date(drawProbabilityType.getCalculateDrawDate().toInteger())
                .numberProbabilityTypeId(drawProbabilityType.getType().getId())
                .values(numberListToString(drawProbabilityType.getNumberList()))
                .build();
    }

    default String numberListToString(List<BigDecimal> numberList) {
        StringBuilder result = new StringBuilder();
        for (int cont=0; cont<49; cont++) {
            result.append(numberList.get(cont)).append("#");
        }
        return result.toString();
    }

    default DrawProbabilityType toDomain(DrawProbabilityTypeMO drawProbabilityTypeMO) {
        DrawProbabilityType drawProbabilityType = new DrawProbabilityType();
        drawProbabilityType.setType(ProbabilityType.builder().id(drawProbabilityTypeMO.getNumberProbabilityTypeId()).build());
        drawProbabilityType.setCalculateDrawDate(new TDateInteger(drawProbabilityTypeMO.getDate()));
        drawProbabilityType.setNumberList(stringToNumberList(drawProbabilityTypeMO.getValues()));

        return drawProbabilityType;
    }

    default List<BigDecimal> stringToNumberList(String numberString) {
        return Arrays.stream(numberString.split("#")).map(BigDecimal::new).toList();
    }
}