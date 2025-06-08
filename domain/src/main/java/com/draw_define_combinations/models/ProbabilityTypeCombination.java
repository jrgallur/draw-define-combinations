package com.draw_define_combinations.models;

import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Contains one combination of different types
 */
@ToString
@Getter
@Setter
public class ProbabilityTypeCombination {
    private String code;
    private List<ProbabilityTypeWeight> combinationList = new ArrayList<>();

    public void addProbabilityTypeWeight(ProbabilityTypeWeight probabilityTypeWeight) {
        combinationList.add(probabilityTypeWeight);
    }

    public String getCode() {
        if (code==null && combinationList.isEmpty()) {
            return null;
        }

        if (code==null) {
            List<ProbabilityTypeWeight> combinationAux = new ArrayList<>(getCombinationList());
            combinationAux.sort(
                    Comparator.comparing(p -> p.getNumberProbabilityType().getCode())
            );
            StringBuilder sb = new StringBuilder();
            for (ProbabilityTypeWeight row : combinationAux) {
                if (!sb.isEmpty()) {
                    sb.append("#");
                }
                sb.append("#").append(row.getNumberProbabilityType().getCode());
            }
            code = sb.toString();
        }
        return code;
    }
}
