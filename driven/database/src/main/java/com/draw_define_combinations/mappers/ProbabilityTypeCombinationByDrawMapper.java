package com.draw_define_combinations.mappers;

import com.draw_define_combinations.domain.ProbabilityTypeCombinationByDraw;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationByDrawMO;
import com.draw_define_combinations.domain.types.TDateInteger;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProbabilityTypeCombinationByDrawMapper {
    default ProbabilityTypeCombinationByDraw toDomain(ProbabilityTypeCombinationByDrawMO mo) {
       return ProbabilityTypeCombinationByDraw.builder()
                .id(mo.getId())
                .drawDate(new TDateInteger(mo.getDrawDate()))
                .drawTypeId(mo.getDrawTypeId())
                .probabilityTypeCombinationId(mo.getProbabilityTypeCombinationId())
                .numberList(stringToNumberList(mo.getValues()))
                .build();
    }

    ProbabilityTypeCombinationByDraw toDomainSimple(ProbabilityTypeCombinationByDrawMO mo);

    default TDateInteger integerToTDateTime(Integer integer) {
        return new TDateInteger(integer);
    }

    default List<BigDecimal> stringToNumberList(String numberString) {
        return Arrays.stream(numberString.split("#")).map(BigDecimal::new).toList();
    }


    default ProbabilityTypeCombinationByDrawMO toModel(ProbabilityTypeCombinationByDraw domain) {
        return ProbabilityTypeCombinationByDrawMO.builder()
                .id(domain.getId())
                .drawDate(domain.getDrawDate().toInteger())
                .drawTypeId(domain.getDrawTypeId())
                .probabilityTypeCombinationId(domain.getProbabilityTypeCombinationId())
                .values(numberListToString(domain.getNumberList()))
                .build();
    }

    private String numberListToString(List<BigDecimal> numberList) {
        StringBuilder result = new StringBuilder();
        for (int cont=0; cont<49; cont++) {
            result.append(numberList.get(cont)).append("#");
        }
        return result.toString();
    }
}