package com.draw_define_combinations.models;

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

    @Column(name = "date")
    private Integer date;

    @Column(name = "values")
    private String values;

    @Column(name = "probability_type_id")
    private Integer probabilityTypeId;
}
