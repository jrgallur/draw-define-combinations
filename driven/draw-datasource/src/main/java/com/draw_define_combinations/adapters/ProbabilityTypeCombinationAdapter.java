package com.draw_define_combinations.adapters;

import com.draw_define_combinations.mappers.ProbabilityTypeCombinationMapper;
import com.draw_define_combinations.models.ProbabilityTypeCombination;
import com.draw_define_combinations.ports.driven.ProbabilityTypeCombinationPort;
import com.draw_define_combinations.repositories.ProbabilityTypeCombinationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ProbabilityTypeCombinationAdapter implements ProbabilityTypeCombinationPort {
    private final ProbabilityTypeCombinationRepository repository;
    private final ProbabilityTypeCombinationMapper mapper;

    @Override
    public boolean existsByCode(String code) {
        return repository.findByCode(code).isPresent();
    }

    @Override
    public void upsert(ProbabilityTypeCombination probabilityTypeCombination) {
        repository.save(mapper.toModel(probabilityTypeCombination));
    }

    @Override
    public List<ProbabilityTypeCombination> getAllProbabilityTypeCombinationList() {
        return repository.findAllWithProbabilityTypeCombinationWeightMOList().stream().map(mapper::toDomain).toList();
    }
}
