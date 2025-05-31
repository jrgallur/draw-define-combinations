package com.draw_define_combinations.models;

import com.draw_define_combinations.fakers.TNumberListFaker;
import com.draw_define_combinations.models.types.TDateInteger;
import com.draw_define_combinations.models.types.TNumberList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrawTest {


    @Test
    void testConstructor() {
        TDateInteger date = TDateInteger.valueOfUnknownFormat("20240316");
        TNumberList numbers = TNumberListFaker.getTNumberList();
        int complementary = 10;

        Draw draw = new Draw(date, numbers, complementary);
        assertNotNull(draw);
        assertEquals("20240316", draw.getDate().toString());
        Assertions.assertEquals(numbers, draw.getNumbers());
        assertEquals(complementary, draw.getComplementary());
    }

    @Test
    void testToString() {
        TDateInteger date = TDateInteger.valueOfUnknownFormat("20240316");
        TNumberList numbers = new TNumberList(TNumberListFaker.getTNumberList());
        byte complementary = 10;

        Draw draw = new Draw(date, numbers, complementary);
        assertEquals("[20240316, [01, 02, 03, 04, 05, 06, C = 10]]", draw.toString());
    }

    @Test
    void testContains() {
        TDateInteger date = TDateInteger.valueOfUnknownFormat("20240316");
        TNumberList numbers = TNumberListFaker.getTNumberList();
        byte complementary = 10;

        Draw draw = new Draw(date, numbers, complementary);
        assertTrue(draw.contains((byte) 2, true));
        assertFalse(draw.contains((byte) 9, true));
    }

    @Test
    void testEquals() {
        TDateInteger date1 = TDateInteger.valueOfUnknownFormat("20240316");
        TNumberList numbers1 = TNumberListFaker.getTNumberList();
        byte complementary1 = 10;

        Draw draw1 = new Draw(date1, numbers1, complementary1);

        TDateInteger date2 = TDateInteger.valueOfUnknownFormat("20240316");
        TNumberList numbers2 = TNumberListFaker.getTNumberList();
        byte complementary2 = 10;

        Draw draw2 = new Draw(date2, numbers2, complementary2);

        assertEquals(draw1, draw2);
    }

    @Test
    void testGetCoincidences() {
        TNumberList numbers1 = TNumberListFaker.getTNumberList();
        byte complementary1 = 10;

        TNumberList numbers2 = TNumberListFaker.getTNumberList();
        byte complementary2 = 11;

        NumberCombination combination1 = new NumberCombination(numbers1, complementary1);
        NumberCombination combination2 = new NumberCombination(numbers2, complementary2);

        Draw draw1 = new Draw(TDateInteger.valueOfUnknownFormat("20240316"), numbers1, complementary1);
        Draw draw2 = new Draw(TDateInteger.valueOfUnknownFormat("20240317"), numbers2, complementary2);

        assertEquals(7, draw1.getCoincidences(combination1, true));
        assertEquals(6, draw1.getCoincidences(combination2, true));
        assertEquals(6, draw2.getCoincidences(combination2, false));
    }

    @Test
    void validateGetNumbers() {
        TNumberList numbers1 = TNumberListFaker.getTNumberList();
        byte complementary1 = 10;

        Draw draw1 = new Draw(TDateInteger.valueOfUnknownFormat("20240316"), numbers1, complementary1);
        assertEquals(1, draw1.getNumber(1));
    }
}