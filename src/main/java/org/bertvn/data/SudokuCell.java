package org.bertvn.data;

import java.util.*;

public class SudokuCell {

    private final Set<SudokuGroup> groups = new HashSet<>();

    private int number;
    private List<Integer> possibleNumbers;

    public SudokuCell(int number) {
        this.number = number;
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
        this.possibleNumbers = possibleNumbers;
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
        return number == cell.number;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }
}
