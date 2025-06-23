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
@Builder
public class ProbabilityTypeCombination {
    private String code;
    @Builder.Default
    private List<ProbabilityTypeCombinationWeight> combinationList = new ArrayList<>();

    public void addProbabilityTypeWeight(ProbabilityTypeCombinationWeight probabilityTypeCombinationWeight) {
        combinationList.add(probabilityTypeCombinationWeight);
    }

    public String getCode() {
        if (code==null && combinationList.isEmpty()) {
            return null;
        }

        if (code==null) {
            List<ProbabilityTypeCombinationWeight> combinationAux = new ArrayList<>(getCombinationList());
            combinationAux.sort(
                    Comparator.comparing(p -> p.getProbabilityType().getCode())
            );
            StringBuilder sb = new StringBuilder();
            for (ProbabilityTypeCombinationWeight row : combinationAux) {
                if (!sb.isEmpty()) {
                    sb.append("#");
                }
                sb.append("#").append(row.getProbabilityType().getCode());
            }
            code = sb.toString();
        }
        return code;
    }
}
