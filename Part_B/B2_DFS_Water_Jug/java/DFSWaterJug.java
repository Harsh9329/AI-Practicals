import java.util.*;

public class DFSWaterJug {
    static final int CAP_A = 4, CAP_B = 3, TARGET = 2;

    static class Node {
        int a, b;
        List<String> path;

        Node(int a, int b, List<String> path) {
            this.a = a;
            this.b = b;
            this.path = path;
        }
    }

    static void push(ArrayDeque<Node> stack, Set<String> visited,
                     int a, int b, String action, List<String> path) {
        String key = a + "," + b;
        if (visited.contains(key)) return;
        visited.add(key);
        List<String> newPath = new ArrayList<>(path);
        newPath.add(action + " -> (" + a + ", " + b + ")");
        stack.push(new Node(a, b, newPath));
    }

    public static void main(String[] args) {
        ArrayDeque<Node> stack = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        stack.push(new Node(0, 0, new ArrayList<>()));
        visited.add("0,0");

        while (!stack.isEmpty()) {
            Node cur = stack.pop();     // LIFO = depth first

            if (cur.a == TARGET || cur.b == TARGET) {
                for (String step : cur.path) System.out.println(step);
                System.out.println("Goal reached in " + cur.path.size() + " steps");
                return;
            }

            int p = Math.min(cur.a, CAP_B - cur.b);   // A -> B
            int q = Math.min(cur.b, CAP_A - cur.a);   // B -> A

            push(stack, visited, CAP_A, cur.b, "Fill A", cur.path);
            push(stack, visited, cur.a, CAP_B, "Fill B", cur.path);
            push(stack, visited, 0, cur.b, "Empty A", cur.path);
            push(stack, visited, cur.a, 0, "Empty B", cur.path);
            push(stack, visited, cur.a - p, cur.b + p, "Pour A to B", cur.path);
            push(stack, visited, cur.a + q, cur.b - q, "Pour B to A", cur.path);
        }

        System.out.println("No solution found");
    }
}
