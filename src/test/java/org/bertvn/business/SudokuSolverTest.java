package org.bertvn.business;

import org.bertvn.data.SudokuBoard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuSolverTest {

    @Test
    public void testSimpleSolve() {
        //given
        SudokuBoard toSolve = createSimpleSudoku();
        SudokuBoard expected = createSimpleSudokuSolution();

        System.out.println("to solve");
        toSolve.print();

        //when
        boolean solved = new SudokuSolver().solve(toSolve);

        System.out.println("solved (?)");
        toSolve.print();

        System.out.println("expected");
        expected.print();

        //then
        assertTrue(solved);
        assertEquals(expected, toSolve);
    }

    private static SudokuBoard createSimpleSudoku() {
        List<Integer> row1 = List.of(0, 0, 2, 0, 0, 0, 0, 7, 0);
        List<Integer> row2 = List.of(1, 8, 0, 7, 0, 3, 0, 2, 0);
        List<Integer> row3 = List.of(3, 0, 4, 0, 0, 0, 0, 0, 1);
        List<Integer> row4 = List.of(5, 3, 1, 0, 0, 0, 9, 0, 4);
        List<Integer> row5 = List.of(8, 0, 0, 0, 4, 9, 6, 0, 0);
        List<Integer> row6 = List.of(0, 4, 0, 8, 0, 0, 0, 5, 7);
        List<Integer> row7 = List.of(0, 9, 6, 0, 5, 7, 8, 3, 0);
        List<Integer> row8 = List.of(0, 1, 0, 3, 9, 6, 0, 4, 0);
        List<Integer> row9 = List.of(0, 5, 0, 2, 8, 0, 0, 9, 0);

        return new SudokuBoard(List.of(row1, row2, row3, row4, row5, row6, row7, row8, row9));
    }

    private static SudokuBoard createSimpleSudokuSolution() {
        List<Integer> row1 = List.of(9, 6, 2, 4, 1, 5, 3, 7, 8);
        List<Integer> row2 = List.of(1, 8, 5, 7, 6, 3, 4, 2, 9);
        List<Integer> row3 = List.of(3, 7, 4, 9, 2, 8, 5, 6, 1);
        List<Integer> row4 = List.of(5, 3, 1, 6, 7, 2, 9, 8, 4);
        List<Integer> row5 = List.of(8, 2, 7, 5, 4, 9, 6, 1, 3);
        List<Integer> row6 = List.of(6, 4, 9, 8, 3, 1, 2, 5, 7);
        List<Integer> row7 = List.of(4, 9, 6, 1, 5, 7, 8, 3, 2);
        List<Integer> row8 = List.of(2, 1, 8, 3, 9, 6, 7, 4, 5);
        List<Integer> row9 = List.of(7, 5, 3, 2, 8, 4, 1, 9, 6);
        return new SudokuBoard(List.of(row1, row2, row3, row4, row5, row6, row7, row8, row9));
    }

    @Test
    public void testIntermediateSolve() {
        //given
        SudokuBoard toSolve = createIntermediateSudoku();
        SudokuBoard expected = createIntermediateSudokuSolution();

        System.out.println("to solve");
        toSolve.print();

        //when
        boolean solved = new SudokuSolver().solve(toSolve);

        System.out.println("solved (?)");
        toSolve.print();

        System.out.println("expected");
        expected.print();

        //then
        assertTrue(expected.isValid());
        assertTrue(expected.isDone());
        assertTrue(solved);
        assertEquals(expected, toSolve);
    }

    private static SudokuBoard createIntermediateSudoku() {
        List<Integer> row1 = List.of(2, 0, 0, 0, 0, 0, 8, 6, 0);
        List<Integer> row2 = List.of(0, 0, 0, 0, 4, 2, 0, 0, 0);
        List<Integer> row3 = List.of(0, 1, 0, 0, 6, 0, 0, 4, 7);
        List<Integer> row4 = List.of(3, 4, 5, 0, 2, 0, 0, 0, 1);
        List<Integer> row5 = List.of(7, 2, 0, 0, 0, 0, 4, 0, 9);
        List<Integer> row6 = List.of(8, 0, 0, 0, 0, 0, 5, 0, 6);
        List<Integer> row7 = List.of(0, 0, 2, 0, 3, 0, 0, 0, 0);
        List<Integer> row8 = List.of(0, 0, 0, 6, 8, 0, 0, 1, 2);
        List<Integer> row9 = List.of(5, 0, 8, 0, 0, 0, 0, 0, 4);

        return new SudokuBoard(List.of(row1, row2, row3, row4, row5, row6, row7, row8, row9));
    }

    private static SudokuBoard createIntermediateSudokuSolution() {
        List<Integer> row1 = List.of(2, 5, 4, 1, 9, 7, 8, 6, 3);
        List<Integer> row2 = List.of(6, 8, 7, 3, 4, 2, 1, 9, 5);
        List<Integer> row3 = List.of(9, 1, 3, 5, 6, 8, 2, 4, 7);
        List<Integer> row4 = List.of(3, 4, 5, 9, 2, 6, 7, 8, 1);
        List<Integer> row5 = List.of(7, 2, 6, 8, 5, 1, 4, 3, 9);
        List<Integer> row6 = List.of(8, 9, 1, 4, 7, 3, 5, 2, 6);
        List<Integer> row7 = List.of(1, 6, 2, 7, 3, 4, 9, 5, 8);
        List<Integer> row8 = List.of(4, 7, 9, 6, 8, 5, 3, 1, 2);
        List<Integer> row9 = List.of(5, 3, 8, 2, 1, 9, 6, 7, 4);
        return new SudokuBoard(List.of(row1, row2, row3, row4, row5, row6, row7, row8, row9));
    }

    @Test
    public void testHardSolve() {
        //given
        SudokuBoard toSolve = createHardSudoku();
        SudokuBoard expected = createHardSudokuSolution();

        System.out.println("to solve");
        toSolve.print();

        //when
        boolean solved = new SudokuSolver().solve(toSolve);

        System.out.println("solved (?)");
        toSolve.print();

        System.out.println("expected");
        expected.print();

        //then
        assertTrue(expected.isValid());
        assertTrue(expected.isDone());
        assertTrue(solved);
        assertEquals(expected, toSolve);
    }

    private static SudokuBoard createHardSudoku() {
        List<Integer> row1 = List.of(0, 0, 1, 0, 0, 0, 0, 6, 0);
        List<Integer> row2 = List.of(0, 0, 0, 8, 0, 0, 0, 0, 0);
        List<Integer> row3 = List.of(0, 7, 3, 0, 0, 0, 0, 1, 9);
        List<Integer> row4 = List.of(0, 0, 5, 0, 0, 2, 0, 0, 7);
        List<Integer> row5 = List.of(0, 0, 0, 7, 6, 0, 5, 8, 0);
        List<Integer> row6 = List.of(7, 0, 4, 5, 9, 0, 0, 0, 6);
        List<Integer> row7 = List.of(0, 6, 0, 0, 2, 0, 0, 0, 0);
        List<Integer> row8 = List.of(0, 0, 0, 0, 0, 0, 0, 7, 8);
        List<Integer> row9 = List.of(1, 9, 0, 0, 0, 3, 2, 4, 0);

        return new SudokuBoard(List.of(row1, row2, row3, row4, row5, row6, row7, row8, row9));
    }

    private static SudokuBoard createHardSudokuSolution() {
        List<Integer> row1 = List.of(8, 5, 1, 3, 4, 9, 7, 6, 2);
        List<Integer> row2 = List.of(9, 2, 6, 8, 7, 1, 3, 5, 4);
        List<Integer> row3 = List.of(4, 7, 3, 2, 5, 6, 8, 1, 9);
        List<Integer> row4 = List.of(6, 8, 5, 1, 3, 2, 4, 9, 7);
        List<Integer> row5 = List.of(2, 1, 9, 7, 6, 4, 5, 8, 3);
        List<Integer> row6 = List.of(7, 3, 4, 5, 9, 8, 1, 2, 6);
        List<Integer> row7 = List.of(5, 6, 8, 4, 2, 7, 9, 3, 1);
        List<Integer> row8 = List.of(3, 4, 2, 9, 1, 5, 6, 7, 8);
        List<Integer> row9 = List.of(1, 9, 7, 6, 8, 3, 2, 4, 5);
        return new SudokuBoard(List.of(row1, row2, row3, row4, row5, row6, row7, row8, row9));
    }

}