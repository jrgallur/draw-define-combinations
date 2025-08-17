package com.draw_define_combinations.domain;

import com.draw_define_combinations.domain.types.TDateInteger;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString
@Getter
@Setter
public class ProbabilityTypeByDraw {
    private List<BigDecimal> numberList;
    private TDateInteger calculateDrawDate;
    private ProbabilityType type;

    public ProbabilityTypeByDraw() {
        numberList = new ArrayList<>(Collections.nCopies(49, new BigDecimal(0)));
    }

    /**
     * Return the probability of a number where 1 is the first number and 49 is the last
     * @param number The number to get its probability
     * @return The probability associated to the number
     */
    public BigDecimal getNumberProbability(int number) {
        return numberList.get(number - 1);
    }

    public void setNumberProbability(int number, BigDecimal value) {
        numberList.set(number - 1, value);
    }
}
