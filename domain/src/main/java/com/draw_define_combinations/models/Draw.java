package com.draw_define_combinations.models;

import com.draw_define_combinations.models.types.TDateInteger;
import com.draw_define_combinations.models.types.TNumberList;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class Draw implements Comparable<Draw> {
    private TDateInteger drawDate;
    private NumberCombination drawNumbers;

    /**
     * Constructor privado para evitar que pueda ser invocado sin parámetros
     */
    @SuppressWarnings("unused")
    private Draw() {

    }

    /**
     * Constructor con una lista de números
     *
     * @param date          Fecha del sorteo
     * @param numbers       Lista de números, sin el complementario
     * @param complementary El número complementario
     */
    public Draw(String date, TNumberList numbers, int complementary) {
        this.drawDate = TDateInteger.valueOfUnknownFormat(date);
        drawNumbers = new NumberCombination(numbers, complementary);
    }

    public Draw(TDateInteger date, TNumberList numbers, int complementary) {
        this.drawDate = date;
        drawNumbers = new NumberCombination(numbers, complementary);
    }

    /**
     * Devuelve un string con la representación del objeto
     */
    public String toString() {
        return "[" +
                drawDate +
                "," +
                drawNumbers.toString() +
                "]";
    }

    /**
     * Indica si un número existe en el sorteo o no
     *
     * @param number           El número a buscar
     * @param useComplementary Indica si se debe usar o no el complementario para la comprobación
     * @return true si el número está en el sorteo, usando o no el complementario
     */
    public boolean contains(int number, boolean useComplementary) {
        return drawNumbers.contains(number, useComplementary);
    }

    /**
     * Devuelve la fecha del sorteo
     *
     * @return La fecha del sorteo
     */
    public TDateInteger getDate() {
        return drawDate;
    }

    /**
     * Devuelve la lista de números del sorteo
     *
     * @return La lista de números del sorteo
     */
    public TNumberList getNumbers() {
        return drawNumbers.getNumbers();
    }

    /**
     * Returns the list of numbers of the draw, including the complementary
     *
     * @return The draw number list
     */
    public TNumberList getNumbersWC() {
        return drawNumbers.getNumbersWC();
    }

    /**
     * Devuelve el objeto con la combinación (y el complementario)
     *
     * @return El objeto con la combinación (y el complementario)
     */
    public NumberCombination getNumberCombination() {
        return drawNumbers;
    }

    /**
     * Devuelve uno de los números, del 1 al 6
     */
    public int getNumber(int index) {
        return getNumberCombination().getNumbersAsArray()[index - 1];
    }

    /**
     * Devuelve el complementario
     *
     * @return El complementario del sorteo
     */
    public byte getComplementary() {
        return drawNumbers.getComplementary();
    }

    /**
     * Compara dos sorteos para poder ordenarlos. Se basa en la fecha
     *
     * @param other El otro sorteo con el que comparar
     */
    @Override
    public int compareTo(Draw other) {
        return this.drawDate.compareTo(other.drawDate);
    }

    /**
     * Compara dos sorteos para poder indicar si son iguales o no, revisando todos los campos: fechas, números y complementario
     *
     * @param other El otro sorteo con el que comparar
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        Draw otherDraw = (Draw) other;
        return this.drawDate.equals(otherDraw.drawDate) && drawNumbers.equals(otherDraw.drawNumbers, false);
    }

    @Override
    public int hashCode() {
        return drawDate.hashCode();
    }

    /**
     * Devuelve el número de coincidencias que hay entre dos sorteos, teniendo en cuenta (o no) el complementario
     *
     * @param other            El otro conjunto de números y complementario a comparar
     * @param useComplementary Si se va a usar o no el complementario en la comparación
     * @return El número de valores que están en un sorteo y en el otro
     */
    public int getCoincidences(NumberCombination other, boolean useComplementary) {
        return drawNumbers.getCoincidences(other, useComplementary);
    }

    /**
     * Devuelve el número de coincidencias que hay entre dos sorteos, teniendo en cuenta (o no) el complementario
     *
     * @param other            El otro sorteo
     * @param useComplementary Si se va a usar o no el complementario en la comparación
     * @return El número de valores que están en un sorteo y en el otro
     */
    public int getCoincidences(Draw other, boolean useComplementary) {
        return drawNumbers.getCoincidences(other.drawNumbers, useComplementary);
    }
}