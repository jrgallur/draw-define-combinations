package com.draw_define_combinations.models;

import com.draw_define_combinations.models.types.TDateInteger;
import com.draw_define_combinations.models.types.TNumberList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DrawListTest {

    private DrawList drawList;
    private List<Draw> draws;

    @BeforeEach
    void setUp() {
        draws = new ArrayList<>();
        TDateInteger date1 = new TDateInteger("2023-01-01", "yyyy-mm-dd");
        TDateInteger date2 = new TDateInteger("2023-01-02", "yyyy-mm-dd");
        TDateInteger date3 = new TDateInteger("2023-01-03", "yyyy-mm-dd");

        TNumberList numbers1 = new TNumberList(Arrays.asList(1, 2, 3, 4, 5, 6));

        Draw draw1 = new Draw(date1, numbers1, 1);
        Draw draw2 = new Draw(date2, numbers1, 2);
        Draw draw3 = new Draw(date3, numbers1, 3);
        draws.add(draw1);
        draws.add(draw2);
        draws.add(draw3);
        drawList = new DrawList(draws);
    }

    @Test
    void testGetOrderedNumber() {
        TDateInteger date1 = new TDateInteger("2023-01-01", "yyyy-mm-dd");
        TDateInteger date2 = new TDateInteger("2023-01-02", "yyyy-mm-dd");
        TDateInteger date3 = new TDateInteger("2023-01-03", "yyyy-mm-dd");
        assertEquals(1, drawList.getOrderedNumber(date1));
        assertEquals(2, drawList.getOrderedNumber(date2));
        assertEquals(3, drawList.getOrderedNumber(date3));
    }

    @Test
    void testGetPreviousDate() {
        TDateInteger date1 = new TDateInteger("2023-01-01", "yyyy-mm-dd");
        TDateInteger date2 = new TDateInteger("2023-01-02", "yyyy-mm-dd");
        TDateInteger date3 = new TDateInteger("2023-01-03", "yyyy-mm-dd");
        assertNull(drawList.getPreviousDate(date1));
        Assertions.assertEquals(date1, drawList.getPreviousDate(date2));
        Assertions.assertEquals(date2, drawList.getPreviousDate(date3));
    }

    @Test
    void testGetNextDate() {
        TDateInteger date1 = new TDateInteger("2023-01-01", "yyyy-mm-dd");
        TDateInteger date2 = new TDateInteger("2023-01-02", "yyyy-mm-dd");
        TDateInteger date3 = new TDateInteger("2023-01-03", "yyyy-mm-dd");

        Assertions.assertEquals(date2, drawList.getNextDate(date1));
        Assertions.assertEquals(date3, drawList.getNextDate(date2));
        assertNull(drawList.getNextDate(date3));
    }

    @Test
    void testGetDraw() {
        TDateInteger date1 = new TDateInteger("2023-01-01", "yyyy-mm-dd");
        TDateInteger date2 = new TDateInteger("2023-01-02", "yyyy-mm-dd");
        TDateInteger date3 = new TDateInteger("2023-01-03", "yyyy-mm-dd");
        assertEquals(draws.get(0), drawList.getDraw(date1));
        assertEquals(draws.get(1), drawList.getDraw(date2));
        assertEquals(draws.get(2), drawList.getDraw(date3));
    }

    @Test
    void testExistsDrawInDate() {
        TDateInteger date1 = new TDateInteger("2023-01-01", "yyyy-mm-dd");
        TDateInteger date2 = new TDateInteger("2023-01-02", "yyyy-mm-dd");
        TDateInteger date3 = new TDateInteger("2023-01-03", "yyyy-mm-dd");
        assertTrue(drawList.existsDrawInDate(date1));
        assertTrue(drawList.existsDrawInDate(date2));
        assertTrue(drawList.existsDrawInDate(date3));
        assertFalse(drawList.existsDrawInDate(new TDateInteger("2023-01-04", "yyyy-mm-dd")));
    }

    @Test
    void testAddNextDraw() {
        TDateInteger date4 = new TDateInteger("2023-01-04", "yyyy-mm-dd");
        Draw draw4 = new Draw(date4, new TNumberList(Arrays.asList(1, 2, 3, 4, 5, 6)), 4);
        drawList.addNextDraw(draw4);
        Assertions.assertEquals(date4.toInteger(), drawList.getLastDrawDate().toInteger());
        assertEquals(draw4, drawList.getDraw(date4));
    }

    @Test
    void testGetFirstDrawDate() {
        TDateInteger date1 = new TDateInteger("2023-01-01", "yyyy-mm-dd");
        Assertions.assertEquals(date1, drawList.getFirstDrawDate());
    }

    @Test
    void testGetLastDrawDate() {
        TDateInteger date3 = new TDateInteger("2023-01-03", "yyyy-mm-dd");
        Assertions.assertEquals(date3, drawList.getLastDrawDate());
    }
}