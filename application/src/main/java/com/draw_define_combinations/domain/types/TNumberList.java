package com.draw_define_combinations.domain.types;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

@ToString
@EqualsAndHashCode
public class TNumberList {
    private byte[] numbers;

    /**
     * Constructor
     */
    public TNumberList() {
        numbers = new byte[0];
    }

    /**
     * Constructor
     */
    public TNumberList(List<Integer> numberList) {
        numbers = new byte[numberList.size()];
        for (int index = 0; index < numberList.size(); index++) {
            numbers[index] = numberList.get(index).byteValue();
        }
    }

    /**
     * Constructor
     */
    public TNumberList(TNumberList numberList) {
        numbers = numberList.getNumbersAsRaw();
    }

    public TNumberList(int[] numberList) {
        numbers = new byte[numberList.length];
        for (int index = 0; index < numberList.length; index++) {
            numbers[index] = (byte) numberList[index];
        }
    }

    public TNumberList(byte[] numberList) {
        numbers = Arrays.copyOfRange(numberList, 0, numberList.length);
    }

    public void add(int value) {
        numbers = Arrays.copyOf(numbers, numbers.length + 1);
        numbers[numbers.length - 1] = (byte) value;
    }

    public int get(int index) {
        return numbers[index - 1];
    }

    public int size() {
        return numbers.length;
    }

    public List<Integer> getList() {
        return Arrays.stream(ArrayUtils.toObject(numbers))
                .map(Byte::intValue) // Convertir cada Byte a su valor int
                .toList();
    }

    public void sort() {
        Arrays.sort(numbers);
    }

    public void set(int index, int value) {
        numbers[index - 1] = (byte) value;
    }

    public TNumberList subList(int fromIndex, int toIndex) {
        return new TNumberList(subListAsRaw(fromIndex - 1, toIndex));
    }

    public byte[] subListAsRaw(int fromIndex, int toIndex) {
        return Arrays.copyOfRange(numbers, fromIndex, toIndex);
    }

    protected byte[] getNumbersAsRaw() {
        return numbers;
    }

}