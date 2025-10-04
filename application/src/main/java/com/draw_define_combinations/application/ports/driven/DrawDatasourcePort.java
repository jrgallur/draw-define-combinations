package com.draw_define_combinations.application.ports.driven;

import com.draw_define_combinations.domain.DrawList;

import java.util.List;


public interface DrawDatasourcePort {
    /**
     * Get the draw list
     *
     * @return Draw list
     */
    DrawList getDrawListByDrawTypeId(Short drawTypeId);

    List<Short> getDrawTypeIdList();
}