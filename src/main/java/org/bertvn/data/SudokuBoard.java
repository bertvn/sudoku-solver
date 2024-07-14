package org.bertvn.data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SudokuBoard {

    public enum Locations {
        _1_1,
        _1_2,
        _1_3,
        _2_1,
        _2_2,
        _2_3,
        _3_1,
        _3_2,
        _3_3,
    }

    private final List<SudokuGroup> blocks = new ArrayList<>();
    private final List<SudokuGroup> rows = new ArrayList<>();
    private final List<SudokuGroup> columns = new ArrayList<>();

    Map<Locations, SudokuGroup> blockMap = new EnumMap<>(Locations.class);

    public SudokuBoard(List<List<Integer>> integerBoard) {
        generateBoard(integerBoard);
    }

    private void generateBoard(List<List<Integer>> integerBoard) {
        Locations[] values = Locations.values();
        for(int i = 0; i < 9; i++) {
            rows.add(new SudokuGroup());
            columns.add(new SudokuGroup());
            SudokuGroup block = new SudokuGroup();
            blocks.add(block);
            blockMap.put(values[i], block);
        }

        int id = 0;
        for(int i = 0; i < integerBoard.size(); i++) {
            List<Integer> rowValues = integerBoard.get(i);
            SudokuGroup row = rows.get(i);
            for(int j = 0; j < rowValues.size(); j++) {
                SudokuCell cell = new SudokuCell(rowValues.get(j), id);
                id++;
                columns.get(j).addCell(cell);
                row.addCell(cell);
                blockMap.get(toLocations(i, j)).addCell(cell);
            }
        }
    }

    public boolean isDone() {
        List<Boolean> results = Stream.of(rows, columns, blocks)
                .flatMap(List::stream)
                .map(SudokuGroup::isDone)
                .distinct()
                .toList();
        return results.size() == 1 && results.get(0);
    }

    public boolean isValid() {
        List<Boolean> results = Stream.of(rows, columns, blocks)
                .flatMap(List::stream)
                .map(SudokuGroup::isValid)
                .distinct()
                .toList();
        return results.size() == 1 && results.get(0);
    }

    public List<SudokuCell> getAllCells() {
        return rows.stream()
                .flatMap(row -> row.getCells().stream())
                .collect(Collectors.toList());
    }

    public List<SudokuGroup> getBlocks() {
        return blocks;
    }

    private Locations toLocations(int row, int column) {
        return Locations.valueOf(toLocationsPart(row) + toLocationsPart(column));
    }

    private String toLocationsPart(int index) {
        return switch(index + 1) {
            case 1, 2, 3 -> "_1";
            case 4, 5, 6 -> "_2";
            case 7, 8, 9 -> "_3";
            default -> "";
        };
    }

    public void print() {
        System.out.println("+-------+-------+-------+");
        System.out.printf("+ %d %d %d + %d %d %d + %d %d %d +%n", rows.get(0).getCells().stream().map(SudokuCell::getNumber).toArray());
        System.out.printf("+ %d %d %d + %d %d %d + %d %d %d +%n", rows.get(1).getCells().stream().map(SudokuCell::getNumber).toArray());
        System.out.printf("+ %d %d %d + %d %d %d + %d %d %d +%n", rows.get(2).getCells().stream().map(SudokuCell::getNumber).toArray());
        System.out.println("+-------+-------+-------+");
        System.out.printf("+ %d %d %d + %d %d %d + %d %d %d +%n", rows.get(3).getCells().stream().map(SudokuCell::getNumber).toArray());
        System.out.printf("+ %d %d %d + %d %d %d + %d %d %d +%n", rows.get(4).getCells().stream().map(SudokuCell::getNumber).toArray());
        System.out.printf("+ %d %d %d + %d %d %d + %d %d %d +%n", rows.get(5).getCells().stream().map(SudokuCell::getNumber).toArray());
        System.out.println("+-------+-------+-------+");
        System.out.printf("+ %d %d %d + %d %d %d + %d %d %d +%n", rows.get(6).getCells().stream().map(SudokuCell::getNumber).toArray());
        System.out.printf("+ %d %d %d + %d %d %d + %d %d %d +%n", rows.get(7).getCells().stream().map(SudokuCell::getNumber).toArray());
        System.out.printf("+ %d %d %d + %d %d %d + %d %d %d +%n", rows.get(8).getCells().stream().map(SudokuCell::getNumber).toArray());
        System.out.println("+-------+-------+-------+");
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) o;
        return Objects.equals(blocks, that.blocks) && Objects.equals(rows, that.rows) && Objects.equals(columns, that.columns) && Objects.equals(blockMap, that.blockMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocks, rows, columns, blockMap);
    }
}
