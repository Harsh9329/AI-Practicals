public class EightQueens {

    static final int N = 8;
    static int[][] board = new int[N][N];

    static void printBoard() {
        for (int[] row : board) {
            for (int col = 0; col < N; col++) {
                System.out.print((col == 0 ? "" : " ") + (row[col] == 1 ? "Q" : "."));
            }
            System.out.println();
        }
        System.out.println();
    }

    static boolean isSafe(int row, int col) {
        // Check left side of current row
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }

        // Check upper-left diagonal
        int i = row, j = col;
        while (i >= 0 && j >= 0) {
            if (board[i][j] == 1) {
                return false;
            }
            i--;
            j--;
        }

        // Check lower-left diagonal
        i = row;
        j = col;
        while (i < N && j >= 0) {
            if (board[i][j] == 1) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    static boolean solveQueens(int col) {
        // All queens placed
        if (col >= N) {
            return true;
        }

        for (int row = 0; row < N; row++) {
            if (isSafe(row, col)) {
                // Place queen
                board[row][col] = 1;

                // Recur for next column
                if (solveQueens(col + 1)) {
                    return true;
                }

                // Backtrack
                board[row][col] = 0;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        if (solveQueens(0)) {
            System.out.println("Solution Found:\n");
            printBoard();
        } else {
            System.out.println("No Solution Exists");
        }
    }
}
