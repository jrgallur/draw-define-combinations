package com.draw_define_combinations.adapters;

import com.draw_define_combinations.mappers.NumberProbabilityListMapper;
import com.draw_define_combinations.models.NumberProbabilityList;
import com.draw_define_combinations.models.NumberProbabilityListMO;
import com.draw_define_combinations.models.types.TDateInteger;
import com.draw_define_combinations.ports.driven.NumberProbabilityListPort;
import com.draw_define_combinations.repositories.NumberProbabilityListRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class NumberProbabilityListAdapter implements NumberProbabilityListPort {
    private final NumberProbabilityListRepository repository;
    private final NumberProbabilityListMapper mapper;

    @Override
    public void upsert(NumberProbabilityList numberProbabilityList) {
        NumberProbabilityListMO numberProbabilityListMO = mapper.toModel(numberProbabilityList);
        Optional<NumberProbabilityListMO> numberProbabilityListMOList = repository.findByNumberProbabilityTypeIdAndDate(numberProbabilityListMO.getNumberProbabilityTypeId(), numberProbabilityListMO.getDate());
        if (numberProbabilityListMOList.isPresent()) {
            numberProbabilityListMOList.get().setValues(numberProbabilityListMO.getValues());
            repository.save(numberProbabilityListMOList.get());
        } else {
            repository.save(numberProbabilityListMO);
        }
    }

    @Override
    public boolean existsByNumberProbabilityTypeAndDrawDate(Short numberProbabilityType, TDateInteger drawdate) {
        return repository.existsByNumberProbabilityTypeIdAndDate(numberProbabilityType, drawdate.toInteger());
    }

}
