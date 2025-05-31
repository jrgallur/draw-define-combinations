package com.draw_define_combinations.fakers;

import com.draw_define_combinations.models.Draw;
import com.draw_define_combinations.models.types.TDateInteger;
import com.draw_define_combinations.models.types.TNumberList;

public class DrawFaker {
    public static Draw getDraw() {
        TDateInteger date = TDateInteger.valueOfUnknownFormat("20240316");
        TNumberList numbers = TNumberListFaker.getTNumberList();
        byte complementary = 10;

        return new Draw(date, numbers, complementary);
    }
}