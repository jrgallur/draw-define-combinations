package com.draw_define_combinations.adapters;

import com.draw_define_combinations.domain.ProbabilityTypeCombinationWeightMO;
import com.draw_define_combinations.mappers.ProbabilityTypeCombinationWeightMapper;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationWeight;
import com.draw_define_combinations.application.ports.driven.ProbabilityTypeCombinationWeightPort;
import com.draw_define_combinations.repositories.ProbabilityTypeCombinationWeightRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ProbabilityTypeCombinationWeightAdapter implements ProbabilityTypeCombinationWeightPort {
    private final ProbabilityTypeCombinationWeightRepository repository;
    private final ProbabilityTypeCombinationWeightMapper mapper;

    @Override
    public void saveAll(List<ProbabilityTypeCombinationWeight> probabilityTypeCombinationWeightList) {
        List<ProbabilityTypeCombinationWeightMO> probabilityTypeCombinationWeightMOList = (probabilityTypeCombinationWeightList.stream().map(mapper::toModel).toList());
        repository.saveAll(probabilityTypeCombinationWeightMOList);
    }

    public void deleteByProbabilityTypeCombinationId(Integer probabilityTypeCombinationId) {
        repository.deleteByProbabilityTypeCombinationId(probabilityTypeCombinationId);
    }
}
