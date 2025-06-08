package com.draw_define_combinations.usecases;

import com.draw_define_combinations.models.NumberProbabilityType;
import com.draw_define_combinations.models.ProbabilityTypeCombination;
import com.draw_define_combinations.models.ProbabilityTypeWeight;
import com.draw_define_combinations.ports.driven.NumberProbabilityTypePort;
import com.draw_define_combinations.ports.driven.ProbabilityTypeCombinationPort;
import com.draw_define_combinations.services.CombinationTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CalculateCombinationsUseCase {
    private final NumberProbabilityTypePort numberProbabilityTypePort;
    private final ProbabilityTypeCombinationPort probabilityTypeCombinationPort;
    private final CombinationTypeService combinationTypeService;

    public void executeAll() {
        List<ProbabilityTypeCombination> probabilityTypeCombinationList = getCombinationList();
        saveCombinationList(probabilityTypeCombinationList);
    }

    private List<ProbabilityTypeCombination> getCombinationList() {
        List<NumberProbabilityType> numberProbabilityTypeList = new ArrayList<>();
        numberProbabilityTypeList.add(NumberProbabilityType.builder().code("0000").build());
        numberProbabilityTypeList.addAll(numberProbabilityTypePort.findAll());

        List<ProbabilityTypeCombination> probabilityTypeCombinationList = combinationTypeService.getCombinationsFromTypeList(numberProbabilityTypeList);

        logProbabilityTypeCombinationList(probabilityTypeCombinationList);

        return probabilityTypeCombinationList;
    }

    private void logProbabilityTypeCombinationList(List<ProbabilityTypeCombination> probabilityTypeCombinationList) {
        log.info("#######");
        for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
            StringBuilder result = new StringBuilder(probabilityTypeCombination.getCode()).append(": ");
            List<ProbabilityTypeWeight> probabilityTypeWeightList = probabilityTypeCombination.getCombinationList();
            for (ProbabilityTypeWeight probabilityTypeWeight : probabilityTypeWeightList) {
                result.append(probabilityTypeWeight.getNumberProbabilityType().getCode()).append("-").append(probabilityTypeWeight.getPrettyWeight()).append(" ");
            }
            log.info(result.toString());
        }
        log.info("#######");
    }

    private void saveCombinationList(List<ProbabilityTypeCombination> probabilityTypeCombinationList) {
        for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
            if (!probabilityTypeCombinationPort.existsByCode(probabilityTypeCombination.getCode())) {
                probabilityTypeCombinationPort.upsert(probabilityTypeCombination);
            }
        }
    }
}
