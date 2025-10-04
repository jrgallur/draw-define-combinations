package com.draw_define_combinations.domain.id;

import lombok.Data;

import java.io.Serializable;

@Data
public class DrawId implements Serializable {
    private Integer drawDate;
    private Short drawTypeId;
}
