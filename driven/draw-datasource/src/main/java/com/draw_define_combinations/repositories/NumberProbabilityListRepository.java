package com.draw_define_combinations.repositories;


import com.draw_define_combinations.models.DrawProbabilityTypeMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NumberProbabilityListRepository extends JpaRepository<DrawProbabilityTypeMO, Short> {
    List<DrawProbabilityTypeMO> findByNumberProbabilityTypeId(Short numberProbabilityType);
}