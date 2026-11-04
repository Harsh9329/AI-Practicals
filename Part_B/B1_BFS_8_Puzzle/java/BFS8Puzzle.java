import java.util.*;

public class BFS8Puzzle {

    static final String GOAL = "123456780";
    static final String START = "136502478";
    static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    // One node = current state + the path taken to reach it
    static class Node {
        String state;
        List<String> path;

        Node(String state, List<String> path) {
            this.state = state;
            this.path = path;
        }
    }

    static List<String> getNeighbors(String state) {
        List<String> neighbors = new ArrayList<>();
        int blank = state.indexOf('0');
        int x = blank / 3;
        int y = blank % 3;

        for (int[] d : DIRECTIONS) {
            int nx = x + d[0];
            int ny = y + d[1];

            if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                char[] next = state.toCharArray();
                int i = nx * 3 + ny;

                char temp = next[blank];
                next[blank] = next[i];
                next[i] = temp;

                neighbors.add(new String(next));
            }
        }

        return neighbors;
    }

    static List<String> bfs(String start) {
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(new Node(start, new ArrayList<>()));
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.state.equals(GOAL)) {
                List<String> solution = new ArrayList<>(current.path);
                solution.add(current.state);
                return solution;
            }

            for (String neighbor : getNeighbors(current.state)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);

                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(current.state);

                    queue.add(new Node(neighbor, newPath));
                }
            }
        }

        return null;
    }

    static void printBoard(String state) {
        for (int i = 0; i < 9; i += 3) {
            System.out.println(state.charAt(i) + " "
                    + state.charAt(i + 1) + " " + state.charAt(i + 2));
        }
    }

    public static void main(String[] args) {
        List<String> solution = bfs(START);

        if (solution != null) {
            System.out.println("\nSolution found in " + (solution.size() - 1) + " moves:\n");

            for (int step = 0; step < solution.size(); step++) {
                System.out.println("Step " + step);
                printBoard(solution.get(step));
                System.out.println();
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}
