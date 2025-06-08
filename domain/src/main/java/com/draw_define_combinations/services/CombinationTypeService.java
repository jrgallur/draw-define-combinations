package com.draw_define_combinations.services;


import com.draw_define_combinations.models.NumberProbabilityType;
import com.draw_define_combinations.models.ProbabilityTypeCombination;
import com.draw_define_combinations.models.ProbabilityTypeWeight;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@NoArgsConstructor
public class CombinationTypeService {
    private static final BigDecimal STEP = BigDecimal.valueOf(0.2);

    public List<ProbabilityTypeCombination> getCombinationsFromTypeList(List<NumberProbabilityType> numberProbabilityTypeList) {
        return new ArrayList<>(getCombinationsOrderN(numberProbabilityTypeList));
    }

    /**
     * Retrieves combinations of different types with the next conditions:
     * The sum of all of them must be 1
     * The vales must be STEP multiples (0, 0.1, 0.2, ..., 0.9, 1.0)
     */
    public List<ProbabilityTypeCombination> getCombinationsOrderN(List<NumberProbabilityType> types) {
        List<ProbabilityTypeCombination> result = new ArrayList<>();

        generateCombinations(types, new ArrayList<>(), BigDecimal.ZERO, STEP, result);
        setCombinationsCodes(result);
        return result;
    }

    private void setCombinationsCodes(List<ProbabilityTypeCombination> probabilityTypeCombinationList) {
        for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
            sortProbabilityTypeWeightList(probabilityTypeCombination.getCombinationList());
            probabilityTypeCombination.setCode(getCodeFromCombinationTypeWeightList(probabilityTypeCombination.getCombinationList()));
        }
    }

    private void sortProbabilityTypeWeightList(List<ProbabilityTypeWeight> probabilityTypeWeightList) {
        probabilityTypeWeightList.sort(Comparator.comparing(p -> p.getNumberProbabilityType().getCode()));
    }
    private String getCodeFromCombinationTypeWeightList(List<ProbabilityTypeWeight> probabilityTypeWeightList) {
        StringBuilder result = new StringBuilder();
        for (ProbabilityTypeWeight probabilityTypeWeight : probabilityTypeWeightList) {
            if (!result.isEmpty()) {
                result.append("#");
            }
            result.append(probabilityTypeWeight.getNumberProbabilityType().getCode()).append("[").append(probabilityTypeWeight.getPrettyWeight()).append("]");
        }

        return result.toString();
    }

    private void generateCombinations(List<NumberProbabilityType> types,
                                      List<BigDecimal> currentWeights,
                                      BigDecimal currentSum,
                                      BigDecimal step,
                                      List<ProbabilityTypeCombination> result) {
        int index = currentWeights.size();
        int size = types.size();

        if (index == size - 1) {
            // Calcular el último peso
            BigDecimal lastWeight = BigDecimal.ONE.subtract(currentSum);

            // Verificar que esté en rango válido y no sea igual a 1
            if (isValidWeight(lastWeight)) {
                List<BigDecimal> finalWeights = new ArrayList<>(currentWeights);
                finalWeights.add(lastWeight);

                ProbabilityTypeCombination combination = new ProbabilityTypeCombination();
                for (int i = 0; i < size; i++) {
                    combination.addProbabilityTypeWeight(
                            ProbabilityTypeWeight.builder()
                                    .numberProbabilityType(types.get(i))
                                    .weight(finalWeights.get(i))
                                    .build()
                    );
                }
                result.add(combination);
            }
        } else {
            BigDecimal weight = BigDecimal.ZERO;
            while (weight.compareTo(BigDecimal.ONE) <= 0) {
                BigDecimal newSum = currentSum.add(weight);

                // Solo continuar si la suma parcial es menor que 1
                if (newSum.compareTo(BigDecimal.ONE) <= 0) {
                    List<BigDecimal> newWeights = new ArrayList<>(currentWeights);
                    newWeights.add(weight);
                    generateCombinations(types, newWeights, newSum, step, result);
                }

                weight = weight.add(step);
            }
        }
    }

    private boolean isValidWeight(BigDecimal weight) {
        // Aceptar solo pesos múltiplos del paso
        return weight.remainder(STEP).compareTo(BigDecimal.ZERO) == 0;
    }

}
