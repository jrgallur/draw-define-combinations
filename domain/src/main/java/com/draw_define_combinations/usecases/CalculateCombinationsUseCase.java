package com.draw_define_combinations.usecases;

import com.draw_define_combinations.models.NumberProbabilityType;
import com.draw_define_combinations.models.ProbabilityTypeCombination;
import com.draw_define_combinations.models.ProbabilityTypeWeight;
import com.draw_define_combinations.ports.driven.NumberProbabilityTypePort;
import com.draw_define_combinations.services.GenerateCombinationTypes;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CalculateCombinationsUseCase {
    private final NumberProbabilityTypePort numberProbabilityTypePort;

    @Autowired
    private ApplicationContext context;

    public void executeAll() {
        getCombinationList();
    }

    private void getCombinationList() {
        List<NumberProbabilityType> numberProbabilityTypeList = new ArrayList<>();
        numberProbabilityTypeList.add(NumberProbabilityType.builder().code("0000").build());
        numberProbabilityTypeList.addAll(numberProbabilityTypePort.findAll());

        log.info("#######");
        for (NumberProbabilityType numberProbabilityType : numberProbabilityTypeList) {
            log.info(numberProbabilityType.getCode());
        }
        log.info("#######");
        List<ProbabilityTypeCombination> probabilityTypeCombinationList =  GenerateCombinationTypes.getCombinationsFromTypeList(numberProbabilityTypeList);
        int cont = 1;
        for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
            String result = String.valueOf(cont) + ": ";
            List<ProbabilityTypeWeight> probabilityTypeWeightList = probabilityTypeCombination.getCombination();
            for (ProbabilityTypeWeight probabilityTypeWeight : probabilityTypeWeightList) {
                result += probabilityTypeWeight.getNumberProbabilityType().getCode() + "-" + probabilityTypeWeight.getWeight() + " ";
            }
            log.info(result);

            cont++;
        }
        log.info("#######");

        // TODO: Implementar
    }
}
