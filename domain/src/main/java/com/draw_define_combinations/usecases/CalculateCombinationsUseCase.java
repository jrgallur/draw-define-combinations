package com.draw_define_combinations.usecases;

import com.draw_define_combinations.models.ProbabilityType;
import com.draw_define_combinations.models.ProbabilityTypeByDraw;
import com.draw_define_combinations.models.ProbabilityTypeCombination;
import com.draw_define_combinations.models.ProbabilityTypeCombinationWeight;
import com.draw_define_combinations.models.types.TDateInteger;
import com.draw_define_combinations.ports.driven.ProbabilityTypeByDrawPort;
import com.draw_define_combinations.ports.driven.ProbabilityTypeCombinationPort;
import com.draw_define_combinations.ports.driven.ProbabilityTypeCombinationWeightPort;
import com.draw_define_combinations.ports.driven.ProbabilityTypePort;
import com.draw_define_combinations.services.ProbabilityTypeCombinationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CalculateCombinationsUseCase {
    private final ProbabilityTypePort probabilityTypePort;
    private final ProbabilityTypeCombinationPort probabilityTypeCombinationPort;
    private final ProbabilityTypeCombinationWeightPort probabilityTypeCombinationWeightPort;
    private final ProbabilityTypeByDrawPort probabilityTypeByDrawPort;
    private final ProbabilityTypeCombinationService probabilityTypeCombinationService;

    /**
     * Calculate all possible combinations following the rules of CombinationTypeService.calculateCombinationsFromTypeList and save the new ones into the database
     */
    public void calculateAndSaveUnExistentCombinations() {
        List<ProbabilityTypeCombination> probabilityTypeCombinationList = getCalculatedCombinationTypeList();
        saveUnExistentCombinationList(probabilityTypeCombinationList);
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
            Map<Integer, Map<TDateInteger, ProbabilityTypeByDraw>> numberProbabilityListByTDateIntegerMapByNumberProbabilityTypeId = new HashMap<>();

            // Para cada combinación
            probabilityTypeCombination.getProbabilityTypeCombinationWeightList().stream().forEach(probabilityTypeCombinationWeight -> {
                // Obtener la lista de probabilidades calculadas para el tipo de la combinación
                List<ProbabilityTypeByDraw> probabilityTypeByDraw = probabilityTypeByDrawPort.findByProbabilityTypeId(probabilityTypeCombinationWeight.getProbabilityType().getId());
                // Crear un Map fecha -> Lista de probabilidades del tipo de la combinación
                Map<TDateInteger, ProbabilityTypeByDraw> numberProbabilityListMap = probabilityTypeByDraw.stream().collect(Collectors.toMap(item -> item.getCalculateDrawDate(), item -> item));
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
        List<ProbabilityType> probabilityTypeList = probabilityTypePort.findAll();

        List<ProbabilityTypeCombination> probabilityTypeCombinationList = probabilityTypeCombinationService.calculateCombinationsFromTypeList(probabilityTypeList);

        logProbabilityTypeCombinationList(probabilityTypeCombinationList);

        return probabilityTypeCombinationList;
    }

    private List<ProbabilityTypeCombination> getDatabaseCombinationTypeList() {
        return probabilityTypeCombinationPort.getAllProbabilityTypeCombinationWithWeightList();
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

    private void saveUnExistentCombinationList(List<ProbabilityTypeCombination> probabilityTypeCombinationList) {
        for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
            if (!probabilityTypeCombinationPort.existsByCode(probabilityTypeCombination.getCode())) {
                ProbabilityTypeCombination probabilityTypeCombinationSaved = probabilityTypeCombinationPort.upsert(probabilityTypeCombination);
                setProbabilityTypeCombinationIdToWeightList(probabilityTypeCombination, probabilityTypeCombinationSaved.getId());
                probabilityTypeCombinationWeightPort.saveAll(probabilityTypeCombination.getProbabilityTypeCombinationWeightList());
            }
        }
    }

    private void setProbabilityTypeCombinationIdToWeightList(ProbabilityTypeCombination probabilityTypeCombination, Integer id) {
        probabilityTypeCombination.getProbabilityTypeCombinationWeightList().forEach(probabilityTypeCombinationWeight -> probabilityTypeCombinationWeight.setProbabilityTypeCombinationId(id));
    }
}
