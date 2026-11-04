import java.util.*;

public class SudokuSolver {
    static int[][] board = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    static void printBoard() {
        for (int[] row : board) System.out.println(Arrays.toString(row));
    }

    static int[] findEmpty() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (board[i][j] == 0) return new int[]{i, j};
        return null;
    }

    static boolean isValid(int num, int row, int col) {
        // Check row
        for (int j = 0; j < 9; j++)
            if (board[row][j] == num && j != col) return false;

        // Check column
        for (int i = 0; i < 9; i++)
            if (board[i][col] == num && i != row) return false;

        // Check 3x3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        for (int i = boxRow; i < boxRow + 3; i++)
            for (int j = boxCol; j < boxCol + 3; j++)
                if (board[i][j] == num && !(i == row && j == col)) return false;

        return true;
    }

    static List<Integer> candidates(int row, int col) {
        // Constraint propagation: only legal numbers for this cell
        List<Integer> list = new ArrayList<>();
        for (int num = 1; num <= 9; num++)
            if (isValid(num, row, col)) list.add(num);
        return list;
    }

    static boolean solve() {
        int[] empty = findEmpty();
        if (empty == null) return true;

        int row = empty[0], col = empty[1];

        for (int num : candidates(row, col)) {
            board[row][col] = num;

            if (solve()) return true;

            // Backtrack
            board[row][col] = 0;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Sudoku Puzzle:");
        printBoard();

        if (solve()) {
            System.out.println("\nSolved Sudoku:");
            printBoard();
        } else {
            System.out.println("No solution exists.");
        }
    }
}
