package com.draw_define_combinations.adapters;

import com.draw_define_combinations.mappers.ProbabilityTypeCombinationMapper;
import com.draw_define_combinations.domain.ProbabilityTypeCombination;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationMO;
import com.draw_define_combinations.application.ports.driven.ProbabilityTypeCombinationPort;
import com.draw_define_combinations.repositories.ProbabilityTypeCombinationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    public ProbabilityTypeCombination upsert(ProbabilityTypeCombination probabilityTypeCombination) {
        ProbabilityTypeCombinationMO model = mapper.toModel(probabilityTypeCombination);
        ProbabilityTypeCombinationMO result = repository.save(model);
        return mapper.toDomain(result);
    }

    @Override
    public List<ProbabilityTypeCombination> getAllSimpleProbabilityTypeCombination() {
        return repository.getAllSimpleProbabilityTypeCombination().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<ProbabilityTypeCombination> findByCode(String code) {
        return repository.findByCode(code).map(mapper::toDomain);
    }
}
