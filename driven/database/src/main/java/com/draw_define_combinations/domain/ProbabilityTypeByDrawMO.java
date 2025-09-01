package com.draw_define_combinations.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "probability_type_by_draw")
public class ProbabilityTypeByDrawMO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private Integer drawDate;

    @Column
    private Short drawTypeId;

    @Column(name = "values")
    private String values;

    @Column(name = "probability_type_id")
    private Integer probabilityTypeId;
}
