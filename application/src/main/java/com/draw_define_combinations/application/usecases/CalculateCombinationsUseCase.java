package com.draw_define_combinations.application.usecases;

import com.draw_define_combinations.application.ports.driven.*;
import com.draw_define_combinations.application.services.ProbabilityTypeCombinationService;
import com.draw_define_combinations.domain.*;
import com.draw_define_combinations.domain.types.TDateInteger;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CalculateCombinationsUseCase {
    private final ProbabilityTypeDatasourcePort probabilityTypeDatasourcePort;
    private final ProbabilityTypeCombinationDatasourcePort probabilityTypeCombinationDatasourcePort;
    private final ProbabilityTypeCombinationWeightDatasourcePort probabilityTypeCombinationWeightDatasourcePort;
    private final ProbabilityTypeCombinationService probabilityTypeCombinationService;
    private final DrawDatasourcePort drawDatasourcePort;
    private final ProbabilityTypeByDrawDatasourcePort probabilityTypeByDrawDatasourcePort;
    private final ProbabilityTypeCombinationByDrawDatasourcePort probabilityTypeCombinationByDrawDatasourcePort;


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
        List<String> probabilityTypeCombinationReceivedCodeList = probabilityTypeCombinationList.stream().map(ProbabilityTypeCombination::getCode).toList();
        List<String> probabilityTypeCombinationExistingCodeList = probabilityTypeCombinationDatasourcePort.findByCodeList(probabilityTypeCombinationReceivedCodeList);
        if (probabilityTypeCombinationExistingCodeList == null || probabilityTypeCombinationExistingCodeList.isEmpty()) {
            saveProbabilityTypeCombinationList(probabilityTypeCombinationList);
        } else {
            Set<String> probabilityTypeCombinationExistingCodeSet = new HashSet<>(probabilityTypeCombinationExistingCodeList);
            List<ProbabilityTypeCombination> probabilityTypeCombinationUnexistingList = probabilityTypeCombinationList.stream().filter(
                    probabilityTypeCombination -> !probabilityTypeCombinationExistingCodeSet.contains(probabilityTypeCombination.getCode())
            ).toList();
            saveProbabilityTypeCombinationList(probabilityTypeCombinationUnexistingList);
        }
    }

    private void saveProbabilityTypeCombinationList(List<ProbabilityTypeCombination> probabilityTypeCombinationList) {
        for (ProbabilityTypeCombination probabilityTypeCombination : probabilityTypeCombinationList) {
            ProbabilityTypeCombination probabilityTypeCombinationSaved = probabilityTypeCombinationDatasourcePort.upsert(probabilityTypeCombination);
            setProbabilityTypeCombinationToWeightList(probabilityTypeCombination, probabilityTypeCombinationSaved);
            probabilityTypeCombinationWeightDatasourcePort.saveAll(probabilityTypeCombination.getProbabilityTypeCombinationWeightList());
        }
    }

    private void setProbabilityTypeCombinationToWeightList(ProbabilityTypeCombination probabilityTypeCombination, ProbabilityTypeCombination probabilityTypeCombinationSaved) {
        probabilityTypeCombination.getProbabilityTypeCombinationWeightList().forEach(probabilityTypeCombinationWeight -> probabilityTypeCombinationWeight.setProbabilityTypeCombinationId(probabilityTypeCombinationSaved.getId()));
    }

    public void saveCombinationsProbabilityValues() {
        log.info("saveCombinationsProbabilityValues - Inicio");
        // Get the active probability type combination list from database
        List<ProbabilityTypeCombination> probabilityTypeCombinationList = getActiveSimpleCombinationTypeList();
        // Get the different draws types
        List<Short> drawTypeIdList = drawDatasourcePort.getDrawTypeIdList();

        // For each drawType...
        for (Short drawTypeId : drawTypeIdList) {
            // Get All the probabilities values for a draw type
            Map<TDateInteger, List<ProbabilityTypeByDraw>> allProbabilityTypeByDrawMap = probabilityTypeByDrawDatasourcePort(drawTypeId);

            // Initializes a Map to optimize
            Map<Integer, List<ProbabilityTypeCombinationWeight>> probabilityTypeCombinationWeightMap = new HashMap<>();
            // Get the draw list
            DrawList drawList = drawDatasourcePort.getDrawListByDrawTypeId(drawTypeId);

            TDateInteger fromDate = drawList.getInitialDrawDate();
            TDateInteger toDate = getFinalDateByIncrement(drawList, fromDate, 1000);
            while (toDate != null) {
                generateUnexistentProbabilityTypeCombinationByDrawAndPage(drawTypeId, probabilityTypeCombinationList, drawList, probabilityTypeCombinationWeightMap, allProbabilityTypeByDrawMap, fromDate, toDate);
                fromDate = drawList.getNextDate(toDate);
                toDate = getFinalDateByIncrement(drawList, toDate, 1000);
            }

        }
        log.info("saveCombinationsProbabilityValues - Fin");
    }

    private TDateInteger getFinalDateByIncrement(DrawList drawList, TDateInteger firstDate, int increment) {
        if (drawList.getNextDate(firstDate) == null) {
            return null;
        }
        int numIteration = 0;
        TDateInteger result = firstDate;
        while (numIteration < increment && drawList.getNextDate(result) != null) {
            numIteration++;
            result = drawList.getNextDate(result);
        }
        return result;
    }

    private void generateUnexistentProbabilityTypeCombinationByDrawAndPage(Short drawTypeId, List<ProbabilityTypeCombination> probabilityTypeCombinationList, DrawList drawList,
                                                                           Map<Integer, List<ProbabilityTypeCombinationWeight>> probabilityTypeCombinationWeightMap,
                                                                           Map<TDateInteger, List<ProbabilityTypeByDraw>> allProbabilityTypeByDrawMap,
                                                                           TDateInteger fromDate, TDateInteger toDate) {
        // Get the not existen tuples of ProbabilityTypeCombinationByDraw which should exist
        List<ProbabilityTypeCombinationByDraw> probabilityTypeCombinationByDrawListUnexistent = getNotDefinedProbabilityTypeCombinationByDrawList(drawTypeId, probabilityTypeCombinationList, drawList, fromDate, toDate);

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
            List<ProbabilityTypeByDraw> probabilityTypeByDrawList = allProbabilityTypeByDrawMap.get(drawDate);
            // Calculate the combination probabilities adding and multiplying probabilities by type
            ProbabilityTypeCombinationByDraw probabilityTypeCombinationByDraw = calculateCombinationProbabilityValues(drawTypeId, drawDate, probabilityTypeCombinationWeightList, probabilityTypeByDrawList, probabilityTypeCombinationId);
            // Save the probabilities combination by draw
            probabilityTypeCombinationByDrawDatasourcePort.save(probabilityTypeCombinationByDraw);
        }
    }

    private List<ProbabilityTypeCombinationByDraw> getNotDefinedProbabilityTypeCombinationByDrawList(Short drawTypeId, List<ProbabilityTypeCombination> probabilityTypeCombinationList,
                                                                                                     DrawList drawList, TDateInteger fromDate, TDateInteger toDate) {
        // Creates an initial list with all the possible values of combinations by  draws (starting on initialDrawDate)
        List<ProbabilityTypeCombinationByDraw> probabilityTypeCombinationByDrawPossibleList = new ArrayList<>();
        probabilityTypeCombinationList.forEach(probabilityTypeCombination -> {
            Draw draw = drawList.getDraw(fromDate);
            while (draw!=null && draw.getDrawDate().isLowerThan(toDate)) {
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
        Set<Pair<TDateInteger,Integer>> probabilityTypeCombinationByDrawExistingSet = probabilityTypeCombinationByDrawExistingList.stream()
                .map(p -> new Pair<>(p.getDrawDate(),p.getProbabilityTypeCombinationId()))
                .collect(Collectors.toSet());

        // Return the possible list without the existent ones
        return probabilityTypeCombinationByDrawPossibleList.parallelStream()
                .filter(a -> !probabilityTypeCombinationByDrawExistingSet.contains(new Pair<>(a.getDrawDate(),a.getProbabilityTypeCombinationId())))
                .toList();
    }

    private Map<TDateInteger, List<ProbabilityTypeByDraw>> probabilityTypeByDrawDatasourcePort(Short drawTypeId) {
        // Get All the probabilities values for a draw type
        List<ProbabilityTypeByDraw> allProbabilityTypeByDrawList = probabilityTypeByDrawDatasourcePort.findByDrawTypeId(drawTypeId);
        return allProbabilityTypeByDrawList.stream().collect(Collectors.groupingBy(ProbabilityTypeByDraw::getDrawDate, Collectors.mapping(Function.identity(), Collectors.toList())));
    }

    private ProbabilityTypeCombinationByDraw calculateCombinationProbabilityValues(Short drawTypeId, TDateInteger drawDate, List<ProbabilityTypeCombinationWeight> probabilityTypeCombinationWeightList,
                                                                                   List<ProbabilityTypeByDraw> probabilityTypeByDrawList, Integer probabilityTypeCombinationId) {
        List<BigDecimal> probabilitiesByCombinationList = new ArrayList<>(Collections.nCopies(49, new BigDecimal(0)));

        if (probabilityTypeByDrawList != null) {
            for (ProbabilityTypeCombinationWeight probabilityTypeCombinationWeight : probabilityTypeCombinationWeightList) {
                ProbabilityTypeByDraw probabilityTypeByDraw = probabilityTypeByDrawList.stream().filter(type -> type.getProbabilityType().getId().equals(probabilityTypeCombinationWeight.getProbabilityType().getId())).findFirst().orElse(null);
                if (probabilityTypeByDraw != null) {
                    BigDecimal multiplicand = probabilityTypeCombinationWeight.getWeight();
                    List<BigDecimal> probabilitiesByTypeList = probabilityTypeByDraw.getNumberList();
                    for (int cont = 0; cont < 49; cont++) {
                        probabilitiesByCombinationList.set(cont, probabilitiesByCombinationList.get(cont).add(probabilitiesByTypeList.get(cont).multiply(multiplicand)));
                    }
                }
            }
        }

        probabilitiesByCombinationList = probabilitiesByCombinationList.stream()
                .map(bd -> bd.setScale(10, RoundingMode.HALF_EVEN))
                .collect(Collectors.toList());

        return ProbabilityTypeCombinationByDraw.builder()
                .drawDate(drawDate)
                .drawTypeId(drawTypeId)
                .probabilityTypeCombinationId(probabilityTypeCombinationId)
                .numberList(probabilitiesByCombinationList)
                .build();
    }

    private List<ProbabilityTypeCombination> getActiveSimpleCombinationTypeList() {
        return probabilityTypeCombinationDatasourcePort.getAllSimpleProbabilityTypeCombination();
    }
}
