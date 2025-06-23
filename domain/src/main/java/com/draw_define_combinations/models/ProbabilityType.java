package com.draw_define_combinations.models;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class ProbabilityType {
    private Short id;
    private String code;
    private String description;
}
