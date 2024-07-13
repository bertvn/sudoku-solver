package org.bertvn.data;

import java.util.*;

public class SudokuGroup {

    private final List<SudokuCell> cells;

    public SudokuGroup() {
        this.cells = new ArrayList<>();
    }

    public void addCell(SudokuCell cell) {
        this.cells.add(cell);
        cell.addContainer(this);
    }

    public boolean isValid() {
        if(cells.size() != 9) {
            return false;
        }
        return cells.stream()
                .filter(SudokuCell::hasValue)
                .map(SudokuCell::getNumber)
                .allMatch(new HashSet<>()::add);
    }

    public boolean isDone() {
        if(cells.size() != 9) {
            return false;
        }
        Set<Integer> value = new HashSet<>();
        return cells.stream()
                .allMatch(cell -> cell.hasValue() && value.add(cell.getNumber()));
    }

    public List<SudokuCell> getCells() {
        return cells;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuGroup that = (SudokuGroup) o;
        return Objects.equals(cells, that.cells);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cells);
    }
}
