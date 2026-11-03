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
    static boolean[] candidates(int r, int c) {
        boolean[] used = new boolean[10];
        for (int i = 0; i < 9; i++) {
            used[board[r][i]] = true;
            used[board[i][c]] = true;
        }
        int br = (r / 3) * 3, bc = (c / 3) * 3;
        for (int i = br; i < br + 3; i++)
            for (int j = bc; j < bc + 3; j++)
                used[board[i][j]] = true;
        return used;
    }

    static boolean solve() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    boolean[] used = candidates(r, c);
                    for (int v = 1; v <= 9; v++) {
                        if (!used[v]) {
                            board[r][c] = v;
                            if (solve()) return true;
                            board[r][c] = 0;    // backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    static void show() {
        for (int r = 0; r < 9; r++) {
            if (r == 3 || r == 6) System.out.println("---------------------");
            for (int c = 0; c < 9; c++)
                System.out.print(board[r][c] == 0 ? ". " : board[r][c] + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        show();
        System.out.println("Solved: " + solve());
        show();
    }
}
