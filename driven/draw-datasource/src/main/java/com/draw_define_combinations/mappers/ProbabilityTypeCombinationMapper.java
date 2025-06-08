package com.draw_define_combinations.mappers;

import com.draw_define_combinations.models.ProbabilityTypeCombination;
import com.draw_define_combinations.models.ProbabilityTypeCombinationMO;
import com.draw_define_combinations.models.ProbabilityTypeWeightMO;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProbabilityTypeCombinationMapper {
    default ProbabilityTypeCombinationMO toModel(ProbabilityTypeCombination domain) {
        if (domain == null) {
            return null;
        }

        ProbabilityTypeCombinationMO result = new ProbabilityTypeCombinationMO();
        result.setCode(domain.getCode());

        // Mapeamos la lista de pesos
        if (domain.getCombinationList() != null) {
            List<ProbabilityTypeWeightMO> weightMOList = new ArrayList<>(domain.getCombinationList()
                    .stream()
                    .map(weight -> {
                        ProbabilityTypeWeightMO weightMO = new ProbabilityTypeWeightMO();
                        weightMO.setProbabilityTypeCombinationId(result);
                        weightMO.setProbabilityTypeCode(weight.getNumberProbabilityType().getCode());
                        weightMO.setWeight(weight.getWeight());
                        return weightMO;
                    })
                    .toList());

            result.setProbabilityTypeWeightMOList(weightMOList);
        }

        return result;
    }
}