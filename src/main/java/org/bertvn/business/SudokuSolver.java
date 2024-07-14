package org.bertvn.business;

import org.bertvn.data.SudokuBoard;
import org.bertvn.data.SudokuCell;
import org.bertvn.data.SudokuGroup;

import java.util.*;
import java.util.stream.Collectors;

public class SudokuSolver {

    public static final int RUN_COUNT_LIMIT = 10;
    public static final List<Integer> VALID_VALUES = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public SudokuSolver() {

    }

    public boolean solve(SudokuBoard board) {

        List<SudokuCell> cellList = board.getAllCells();
        int runCountLimit = RUN_COUNT_LIMIT;

        while(true) {
            assignPossibleValues(cellList);

            setValueOfCellsWithSinglePossibleValue(cellList);

            if(board.isDone()) {
                break;
            }

            setValueForCellIfOnlyViableLocationOfSaidValue(board);

            if(board.isDone()) {
                break;
            }

            setValueForCellsBasedOnBlocksClaimingNumbers(board);

            runCountLimit--;
            if(runCountLimit == 0 || board.isDone()) {
                break;
            }
        }

        return board.isDone();
    }

    private static void assignPossibleValues(List<SudokuCell> cellList) {
        for(SudokuCell cell : cellList) {
            if(cell.hasValue()) {
                continue;
            }
            List<Integer> alreadyUsedNumbers = cell.getContainers().stream()
                    .flatMap(container -> container.getCells().stream())
                    .distinct()
                    .map(SudokuCell::getNumber)
                    .filter(x -> x != 0)
                    .distinct()
                    .toList();

            List<Integer> numbersPossibleForCell = new ArrayList<>(VALID_VALUES);
            numbersPossibleForCell.removeAll(alreadyUsedNumbers);

            cell.setPossibleNumbers(numbersPossibleForCell);
        }
    }

    private static void setValueOfCellsWithSinglePossibleValue(List<SudokuCell> cellList) {
        boolean changed;
        do {
            changed = false;
            for(SudokuCell cell : cellList) {
                if(cell.hasValue()) {
                    continue;
                }
                List<Integer> possibleNumbers = cell.getPossibleNumbers();
                if(possibleNumbers.size() == 1) {
                    Integer number = possibleNumbers.get(0);
                    cell.setNumber(number);
                    cell.getContainers().stream()
                            .flatMap(x -> x.getCells().stream())
                            .distinct()
                            .forEach(x -> x.removePossibleNumber(number));
                    changed = true;
                }
            }
        } while(changed);
    }

    private static void setValueForCellIfOnlyViableLocationOfSaidValue(SudokuBoard board) {
        boolean changed;
        do {
            changed = false;
            for(SudokuGroup block : board.getBlocks()) {
                if(block.isDone()) {
                    continue;
                }
                Map<Integer, List<SudokuCell>> possibleNumberToCellMap = createValueToApplicableCellMap(block);
                changed |= setValueForCellIfOnlyViableLocationOfSaidValue(possibleNumberToCellMap);
            }
        } while(changed);
    }

    private static boolean setValueForCellIfOnlyViableLocationOfSaidValue(Map<Integer, List<SudokuCell>> possibleNumberToCellMap) {
        boolean changed = false;
        for(Map.Entry<Integer, List<SudokuCell>> entry : possibleNumberToCellMap.entrySet()) {
            if(entry.getValue().size() == 1) {
                entry.getValue().get(0).setNumber(entry.getKey());
                changed = true;
            }
        }
        return changed;
    }

    private static void setValueForCellsBasedOnBlocksClaimingNumbers(SudokuBoard board) {
        boolean changed;
        do {
            List<SudokuCell> allCells = board.getAllCells();
            int filledCount = (int) allCells.stream().filter(SudokuCell::hasValue).count();
            for(SudokuGroup block : board.getBlocks()) {
                changed = false;
                if(block.isDone()) {
                    continue;
                }
                Map<Integer, List<SudokuCell>> possibleNumberToCellMap = createValueToApplicableCellMap(block);
                for(Map.Entry<Integer, List<SudokuCell>> entry : possibleNumberToCellMap.entrySet()) {
                    List<SudokuCell> applicableCells = entry.getValue();
                    int count = applicableCells.size();
                    if(count == 2 || count == 3) {
                        SudokuCell baseCell = applicableCells.get(0);
                        List<SudokuGroup> containers = new ArrayList<>(baseCell.getContainers());
                        for(int i = 1; i < applicableCells.size(); i++) {
                            containers.retainAll(applicableCells.get(i).getContainers());
                        }
                        if(containers.size() == 2) {
                            containers.remove(block);
                            SudokuGroup sudokuGroup = containers.get(0);
                            List<SudokuCell> cellsInGroup = new ArrayList<>(sudokuGroup.getCells());
                            cellsInGroup.removeAll(applicableCells);
                            cellsInGroup.forEach(x -> x.removePossibleNumber(entry.getKey()));
                            changed = true;
                        }
                    }
                }
                if(changed) {
                    setValueOfCellsWithSinglePossibleValue(allCells);
                    setValueForCellIfOnlyViableLocationOfSaidValue(possibleNumberToCellMap);
                }
            }

            if(board.isDone()) {
                break;
            }
            int afterFilledCount = (int) allCells.stream().filter(SudokuCell::hasValue).count();

            changed = afterFilledCount != filledCount;
        } while(changed);
    }

    private static Map<Integer, List<SudokuCell>> createValueToApplicableCellMap(SudokuGroup block) {
        Map<Integer, List<SudokuCell>> possibleNumberToCellMap = new HashMap<>();
        List<SudokuCell> cells = block.getCells();
        Set<Integer> usedNumbers = cells.stream()
                .filter(SudokuCell::hasValue)
                .map(SudokuCell::getNumber)
                .collect(Collectors.toSet());
        for(SudokuCell cell : cells) {
            if(cell.hasValue()) {
                continue;
            }
            List<Integer> possibleNumbers = cell.getPossibleNumbers();
            possibleNumbers.removeAll(usedNumbers);
            for(Integer possibleNumber : possibleNumbers) {
                possibleNumberToCellMap.computeIfAbsent(possibleNumber, key -> new ArrayList<>()).add(cell);
            }
        }
        return possibleNumberToCellMap;
    }
}
