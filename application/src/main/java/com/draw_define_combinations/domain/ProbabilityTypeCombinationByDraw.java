package com.draw_define_combinations.domain;

import com.draw_define_combinations.domain.types.TDateInteger;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
public class ProbabilityTypeCombinationByDraw {
    private Integer id;
    private Short drawTypeId;
    private TDateInteger drawDate;
    private Integer probabilityTypeCombinationId;
    private List<BigDecimal> numberList;

    public BigDecimal getNumberProbability(int number) {
        initializeNumberList();
        return numberList.get(number - 1);
    }

    public void setNumberProbability(int number, BigDecimal value) {
        initializeNumberList();
        numberList.set(number - 1, value);
    }

    public List<BigDecimal> getNumberList() {
        initializeNumberList();
        return numberList;
    }

    private void initializeNumberList() {
        if (numberList == null) {
            numberList = new ArrayList<>(Collections.nCopies(49, new BigDecimal(0)));
        }
    }
}
