package com.draw_define_combinations.repositories;

import com.draw_define_combinations.models.NumberProbabilityListMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NumberProbabilityListRepository extends JpaRepository<NumberProbabilityListMO, Short> {
    List<NumberProbabilityListMO> findByNumberProbabilityTypeId(Short numberProbabilityType);
    Optional<NumberProbabilityListMO> findByNumberProbabilityTypeIdAndDate(Short numberProbabilityType, Integer date);
    boolean existsByNumberProbabilityTypeIdAndDate(Short numberProbabilityType, Integer date);
}