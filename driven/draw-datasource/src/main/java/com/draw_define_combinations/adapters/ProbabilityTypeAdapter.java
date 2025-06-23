package com.draw_define_combinations.adapters;

import com.draw_define_combinations.mappers.DrawProbabilityTypeMapper;
import com.draw_define_combinations.models.DrawProbabilityType;
import com.draw_define_combinations.ports.driven.ProbabilityTypePort;
import com.draw_define_combinations.repositories.NumberProbabilityListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ProbabilityTypeAdapter implements ProbabilityTypePort {
    private final NumberProbabilityListRepository repository;
    private final DrawProbabilityTypeMapper mapper;

    @Override
    public List<DrawProbabilityType> findByProbabilityTypeId(Short probabilityTypeId) {
        return repository.findByNumberProbabilityTypeId(probabilityTypeId).stream().map(mapper::toDomain).toList();
    }
}
