package org.bertvn.business;

import org.bertvn.data.SudokuBoard;
import org.bertvn.data.SudokuCell;
import org.bertvn.data.SudokuGroup;

import java.util.*;
import java.util.stream.Collectors;

public class SudokuSolver {

    public SudokuSolver() {

    }

    public boolean solve(SudokuBoard board) {

        List<SudokuCell> cellList = board.getAllCells();
        while(true) {
            boolean changed = false;

            for(SudokuCell cell : cellList) {
                if(cell.hasValue()) {
                    continue;
                }
                List<Integer> presentNumbers = cell.getContainers().stream()
                        .flatMap(container -> container.getCells().stream())
                        .distinct()
                        .map(SudokuCell::getNumber)
                        .filter(x -> x != 0)
                        .distinct()
                        .toList();

                List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
                numbers.removeAll(presentNumbers);
                if(numbers.size() == 1) {
                    cell.setNumber(numbers.get(0));
                    changed = true;
                }
                else {
                    cell.setPossibleNumbers(numbers);
                }
            }

            if(changed) {
                continue;
            }

            for(SudokuGroup block : board.getBlocks()) {
                if(block.isDone()) {
                    continue;
                }
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
                for(Map.Entry<Integer, List<SudokuCell>> entry : possibleNumberToCellMap.entrySet()) {
                    if(entry.getValue().size() == 1) {
                        entry.getValue().get(0).setNumber(entry.getKey());
                        changed = true;
                    }
                }
            }

            if(!changed) {
                break;
            }
        }

        return board.isDone();
    }
}
