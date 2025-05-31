package com.draw_define_combinations.mappers;

import com.draw_define_combinations.models.*;
import com.draw_define_combinations.models.types.TDateInteger;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NumberProbabilityListMapper {

    default NumberProbabilityListMO toModel(NumberProbabilityList numberProbabilityList) {
        return NumberProbabilityListMO.builder()
                .date(numberProbabilityList.getCalculateDrawDate().toInteger())
                .numberProbabilityTypeId(numberProbabilityList.getType().getId())
                .values(numberListToString(numberProbabilityList.getNumberList()))
                .build();
    }

    default String numberListToString(List<BigDecimal> numberList) {
        StringBuilder result = new StringBuilder();
        for (int cont=0; cont<49; cont++) {
            result.append(numberList.get(cont)).append("#");
        }
        return result.toString();
    }

    default NumberProbabilityList toDomain(NumberProbabilityListMO numberProbabilityListMO) {
        NumberProbabilityList numberProbabilityList = new NumberProbabilityList();
        numberProbabilityList.setType(NumberProbabilityType.builder().id(numberProbabilityListMO.getNumberProbabilityTypeId()).build());
        numberProbabilityList.setCalculateDrawDate(new TDateInteger(numberProbabilityListMO.getDate()));
        numberProbabilityList.setNumberList(stringToNumberList(numberProbabilityListMO.getValues()));

        return numberProbabilityList;
    }

    default List<BigDecimal> stringToNumberList(String numberString) {
        return Arrays.stream(numberString.split("#")).map(BigDecimal::new).toList();
    }
}