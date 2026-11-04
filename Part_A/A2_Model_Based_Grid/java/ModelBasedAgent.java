import java.util.*;

public class ModelBasedAgent {

    static class Agent {
        char[][] grid;
        int[] position;
        int[] goal;
        char[][] model;                 // '?' unknown, 'O' free, 'X' obstacle
        Set<String> visited = new HashSet<>();

        Agent(char[][] grid, int[] start, int[] goal) {
            this.grid = grid;
            this.position = start.clone();
            this.goal = goal;
            this.model = new char[grid.length][grid[0].length];
            for (char[] row : model) Arrays.fill(row, '?');
            visited.add(start[0] + "," + start[1]);
            sense();
        }

        void sense() {
            int x = position[0], y = position[1];
            int[][] dirs = {{0, 0}, {-1, 0}, {1, 0}, {0, -1}, {0, 1}};

            for (int[] d : dirs) {
                int nx = x + d[0], ny = y + d[1];
                if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length)
                    model[nx][ny] = grid[nx][ny];
            }
        }

        void displayGrid() {
            for (char[] row : model) System.out.println(Arrays.toString(row));
            System.out.println();
        }

        boolean move() {
            int x = position[0], y = position[1];
            int gx = goal[0], gy = goal[1];

            List<int[]> moves = new ArrayList<>();
            if (x < gx) moves.add(new int[]{x + 1, y});
            if (y < gy) moves.add(new int[]{x, y + 1});
            if (x > gx) moves.add(new int[]{x - 1, y});
            if (y > gy) moves.add(new int[]{x, y - 1});

            moves.add(new int[]{x + 1, y});
            moves.add(new int[]{x, y + 1});
            moves.add(new int[]{x - 1, y});
            moves.add(new int[]{x, y - 1});

            for (int[] m : moves) {
                int nx = m[0], ny = m[1];
                if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length) {
                    String key = nx + "," + ny;
                    if (model[nx][ny] != 'X' && !visited.contains(key)) {
                        position = new int[]{nx, ny};
                        visited.add(key);
                        sense();
                        return true;
                    }
                }
            }
            return false;
        }

        void run() {
            int step = 1;
            while (!(position[0] == goal[0] && position[1] == goal[1]) && step <= 50) {
                System.out.println("Step " + step + ": Current Position = ("
                        + position[0] + ", " + position[1] + ")");
                if (!move()) {
                    System.out.println("No path to the goal.");
                    return;
                }
                step++;
            }
            System.out.println("\nGoal Reached at (" + position[0]
                    + ", " + position[1] + ")");
        }
    }

    public static void main(String[] args) {
        char[][] grid = {
            {'O', 'O', 'O', 'O'},
            {'O', 'X', 'O', 'O'},
            {'O', 'X', 'O', 'O'},
            {'O', 'O', 'O', 'O'}
        };

        Agent agent = new Agent(grid, new int[]{0, 0}, new int[]{3, 3});
        System.out.println("Agent's initial model ('?' = not sensed yet):");
        agent.displayGrid();
        agent.run();
    }
}
