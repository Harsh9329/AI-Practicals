import java.util.Arrays;

public class EightQueens {
    static final int N = 8;
    static int[] pos = new int[N];   // pos[col] = row
    static int count = 0;

    static boolean safe(int row, int col) {
        for (int c = 0; c < col; c++)
            if (pos[c] == row || Math.abs(pos[c] - row) == col - c)
                return false;
        return true;
    }

    static void solve(int col) {
        if (col == N) {              // all queens placed
            count++;
            if (count == 1) show();
            return;
        }

        for (int row = 0; row < N; row++) {
            if (safe(row, col)) {
                pos[col] = row;
                solve(col + 1);
                pos[col] = -1;       // backtrack
            }
        }
    }

    static void show() {
        System.out.println("First solution:");
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++)
                System.out.print(pos[c] == r ? "Q " : ". ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Arrays.fill(pos, -1);
        solve(0);
        System.out.println("Total solutions: " + count);
    }
}
