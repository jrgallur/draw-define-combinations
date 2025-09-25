package com.draw_define_combinations.adapters;

import com.draw_define_combinations.application.ports.driven.ProbabilityTypeCombinationWeightDatasourcePort;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationMO;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationWeight;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationWeightMO;
import com.draw_define_combinations.mappers.ProbabilityTypeCombinationWeightMapper;
import com.draw_define_combinations.repositories.ProbabilityTypeCombinationWeightRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ProbabilityTypeCombinationWeightDatasourceAdapter implements ProbabilityTypeCombinationWeightDatasourcePort {
    private final ProbabilityTypeCombinationWeightRepository repository;
    private final ProbabilityTypeCombinationWeightMapper mapper;

    @Override
    public void saveAll(List<ProbabilityTypeCombinationWeight> probabilityTypeCombinationWeightList) {
        List<ProbabilityTypeCombinationWeightMO> probabilityTypeCombinationWeightMOList = (probabilityTypeCombinationWeightList.stream().map(mapper::toModel).toList());
        repository.saveAll(probabilityTypeCombinationWeightMOList);
    }

    @Override
    public void deleteByProbabilityTypeCombinationId(Integer probabilityTypeCombinationId) {
        repository.deleteByProbabilityTypeCombinationId(probabilityTypeCombinationId);
    }

    @Override
    public List<ProbabilityTypeCombinationWeight> findByProbabilityTypeCombination(Integer probabilityTypeCombinationId) {
        ProbabilityTypeCombinationMO probabilityTypeCombinationMO = ProbabilityTypeCombinationMO.builder()
                .id(probabilityTypeCombinationId)
                .build();
        return repository.findByProbabilityTypeCombination(probabilityTypeCombinationMO).stream().map(mapper::toDomain).toList();
    }
}
