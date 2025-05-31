package com.draw_define_combinations.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProbabilityTypeWeight {
    NumberProbabilityType numberProbabilityType;
    BigDecimal weight;
}
