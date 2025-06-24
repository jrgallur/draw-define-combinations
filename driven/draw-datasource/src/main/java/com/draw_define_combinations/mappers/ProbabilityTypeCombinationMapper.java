package com.draw_define_combinations.mappers;

import com.draw_define_combinations.models.*;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = ProbabilityTypeCombinationWeightMapper.class)
public interface ProbabilityTypeCombinationMapper {
    default ProbabilityTypeCombinationMO toModel(ProbabilityTypeCombination domain) {
        if (domain == null) {
            return null;
        }

        ProbabilityTypeCombinationMO result = new ProbabilityTypeCombinationMO();
        result.setCode(domain.getCode());

        // Mapeamos la lista de pesos
        if (domain.getProbabilityTypeCombinationWeightList() != null) {
            List<ProbabilityTypeCombinationWeightMO> weightMOList = new ArrayList<>(domain.getProbabilityTypeCombinationWeightList()
                    .stream()
                    .map(weight -> {
                        ProbabilityTypeCombinationWeightMO weightMO = new ProbabilityTypeCombinationWeightMO();
                        weightMO.setProbabilityTypeCombination(result);
                        weightMO.setProbabilityTypeCombinationId(result.getId());
                        weightMO.setProbabilityTypeCode(weight.getProbabilityType().getCode());
                        weightMO.setProbabilityTypeId(weight.getProbabilityType().getId());
                        weightMO.setWeight(weight.getWeight());
                        return weightMO;
                    })
                    .toList());

            result.setProbabilityTypeCombinationWeightList(weightMOList);
        }

        return result;
    }
    ProbabilityTypeCombination toDomain(ProbabilityTypeCombinationMO mo);
}