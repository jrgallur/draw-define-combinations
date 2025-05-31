package com.draw_define_combinations.adapters;

import com.draw_define_combinations.mappers.NumberProbabilityTypeMapper;
import com.draw_define_combinations.models.NumberProbabilityType;
import com.draw_define_combinations.ports.driven.NumberProbabilityTypePort;
import com.draw_define_combinations.repositories.NumberProbabilityTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class NumberProbabilityTypeAdapter implements NumberProbabilityTypePort {
    private final NumberProbabilityTypeRepository repository;
    private final NumberProbabilityTypeMapper mapper;


    @Override
    public NumberProbabilityType getByCode(String code) {
        return mapper.toDomain(repository.findByCode(code).orElse(null));
    }

    @Override
    public List<NumberProbabilityType> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}
