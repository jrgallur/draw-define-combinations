package com.draw_define_combinations.adapters;

import com.draw_define_combinations.application.ports.driven.ProbabilityTypeCombinationDatasourcePort;
import com.draw_define_combinations.domain.ProbabilityTypeCombination;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationMO;
import com.draw_define_combinations.mappers.ProbabilityTypeCombinationMapper;
import com.draw_define_combinations.repositories.ProbabilityTypeCombinationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ProbabilityTypeCombinationDatasourceAdapter implements ProbabilityTypeCombinationDatasourcePort {
    private final ProbabilityTypeCombinationRepository repository;
    private final ProbabilityTypeCombinationMapper mapper;

    @Override
    public List<String> findByCodeList(List<String> codeList) {
        return repository.findAllByCodeIn(codeList).stream().map(ProbabilityTypeCombinationMO::getCode).toList();
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
}
