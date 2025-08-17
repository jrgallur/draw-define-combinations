package com.draw_define_combinations.domain.id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProbabilityTypeCombinationWeightId implements Serializable {
    private Integer probabilityTypeCombinationId;
    private Integer probabilityTypeId;
}
