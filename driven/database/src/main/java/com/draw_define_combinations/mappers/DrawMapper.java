package com.draw_define_combinations.mappers;
import com.draw_define_combinations.domain.Draw;
import com.draw_define_combinations.domain.DrawMO;
import com.draw_define_combinations.domain.NumberCombination;
import com.draw_define_combinations.domain.types.TDateInteger;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DrawMapper {
    default Draw toDomain(DrawMO drawMO) {
        NumberCombination numberCombination = new NumberCombination(new byte[]{drawMO.getNumber1(), drawMO.getNumber2(), drawMO.getNumber3(), drawMO.getNumber4(), drawMO.getNumber5(), drawMO.getNumber6()}, drawMO.getComplementary());
        return Draw.builder()
                .drawDate(new TDateInteger(drawMO.getDrawDate()))
                .drawTypeId(drawMO.getDrawTypeId())
                .drawNumbers(numberCombination)
                .build();
    }
}