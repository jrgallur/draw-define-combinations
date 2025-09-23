package com.draw_define_combinations.application.usecases;

import com.draw_define_combinations.application.ports.driven.*;
import com.draw_define_combinations.domain.*;
import com.draw_define_combinations.application.services.ProbabilityTypeCombinationService;
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
    private final ProbabilityTypeByDrawDatasourcePort probabilityTypeByDrawDatasourcePort;
    private final ProbabilityTypeCombinationService probabilityTypeCombinationService;
    private final DrawDatasourcePort drawDatasourcePort;

    /**
     * Calculate all possible combinations following the rules of CombinationTypeService.calculateCombinationsFromTypeList and save the new ones into the database
     */
    public void calculateAndSaveNotExistentCombinations() {
        // Calculate all the combinations and return them as a list
        List<ProbabilityTypeCombination> probabilityTypeCombinationList = getCalculatedCombinationTypeList();
        // Save all not existent combinations
        saveNotExistentProbabilityTypeCombination(probabilityTypeCombinationList);
    }

    public void saveCombinationsProbabilityValues() {
        // Get the probability type combination list from database
        List<ProbabilityTypeCombination> probabilityTypeCombinationList = getDatabaseSimpleCombinationTypeList();
        // Get the different draws types
        List<Short> drawTypeIdList = drawDatasourcePort.getDrawTypeIdList();
        // For each drawType...
        for (Short drawTypeId : drawTypeIdList) {
            // Get the draw list
            DrawList drawList = drawDatasourcePort.getDrawListByDrawTypeId(drawTypeId);
            // For each probabilityTypeCombination
            for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
                // TODO: Para cada fecha de los sorteos
                // TODO: Obtener el listado de los pesos por tipo de las combinaciones
                // TODO: Obtener el valor de las probabilidades por tipo para un sorteo determinado
                // TODO: Calcular el valor de las probabilidades por combinación, sumando y dividiendo los valores de las probabilidades según el tipo de la combinación
                // TODO: Guardar en base de datos el valor de las probabilidades por combinación y sorteo

/*
                Map<Integer, Map<TDateInteger, ProbabilityTypeByDraw>> numberProbabilityListByTDateIntegerMapByNumberProbabilityTypeId = new HashMap<>();
                // For each weight of the combination
                probabilityTypeCombination.getProbabilityTypeCombinationWeightList().stream().forEach(probabilityTypeCombinationWeight -> {
                    // Get the calculated probabilities for the type of the weight
                    List<ProbabilityTypeByDraw> probabilityTypeByDrawList = probabilityTypeByDrawDatasourcePort.findByProbabilityTypeId(probabilityTypeCombinationWeight.getProbabilityType().getId());
                    // Create a Map (date -> probability type -> calculated probabilities by type) for each draw date
                    // TODO
                    Map<TDateInteger, Map<ProbabilityType, ProbabilityTypeByDraw>> probabilitiesListByDrawMap = probabilityTypeByDrawList.stream().collect(Collectors.toMap(item -> item.getCalculateDrawDate(), item -> item));
                    numberProbabilityListByTDateIntegerMapByNumberProbabilityTypeId.put(probabilityTypeCombinationWeight.getProbabilityType().getId(), probabilitiesListByDrawMap);
                    // Guardar en el Map de Tipo -> (fecha -> Lista de probabilidades) el valor
                });

                // Elegimos el primer tipo

                // Recorremos el tipo fecha a fecha
                // Si existen todos los valores calculados para todos los tipos para la fecha actual, calculamos la combinación
*/
            }
        }
    }

    private void saveProbabilityCombinationList() {

    }

    private List<ProbabilityTypeCombination> getCalculatedCombinationTypeList() {
        List<ProbabilityType> probabilityTypeList = probabilityTypeDatasourcePort.findAll();

        List<ProbabilityTypeCombination> probabilityTypeCombinationList = probabilityTypeCombinationService.calculateCombinationsFromTypeList(probabilityTypeList);

        logProbabilityTypeCombinationList(probabilityTypeCombinationList);

        return probabilityTypeCombinationList;
    }

    private List<ProbabilityTypeCombination> getDatabaseSimpleCombinationTypeList() {
        return probabilityTypeCombinationDatasourcePort.getAllSimpleProbabilityTypeCombination();
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
