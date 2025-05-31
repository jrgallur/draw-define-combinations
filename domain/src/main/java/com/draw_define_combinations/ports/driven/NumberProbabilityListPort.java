package com.draw_define_combinations.ports.driven;

import com.draw_define_combinations.models.NumberProbabilityList;
import com.draw_define_combinations.models.types.TDateInteger;

public interface NumberProbabilityListPort {
    void upsert(NumberProbabilityList numberProbabilityList);
    boolean existsByNumberProbabilityTypeAndDrawDate(Short numberProbabilityType, TDateInteger drawdate);
}
