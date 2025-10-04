package com.draw_define_combinations.adapters;

import com.draw_define_combinations.application.ports.driven.DrawDatasourcePort;
import com.draw_define_combinations.domain.DrawList;
import com.draw_define_combinations.mappers.DrawMapper;
import com.draw_define_combinations.repositories.DrawRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class DrawDatasourceAdapter implements DrawDatasourcePort {
    private final DrawMapper mapper;
    private final DrawRepository repository;

    public DrawList getDrawListByDrawTypeId(Short drawTypeId) {
        return new DrawList(new ArrayList<>(repository.findByDrawTypeId(drawTypeId).stream().map(mapper::toDomain).toList()));
    }

    @Override
    public List<Short> getDrawTypeIdList() {
        return repository.findDrawTypeList();
    }
}