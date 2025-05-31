package com.draw_define_combinations.models;


import com.draw_define_combinations.models.id.ProbabilityTypeWeightId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "probability_type_weight")
@IdClass(ProbabilityTypeWeightId.class)
public class ProbabilityTypeWeightMO {
    @Id
    @ManyToOne
    @JoinColumn(name="id", nullable=false)
    private ProbabilityTypeCombinationMO probabilityTypeCombinationId;

    @Id
    private String probabilityTypeCode;

    BigDecimal weight;
}
