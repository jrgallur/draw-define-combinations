package com.draw_define_combinations.usecases;

import com.draw_define_combinations.models.DrawProbabilityType;
import com.draw_define_combinations.models.ProbabilityType;
import com.draw_define_combinations.models.ProbabilityTypeCombination;
import com.draw_define_combinations.models.ProbabilityTypeCombinationWeight;
import com.draw_define_combinations.models.types.TDateInteger;
import com.draw_define_combinations.ports.driven.ProbabilityTypePort;
import com.draw_define_combinations.ports.driven.NumberProbabilityTypePort;
import com.draw_define_combinations.ports.driven.ProbabilityTypeCombinationPort;
import com.draw_define_combinations.services.CombinationTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CalculateCombinationsUseCase {
    private final NumberProbabilityTypePort numberProbabilityTypePort;
    private final ProbabilityTypeCombinationPort probabilityTypeCombinationPort;
    private final ProbabilityTypePort probabilityTypePort;
    private final CombinationTypeService combinationTypeService;

    /**
     * Calculate all posible combinations following the rules of CombinationTypeService.calculateCombinationsFromTypeList and save the new ones into the database
     */
    public void calculateAndSaveUnexistantCombinations() {
        List<ProbabilityTypeCombination> probabilityTypeCombinationList = getCalculatedCombinationTypeList();
        saveUnexistantCombinationList(probabilityTypeCombinationList);
    }

    public void saveProbabilityCombinations() {
        // Obtener la lista de combinaciones de la base de datos
        // obtener la lista de sorteos (para las fechas)
        // Obtener la lista de las probabilidades de los tipos
        // Guardar en base de datos las tuplas de tipo_calculo, combinación, probabilidades

        // Get the probability type combination list from database
        List<ProbabilityTypeCombination> probabilityTypeCombinationList = getDatabaseCombinationTypeList();
        // For each probabilityTypeCombination
        for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
            // TODO: If not exists the relationship between the draw and the probabilityTypeCombination
            Map<Short, Map<TDateInteger, DrawProbabilityType>> numberProbabilityListByTDateIntegerMapByNumberProbabilityTypeId = new HashMap<>();

            // Para cada combinación
            probabilityTypeCombination.getCombinationList().stream().forEach(probabilityTypeCombinationWeight -> {
                // Obtener la lista de probabilidades calculadas para el tipo de la combinación
                List<DrawProbabilityType> drawProbabilityType = probabilityTypePort.findByProbabilityTypeId(probabilityTypeCombinationWeight.getProbabilityType().getId());
                // Crear un Map fecha -> Lista de probabilidades del tipo de la combinación
                Map<TDateInteger, DrawProbabilityType> numberProbabilityListMap = drawProbabilityType.stream().collect(Collectors.toMap(item -> item.getCalculateDrawDate(), item -> item));
                // Guardar en el Map de Tipo -> (fecha -> Lista de probabilidades) el valor
                numberProbabilityListByTDateIntegerMapByNumberProbabilityTypeId.put(probabilityTypeCombinationWeight.getProbabilityType().getId(), numberProbabilityListMap);
            });

            // Elegimos el primer tipo

            // Recorremos el tipo fecha a fecha
            // Si existen todos los valores calculados para todos los tipos para la fecha actual, calculamos la combinación

        }
    }

    private void saveProbabilityCombinationList() {

    }

    private List<ProbabilityTypeCombination> getCalculatedCombinationTypeList() {
        List<ProbabilityType> probabilityTypeList = new ArrayList<>();
        probabilityTypeList.add(ProbabilityType.builder().code("0000").build());
        probabilityTypeList.addAll(numberProbabilityTypePort.findAll());

        List<ProbabilityTypeCombination> probabilityTypeCombinationList = combinationTypeService.calculateCombinationsFromTypeList(probabilityTypeList);

        logProbabilityTypeCombinationList(probabilityTypeCombinationList);

        return probabilityTypeCombinationList;
    }

    private List<ProbabilityTypeCombination> getDatabaseCombinationTypeList() {
        return probabilityTypeCombinationPort.getAllProbabilityTypeCombinationList();
    }

    private void logProbabilityTypeCombinationList(List<ProbabilityTypeCombination> probabilityTypeCombinationList) {
        log.info("#######");
        for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
            StringBuilder result = new StringBuilder(probabilityTypeCombination.getCode()).append(": ");
            List<ProbabilityTypeCombinationWeight> probabilityTypeCombinationWeightList = probabilityTypeCombination.getCombinationList();
            for (ProbabilityTypeCombinationWeight probabilityTypeCombinationWeight : probabilityTypeCombinationWeightList) {
                result.append(probabilityTypeCombinationWeight.getProbabilityType().getCode()).append("-").append(probabilityTypeCombinationWeight.getPrettyWeight()).append(" ");
            }
            log.info(result.toString());
        }
        log.info("#######");
    }

    private void saveUnexistantCombinationList(List<ProbabilityTypeCombination> probabilityTypeCombinationList) {
        for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
            if (!probabilityTypeCombinationPort.existsByCode(probabilityTypeCombination.getCode())) {
                probabilityTypeCombinationPort.upsert(probabilityTypeCombination);
            }
        }
    }
}
