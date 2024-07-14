package org.bertvn.data;

import java.util.*;

public class SudokuCell {

    private final List<SudokuGroup> groups = new ArrayList<>();
    private final List<Integer> possibleNumbers;
    private final int id;

    private int number;

    public SudokuCell(int number, int id) {
        this.number = number;
        this.id = id;
        possibleNumbers = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean hasValue() {
        return number != 0;
    }

    public void addContainer(SudokuGroup sudokuGroup) {
        groups.add(sudokuGroup);
    }

    public Collection<SudokuGroup> getContainers() {
        return groups;
    }

    public List<Integer> getPossibleNumbers() {
        return possibleNumbers;
    }

    public void setPossibleNumbers(List<Integer> possibleNumbers) {
        this.possibleNumbers.clear();
        this.possibleNumbers.addAll(possibleNumbers);
    }

    public void removePossibleNumber(Integer number) {
        possibleNumbers.remove(number);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuCell cell = (SudokuCell) o;
        return number == cell.number && id == cell.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, id);
    }
}
