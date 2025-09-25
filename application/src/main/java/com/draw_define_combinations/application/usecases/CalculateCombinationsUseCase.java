package com.draw_define_combinations.application.usecases;

import com.draw_define_combinations.application.ports.driven.*;
import com.draw_define_combinations.domain.*;
import com.draw_define_combinations.application.services.ProbabilityTypeCombinationService;
import com.draw_define_combinations.domain.types.TDateInteger;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CalculateCombinationsUseCase {
    private final ProbabilityTypeDatasourcePort probabilityTypeDatasourcePort;
    private final ProbabilityTypeCombinationDatasourcePort probabilityTypeCombinationDatasourcePort;
    private final ProbabilityTypeCombinationWeightDatasourcePort probabilityTypeCombinationWeightDatasourcePort;
    private final ProbabilityTypeByDrawDatasourcePort probabilityTypeByDrawDatasourcePort;
    private final ProbabilityTypeCombinationService probabilityTypeCombinationService;
    private final ProbabilityTypeCombinationByDrawDatasourcePort probabilityTypeCombinationByDrawDatasourcePort;
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
            // Initializes a Map to optimize
            Map<Integer, List<ProbabilityTypeCombinationWeight>> probabilityTypeCombinationWeightMap = new HashMap<>();
            // Get the draw list
            DrawList drawList = drawDatasourcePort.getDrawListByDrawTypeId(drawTypeId);

            // Get the not existen tuples of ProbabilityTypeCombinationByDraw which should exist
            List<ProbabilityTypeCombinationByDraw> probabilityTypeCombinationByDrawListUnexistent = getNotDefinedProbabilityTypeCombinationByDrawList(drawTypeId, probabilityTypeCombinationList, drawList);

            // For each one
            for (ProbabilityTypeCombinationByDraw probabilityTypeCombinationByDrawToCreate : probabilityTypeCombinationByDrawListUnexistent) {
                Integer probabilityTypeCombinationId = probabilityTypeCombinationByDrawToCreate.getProbabilityTypeCombinationId();
                if (!probabilityTypeCombinationWeightMap.containsKey(probabilityTypeCombinationId)) {
                    // Get the weight of the combination
                    List<ProbabilityTypeCombinationWeight> probabilityTypeCombinationWeightList = probabilityTypeCombinationWeightDatasourcePort.findByProbabilityTypeCombination(probabilityTypeCombinationId);
                    probabilityTypeCombinationWeightMap.put(probabilityTypeCombinationId, probabilityTypeCombinationWeightList);
                }
                List<ProbabilityTypeCombinationWeight> probabilityTypeCombinationWeightList = probabilityTypeCombinationWeightMap.get(probabilityTypeCombinationId);
                TDateInteger drawDate = probabilityTypeCombinationByDrawToCreate.getDrawDate();
                // Get the probabilities values for a draw type and draw date
                List<ProbabilityTypeByDraw> probabilityTypeByDrawList = probabilityTypeByDrawDatasourcePort.findByDrawTypeIdAndDrawDate(drawTypeId, drawDate);
                // Calculate the combination probabilities adding and multiplying probabilities by type
                ProbabilityTypeCombinationByDraw probabilityTypeCombinationByDraw = calculateCombinationProbabilityValues(drawTypeId, drawDate, probabilityTypeCombinationWeightList, probabilityTypeByDrawList);
                // Save the probabilities combination by draw
                probabilityTypeCombinationByDrawDatasourcePort.save(probabilityTypeCombinationByDraw);
            }
        }
    }

    private List<ProbabilityTypeCombinationByDraw> getNotDefinedProbabilityTypeCombinationByDrawList(Short drawTypeId, List<ProbabilityTypeCombination> probabilityTypeCombinationList, DrawList drawList) {
        // Creates an initial list with all the possible values of combinations by  draws (starting on initialDrawDate)
        List<ProbabilityTypeCombinationByDraw> probabilityTypeCombinationByDrawPossibleList = new ArrayList<>();
        probabilityTypeCombinationList.forEach(probabilityTypeCombination -> {
            Draw draw = drawList.getDraw(drawList.getInitialDrawDate());
            while (draw!=null) {
                probabilityTypeCombinationByDrawPossibleList.add(
                        ProbabilityTypeCombinationByDraw.builder()
                                .drawDate(draw.getDrawDate())
                                .probabilityTypeCombinationId(probabilityTypeCombination.getId())
                                .build()
                );
                draw = drawList.findNextDraw(draw.getDrawDate());
            }
        });

        // Get the existing combinations by draw
        List<ProbabilityTypeCombinationByDraw> probabilityTypeCombinationByDrawExistingList = probabilityTypeCombinationByDrawDatasourcePort.findByDrawTypeIdSimple(drawTypeId);
        // Convert the existing combinations by draw list in a Set for efficiency
        Set<String> probabilityTypeCombinationByDrawExistingSet = probabilityTypeCombinationByDrawExistingList.stream()
                .map(p -> p.getDrawDate() + "|" + p.getProbabilityTypeCombinationId())
                .collect(Collectors.toSet());

        // Return the possible list without the existent ones
        return probabilityTypeCombinationByDrawPossibleList.stream()
                .filter(a -> !probabilityTypeCombinationByDrawExistingSet.contains(a.getDrawDate() + "|" + a.getProbabilityTypeCombinationId()))
                .collect(Collectors.toList());
    }

    private ProbabilityTypeCombinationByDraw calculateCombinationProbabilityValues(Short drawTypeId, TDateInteger drawDate, List<ProbabilityTypeCombinationWeight> probabilityTypeCombinationWeightList, List<ProbabilityTypeByDraw> probabilityTypeByDrawList) {
        List<BigDecimal> probabilitiesByCombinationList = new ArrayList<>(Collections.nCopies(49, new BigDecimal(0)));
        for (ProbabilityTypeCombinationWeight probabilityTypeCombinationWeight : probabilityTypeCombinationWeightList) {
            ProbabilityTypeByDraw probabilityTypeByDraw = probabilityTypeByDrawList.stream().filter(type -> type.getType().getId().equals(probabilityTypeCombinationWeight.getProbabilityType().getId())).findFirst().orElse(null);
            if (probabilityTypeByDraw!=null) {
                BigDecimal multiplicand = probabilityTypeCombinationWeight.getWeight();
                List<BigDecimal> probabilitiesByTypeList = probabilityTypeByDraw.getNumberList();
                for (int cont = 0; cont < 49; cont++) {
                    probabilitiesByCombinationList.set(cont, probabilitiesByCombinationList.get(cont).add(probabilitiesByTypeList.get(cont).multiply(multiplicand)));
                }
            }
        }

        return ProbabilityTypeCombinationByDraw.builder()
                .drawDate(drawDate)
                .drawTypeId(drawTypeId)
                .probabilityTypeCombinationId(probabilityTypeCombinationWeightList.get(0).getProbabilityTypeCombinationId())
                .numberList(probabilitiesByCombinationList)
                .build();
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
