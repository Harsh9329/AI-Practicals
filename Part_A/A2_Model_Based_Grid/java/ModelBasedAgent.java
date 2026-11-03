import java.util.*;

public class ModelBasedAgent {
    static final int N = 4;
    static final String[] GRID = {"S...", ".X..", ".X..", "...G"};
    static final int[] START = {0, 0};
    static final int[] GOAL = {3, 3};
    static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    // -1 unknown, 0 free, 1 blocked
    static int[][] model = new int[N][N];

    static void sense(int r, int c) {
        for (int[] d : DIRS) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < N && nc >= 0 && nc < N)
                model[nr][nc] = GRID[nr].charAt(nc) == 'X' ? 1 : 0;
        }
        model[r][c] = GRID[r].charAt(c) == 'X' ? 1 : 0;
    }

    static List<int[]> plan(int sr, int sc) {
        int[][] parent = new int[N][N];
        for (int[] row : parent) Arrays.fill(row, -1);

        boolean[][] seen = new boolean[N][N];
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{sr, sc});
        seen[sr][sc] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1];

            if (r == GOAL[0] && c == GOAL[1]) {
                LinkedList<int[]> path = new LinkedList<>();
                while (!(r == sr && c == sc)) {
                    path.addFirst(new int[]{r, c});
                    int p = parent[r][c];
                    r = p / N;
                    c = p % N;
                }
                return path;
            }

            for (int[] d : DIRS) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < N && nc >= 0 && nc < N
                        && !seen[nr][nc] && model[nr][nc] != 1) {
                    seen[nr][nc] = true;
                    parent[nr][nc] = r * N + c;
                    queue.add(new int[]{nr, nc});
                }
            }
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        for (int[] row : model) Arrays.fill(row, -1);

        int r = START[0], c = START[1];
        sense(r, c);
        int steps = 0;

        while (!(r == GOAL[0] && c == GOAL[1])) {
            List<int[]> path = plan(r, c);
            if (path.isEmpty()) {
                System.out.println("No path to the goal.");
                break;
            }
            int[] next = path.get(0);
            r = next[0];
            c = next[1];
            sense(r, c);
            steps++;
            System.out.println("Step " + steps + ": move to (" + r + ", " + c + ")");
        }

        System.out.println("Goal reached: " + (r == GOAL[0] && c == GOAL[1]));
        System.out.println("Model (-1 unknown, 0 free, 1 blocked):");
        for (int[] row : model) System.out.println(Arrays.toString(row));
    }
}
