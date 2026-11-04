import java.util.*;

public class DFSWaterJug {

    // One node = current state + the actions taken to reach it
    static class Node {
        int x, y;
        List<String> path;

        Node(int x, int y, List<String> path) {
            this.x = x;
            this.y = y;
            this.path = path;
        }
    }

    static void dfs(int jug1, int jug2, int target) {
        Stack<Node> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        stack.push(new Node(0, 0, new ArrayList<>()));
        visited.add("0,0");

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            int x = current.x;
            int y = current.y;

            // Check if target is reached
            if (x == target || y == target) {
                for (String line : current.path) {
                    System.out.println(line);
                }
                System.out.println("\nTarget achieved in " + current.path.size() + " steps!");
                return;
            }

            // Possible moves: new state + action name
            int[][] states = {
                {jug1, y},                              // Fill Jug1
                {x, jug2},                              // Fill Jug2
                {0, y},                                 // Empty Jug1
                {x, 0},                                 // Empty Jug2
                {Math.max(0, x - (jug2 - y)), Math.min(jug2, y + x)},  // Pour Jug1 -> Jug2
                {Math.min(jug1, x + y), Math.max(0, y - (jug1 - x))}   // Pour Jug2 -> Jug1
            };

            String[] actions = {
                "Fill Jug1",
                "Fill Jug2",
                "Empty Jug1",
                "Empty Jug2",
                "Pour Jug1 -> Jug2",
                "Pour Jug2 -> Jug1"
            };

            for (int i = 0; i < states.length; i++) {
                int nx = states[i][0];
                int ny = states[i][1];
                String key = nx + "," + ny;

                if (!visited.contains(key)) {
                    visited.add(key);

                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(actions[i] + " -> (" + nx + ", " + ny + ")");

                    stack.push(new Node(nx, ny, newPath));
                }
            }
        }

        System.out.println("No solution found");
    }

    public static void main(String[] args) {
        int jug1Capacity = 4;
        int jug2Capacity = 3;
        int target = 2;

        dfs(jug1Capacity, jug2Capacity, target);
    }
}
