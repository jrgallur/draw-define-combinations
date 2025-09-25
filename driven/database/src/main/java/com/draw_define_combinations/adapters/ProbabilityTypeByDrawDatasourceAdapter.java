package com.draw_define_combinations.adapters;

import com.draw_define_combinations.domain.types.TDateInteger;
import com.draw_define_combinations.mappers.ProbabilityTypeByDrawMapper;
import com.draw_define_combinations.domain.ProbabilityTypeByDraw;
import com.draw_define_combinations.application.ports.driven.ProbabilityTypeByDrawDatasourcePort;
import com.draw_define_combinations.repositories.ProbabilityTypeByDrawRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ProbabilityTypeByDrawDatasourceAdapter implements ProbabilityTypeByDrawDatasourcePort {
    private final ProbabilityTypeByDrawRepository repository;
    private final ProbabilityTypeByDrawMapper mapper;

    @Override
    public List<ProbabilityTypeByDraw> findByProbabilityTypeId(Integer probabilityTypeId) {
        return repository.findByProbabilityTypeId(probabilityTypeId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<ProbabilityTypeByDraw> findByDrawTypeIdAndDrawDate(Short drawTypeId, TDateInteger drawDate) {
        return repository.findByDrawTypeIdAndDrawDate(drawTypeId, drawDate.toInteger()).stream().map(mapper::toDomain).toList();
    }
}
