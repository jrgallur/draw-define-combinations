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
@Table(name = "probability_type_combination_weight")
@IdClass(ProbabilityTypeWeightId.class)
public class ProbabilityTypeCombinationWeightMO {
    @Id
    @Column(name = "probability_type_combination_id")
    private Integer probabilityTypeCombinationId;

    @Id
    @Column(name = "probability_type_id")
    private Integer probabilityTypeId;

    @ManyToOne
    @JoinColumn(name="probability_type_combination_id", nullable=false, insertable = false, updatable = false)
    private ProbabilityTypeCombinationMO probabilityTypeCombination;

    @ManyToOne
    @JoinColumn(name="probability_type_id", nullable=false, insertable = false, updatable = false)
    private ProbabilityTypeMO probabilityType;

    @Column(name="probability_type_code")
    private String probabilityTypeCode;

    BigDecimal weight;
}
