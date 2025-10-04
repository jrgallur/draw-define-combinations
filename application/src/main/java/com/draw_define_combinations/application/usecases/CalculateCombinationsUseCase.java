package com.draw_define_combinations.application.usecases;

import com.draw_define_combinations.application.ports.driven.ProbabilityTypeCombinationDatasourcePort;
import com.draw_define_combinations.application.ports.driven.ProbabilityTypeCombinationWeightDatasourcePort;
import com.draw_define_combinations.application.ports.driven.ProbabilityTypeDatasourcePort;
import com.draw_define_combinations.application.services.ProbabilityTypeCombinationService;
import com.draw_define_combinations.domain.ProbabilityType;
import com.draw_define_combinations.domain.ProbabilityTypeCombination;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationWeight;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CalculateCombinationsUseCase {
    private final ProbabilityTypeDatasourcePort probabilityTypeDatasourcePort;
    private final ProbabilityTypeCombinationDatasourcePort probabilityTypeCombinationDatasourcePort;
    private final ProbabilityTypeCombinationWeightDatasourcePort probabilityTypeCombinationWeightDatasourcePort;
    private final ProbabilityTypeCombinationService probabilityTypeCombinationService;

    /**
     * Calculate all possible combinations following the rules of CombinationTypeService.calculateCombinationsFromTypeList and save the new ones into the database
     */
    public void calculateAndSaveNotExistentCombinations() {
        log.info("calculateAndSaveNotExistentCombinations - Inicio");
        // Calculate all the combinations and return them as a list
        List<ProbabilityTypeCombination> probabilityTypeCombinationList = getCalculatedCombinationTypeList();
        // Save all not existent combinations
        saveNotExistentProbabilityTypeCombination(probabilityTypeCombinationList);
        log.info("calculateAndSaveNotExistentCombinations - Fin");
    }

    private List<ProbabilityTypeCombination> getCalculatedCombinationTypeList() {
        List<ProbabilityType> probabilityTypeList = probabilityTypeDatasourcePort.findAll();

        List<ProbabilityTypeCombination> probabilityTypeCombinationList = probabilityTypeCombinationService.calculateCombinationsFromTypeList(probabilityTypeList);

        logProbabilityTypeCombinationList(probabilityTypeCombinationList);

        return probabilityTypeCombinationList;
    }

    private void logProbabilityTypeCombinationList(List<ProbabilityTypeCombination> probabilityTypeCombinationList) {
        log.info("#######");
        for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
            StringBuilder result = new StringBuilder(probabilityTypeCombination.getCode()).append(": ");
            List<ProbabilityTypeCombinationWeight> probabilityTypeCombinationWeightList = probabilityTypeCombination.getProbabilityTypeCombinationWeightList();
            for (ProbabilityTypeCombinationWeight probabilityTypeCombinationWeight : probabilityTypeCombinationWeightList) {
                result.append(probabilityTypeCombinationWeight.getProbabilityType().getCode()).append("-").append(probabilityTypeCombinationWeight.getPrettyWeight()).append(" ");
            }
            log.info(result.toString());
        }
        log.info("#######");
    }

    private void saveNotExistentProbabilityTypeCombination(List<ProbabilityTypeCombination> probabilityTypeCombinationList) {
        for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
            if (!probabilityTypeCombinationDatasourcePort.existsByCode(probabilityTypeCombination.getCode())) {
                ProbabilityTypeCombination probabilityTypeCombinationSaved = probabilityTypeCombinationDatasourcePort.upsert(probabilityTypeCombination);
                setProbabilityTypeCombinationToWeightList(probabilityTypeCombination, probabilityTypeCombinationSaved);
                probabilityTypeCombinationWeightDatasourcePort.saveAll(probabilityTypeCombination.getProbabilityTypeCombinationWeightList());
            }
        }
    }

    private void setProbabilityTypeCombinationToWeightList(ProbabilityTypeCombination probabilityTypeCombination, ProbabilityTypeCombination probabilityTypeCombinationSaved) {
        probabilityTypeCombination.getProbabilityTypeCombinationWeightList().forEach(probabilityTypeCombinationWeight -> probabilityTypeCombinationWeight.setProbabilityTypeCombinationId(probabilityTypeCombinationSaved.getId()));
    }
}
