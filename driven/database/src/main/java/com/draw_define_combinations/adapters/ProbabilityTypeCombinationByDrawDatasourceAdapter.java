package com.draw_define_combinations.adapters;

import com.draw_define_combinations.application.ports.driven.ProbabilityTypeCombinationByDrawDatasourcePort;
import com.draw_define_combinations.domain.ProbabilityTypeCombinationByDraw;
import com.draw_define_combinations.mappers.ProbabilityTypeCombinationByDrawMapper;
import com.draw_define_combinations.repositories.ProbabilityTypeCombinationByDrawRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ProbabilityTypeCombinationByDrawDatasourceAdapter implements ProbabilityTypeCombinationByDrawDatasourcePort {
    private final ProbabilityTypeCombinationByDrawRepository repository;
    private final ProbabilityTypeCombinationByDrawMapper mapper;

    @Override
    public void save(ProbabilityTypeCombinationByDraw probabilityTypeCombinationByDraw) {
        repository.save(mapper.toModel(probabilityTypeCombinationByDraw));
    }

    @Override
    public List<ProbabilityTypeCombinationByDraw> findByDrawTypeIdSimple(Short drawTypeId) {
        return repository.findByDrawTypeId(drawTypeId).stream().map(mapper::toDomainSimple).toList();
    }

}
