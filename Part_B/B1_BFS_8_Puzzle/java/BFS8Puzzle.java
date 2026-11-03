import java.util.*;

public class BFS8Puzzle {
    static final String GOAL = "123456780";
    static final String START = "136502478";
    static final int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static List<String> neighbours(String state) {
        List<String> result = new ArrayList<>();
        int blank = state.indexOf('0');
        int r = blank / 3, c = blank % 3;

        for (int[] d : DIRS) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < 3 && nc >= 0 && nc < 3) {
                char[] a = state.toCharArray();
                int i = nr * 3 + nc;
                char tmp = a[blank];
                a[blank] = a[i];
                a[i] = tmp;
                result.add(new String(a));
            }
        }
        return result;
    }

    static void show(String state) {
        for (int i = 0; i < 9; i += 3)
            System.out.println(state.charAt(i) + " "
                    + state.charAt(i + 1) + " " + state.charAt(i + 2));
    }

    public static void main(String[] args) {
        ArrayDeque<String> queue = new ArrayDeque<>();
        Map<String, String> parent = new HashMap<>();
        queue.add(START);
        parent.put(START, null);

        String found = null;
        int explored = 0;

        while (!queue.isEmpty()) {
            String state = queue.poll();
            explored++;
            if (state.equals(GOAL)) {
                found = state;
                break;
            }
            for (String next : neighbours(state)) {
                if (!parent.containsKey(next)) {
                    parent.put(next, state);
                    queue.add(next);
                }
            }
        }

        if (found == null) {
            System.out.println("No solution");
            return;
        }

        LinkedList<String> path = new LinkedList<>();
        for (String s = found; s != null; s = parent.get(s))
            path.addFirst(s);

        System.out.println("Moves: " + (path.size() - 1)
                + " | States explored: " + explored);

        int step = 0;
        for (String s : path) {
            System.out.println("\nStep " + step++);
            show(s);
        }
    }
}
