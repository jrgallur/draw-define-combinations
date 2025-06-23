package com.draw_define_combinations.mappers;

import com.draw_define_combinations.models.*;
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
            List<ProbabilityTypeCombinationWeightMO> weightMOList = new ArrayList<>(domain.getCombinationList()
                    .stream()
                    .map(weight -> {
                        ProbabilityTypeCombinationWeightMO weightMO = new ProbabilityTypeCombinationWeightMO();
                        weightMO.setProbabilityTypeCombinationId(result);
                        weightMO.setProbabilityTypeCode(weight.getProbabilityType().getCode());
                        weightMO.setWeight(weight.getWeight());
                        return weightMO;
                    })
                    .toList());

            result.setProbabilityTypeCombinationWeightMOList(weightMOList);
        }

        return result;
    }

    default ProbabilityTypeCombination toDomain(ProbabilityTypeCombinationMO model) {
        if (model == null) {
            return null;
        }

        return ProbabilityTypeCombination.builder()
                .code(model.getCode())
                .combinationList(model.getProbabilityTypeCombinationWeightMOList().stream().map(this::toDomain).toList())
                .build();
    }

    default ProbabilityTypeCombinationWeight toDomain(ProbabilityTypeCombinationWeightMO model) {
        return ProbabilityTypeCombinationWeight.builder()
                .probabilityType(ProbabilityType.builder()
                        .code(model.getProbabilityTypeCode())
                        .build())
                .weight(model.getWeight())
                .build();
    }
}