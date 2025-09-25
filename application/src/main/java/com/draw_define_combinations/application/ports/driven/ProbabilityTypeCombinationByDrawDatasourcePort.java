package com.draw_define_combinations.application.ports.driven;

import com.draw_define_combinations.domain.ProbabilityTypeCombinationByDraw;

import java.util.List;

public interface ProbabilityTypeCombinationByDrawDatasourcePort {
    void save(ProbabilityTypeCombinationByDraw probabilityTypeCombinationByDraw);
    List<ProbabilityTypeCombinationByDraw> findByDrawTypeIdSimple(Short drawTypeId);
}
