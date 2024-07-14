package org.bertvn.data;

import java.util.ArrayList;
import java.util.List;

public class SudokuTriplet {

    private final List<SudokuCell> cellList;

    public SudokuTriplet() {
        this.cellList = new ArrayList<>();
    }

    public void addCell(SudokuCell cell) {
        this.cellList.add(cell);
    }

    public List<SudokuCell> getCellList() {
        return cellList;
    }
}
