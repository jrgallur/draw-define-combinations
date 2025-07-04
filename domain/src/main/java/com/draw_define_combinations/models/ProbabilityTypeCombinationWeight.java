package com.draw_define_combinations.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Data
@Builder
public class ProbabilityTypeCombinationWeight {
    ProbabilityType probabilityType;
    BigDecimal weight;

    public String getPrettyWeight() {
        return (new DecimalFormat("#0.0")).format(weight);
    }
}
