package com.draw_define_combinations.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "draw")
public class DrawMO {
    // Fecha del sorteo
    @Id
    private Integer date;
    // Números del sorteo, sin el complementario
    private Byte number1;
    private Byte number2;
    private Byte number3;
    private Byte number4;
    private Byte number5;
    private Byte number6;
    // Número complementario del sorteo
    private Byte complementary;
}