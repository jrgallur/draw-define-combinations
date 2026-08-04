package com.draw_define_combinations.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "probability_type_combination_by_draw", schema = "public")
public class ProbabilityTypeCombinationByDrawMO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prob_comb_draw_seq")
    @SequenceGenerator(
            name = "prob_comb_draw_seq",
            sequenceName = "probability_type_combination_by_draw_id_seq",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "draw_type_id", nullable = false)
    private Short drawTypeId;

    @Column(name = "draw_date", nullable = false)
    private Integer drawDate;

    @Column(name = "probability_type_combination_id", nullable = false)
    private Integer probabilityTypeCombinationId;

    @Column(name = "values", length = 700)
    private String values;
}