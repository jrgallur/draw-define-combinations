package com.draw_define_combinations.application.ports.driven;

import com.draw_define_combinations.domain.ProbabilityTypeByDraw;
import com.draw_define_combinations.domain.types.TDateInteger;

import java.util.List;

public interface ProbabilityTypeByDrawDatasourcePort {
    List<ProbabilityTypeByDraw> findByProbabilityTypeId(Integer probabilityTypeId);
    List<ProbabilityTypeByDraw> findByDrawTypeIdAndDrawDate(Short drawTypeId, TDateInteger drawDate);
}
