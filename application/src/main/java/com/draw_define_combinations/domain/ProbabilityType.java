package com.draw_define_combinations.domain;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class ProbabilityType {
    private Integer id;
    private String code;
    private String description;
}
