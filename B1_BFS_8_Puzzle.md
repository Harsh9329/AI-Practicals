# Assignment B1 — BFS for Solving a Planning Problem (8-Puzzle)

> **Selected practical:** This is one of the bold / highlighted practicals in **Part B** of the manual. Mini-project is not included.

## 1. Aim

To implement Breadth-First Search (BFS) to solve a planning or problem-solving task such as the 8-puzzle, robot path planning, or Blocks World problem.

## 2. Prerequisite

State-space representation, queue data structure, basics of graph traversal.

## 3. Objective

- To understand uninformed (blind) search strategies in Artificial Intelligence.
- To study the Breadth First Search (BFS) algorithm and its properties.
- To implement BFS to solve a chosen planning problem (8-puzzle, robot path planning, or Blocks World).
- To analyse the completeness, optimality, time and space complexity of BFS.

## 4. Outcome

Students will be able to formulate a planning problem as a state-space graph and implement BFS to find the shortest (least number of moves) solution path from the initial state to the goal state.

## 5. Software and Hardware Requirements

Python 3.x with `collections.deque` / Java with `LinkedList` or `ArrayDeque` as Queue / C++ with STL queue, Windows/Linux OS, 4 GB RAM minimum.

## 6. Theory

### Uninformed Search

Uninformed (blind) search strategies have no additional information about states beyond that provided in the problem definition — they can only generate successors and test whether a state is the goal. Breadth First Search (BFS) is one of the simplest uninformed strategies.

### Breadth First Search (BFS)

BFS expands the shallowest (least-deep) unexpanded node first. It is implemented using a **FIFO queue**: newly generated successors go to the back of the queue, so shallower nodes are always expanded before deeper ones. BFS explores the search tree level by level.

### Properties of BFS

- **Completeness:** BFS is complete — if a solution exists, it will be found, provided the branching factor is finite.
- **Optimality:** BFS is optimal (finds the shallowest / least-cost solution) provided all step costs are equal (e.g., 1 per move).
- **Time Complexity:** `O(b^d)`, where `b` is the branching factor and `d` is the depth of the shallowest goal.
- **Space Complexity:** `O(b^d)` — BFS keeps every generated node in memory, which is its major drawback for large search spaces.

### Example Application: Robot Path Planning

In grid-based robot path planning, each grid cell is a state, and actions move the robot to an adjacent free cell (Up/Down/Left/Right). BFS explores the grid outward from the start cell layer by layer and is guaranteed to find the shortest path (fewest cell-to-cell moves) to the goal, since every move has equal cost.

### Example Application: 8-Puzzle

Each board configuration is a state; actions slide the blank Up/Down/Left/Right. BFS explores all configurations reachable in 1 move, then 2 moves, and so on, guaranteeing the shortest solution. However, BFS's memory requirement grows extremely fast for the 8-puzzle since the branching factor is up to 4 and solutions can be many moves deep, so BFS is practical only for puzzles with short solutions.

## 7. Algorithm Steps

1. Create a FIFO queue and enqueue the start state; mark it visited.
2. While the queue is not empty:
   1. Dequeue the front state `n`.
   2. If `n` is the goal, reconstruct the path (using parent pointers) and return it.
   3. Otherwise, generate all valid successors of `n`; enqueue each unvisited successor and mark it visited.
3. If the queue empties without finding the goal, report failure (no solution exists).

## 8. Diagram

The manual shows the BFS search tree. In simple words:

```text
Level 0:                 Start
                       /   |   \
Level 1:           S1     S2     S3
                  / | \   / | \   ...
Level 2:        ...
```

BFS visits **all** nodes at level 0, then all at level 1, then all at level 2. Therefore the first goal it reaches is at the smallest depth, which is the shortest solution for unit-cost moves.

For the 8-puzzle, a state is a 3 × 3 board:

```text
1 3 6
5 0 2
4 7 8
```

`0` is the blank. Moving a tile into the blank produces a neighbouring state.

## 9. Conclusion

BFS was implemented and applied to solve a chosen planning problem. The algorithm correctly found the shortest solution path (in terms of number of moves), and its completeness and optimality (for unit-cost problems) were verified experimentally on multiple test instances.

## 10. Viva Voce Questions

1. Why is BFS said to be optimal only when all step costs are equal?
2. What data structure is central to BFS, and why?
3. Give the time and space complexity of BFS in terms of `b` and `d`.
4. Compare BFS with Depth First Search in terms of memory usage.
5. Under what circumstances would BFS run out of memory in practice?
6. How would you modify BFS to handle unequal (weighted) step costs?
7. What is the difference between graph-search and tree-search versions of BFS?
8. Give the PEAS description for robot path planning if used as your chosen application.

## 11. How to Run

### Python

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_B\B1_BFS_8_Puzzle\python"
python bfs_8puzzle.py
```

### Java

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_B\B1_BFS_8_Puzzle\java"
javac BFS8Puzzle.java
java BFS8Puzzle
```

## 12. Python Code

```python
# B1 - BFS for the 8-Puzzle

from collections import deque

GOAL = (1, 2, 3, 4, 5, 6, 7, 8, 0)
START = (1, 3, 6, 5, 0, 2, 4, 7, 8)      # 0 is the blank


def neighbours(state):
    """All states reachable by moving one tile into the blank."""
    blank = state.index(0)
    r, c = divmod(blank, 3)
    for dr, dc in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
        nr, nc = r + dr, c + dc
        if 0 <= nr < 3 and 0 <= nc < 3:
            i = nr * 3 + nc
            s = list(state)
            s[blank], s[i] = s[i], s[blank]
            yield tuple(s)


def bfs(start):
    queue = deque([start])
    parent = {start: None}       # state -> state it came from
    explored = 0

    while queue:
        state = queue.popleft()  # FIFO = breadth first
        explored += 1
        if state == GOAL:
            path = []
            while state is not None:
                path.append(state)
                state = parent[state]
            return path[::-1], explored

        for nxt in neighbours(state):
            if nxt not in parent:
                parent[nxt] = state
                queue.append(nxt)
    return None, explored


def show(state):
    for i in range(0, 9, 3):
        print(state[i], state[i + 1], state[i + 2])


path, explored = bfs(START)
print("Moves:", len(path) - 1, "| States explored:", explored)
for step, state in enumerate(path):
    print("\nStep", step)
    show(state)
```

## 13. Java Code

```java
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
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `GOAL = (1, 2, 3, 4, 5, 6, 7, 8, 0)` | Goal board; `0` is the blank. |
| `START = (1, 3, 6, 5, 0, 2, 4, 7, 8)` | Start board used in the demo. |
| `def neighbours(state):` | Generates every state reachable by one legal slide. |
| `blank = state.index(0)` | Finds the blank tile. |
| `r, c = divmod(blank, 3)` | Converts the 1-D index into a row and column. |
| `for dr, dc in [...]:` | Tries up, down, left, right. |
| `if 0 <= nr < 3 and 0 <= nc < 3:` | Stays inside the board. |
| `s[blank], s[i] = s[i], s[blank]` | Swaps the blank with the neighbouring tile = one move. |
| `yield tuple(s)` | Returns the new state as an immutable tuple (usable in a set/dict). |
| `queue = deque([start])` | BFS uses a **FIFO queue**. |
| `parent = {start: None}` | Remembers where each state came from, for path rebuilding. |
| `explored = 0` / `explored += 1` | Counts how many states BFS removes from the queue. |
| `state = queue.popleft()` | Takes the shallowest state first — this makes it BFS. |
| `if state == GOAL:` | Goal test. |
| `while state is not None:` | Walks the parent chain back to the start. |
| `return path[::-1], explored` | Reverses the path so it runs start → goal. |
| `if nxt not in parent:` | Visited check: each state is enqueued only once. |
| `parent[nxt] = state` / `queue.append(nxt)` | Records the parent and adds the successor. |
| `def show(state):` | Prints the board as three rows. |

### Java code

| Code block | Explanation |
|---|---|
| `static final String GOAL = "123456780";` | Goal state as a 9-character string. |
| `static final String START = "136502478";` | Start state. |
| `static List<String> neighbours(String state)` | Generates all one-move successors. |
| `int blank = state.indexOf('0');` | Finds the blank. |
| `char[] a = state.toCharArray();` | Strings are immutable, so copy before swapping. |
| `ArrayDeque<String> queue` | FIFO queue for BFS (`add` at back, `poll` from front). |
| `Map<String, String> parent` | Parent of every state for path reconstruction. |
| `while (!queue.isEmpty())` | BFS loop. |
| `String state = queue.poll();` | Removes the shallowest state. |
| `if (state.equals(GOAL))` | Goal test. |
| `if (!parent.containsKey(next))` | Visited check. |
| `parent.put(next, state); queue.add(next);` | Records the parent and enqueues. |
| `path.addFirst(s)` | Builds the path from goal back to start. |
| `for (String s : path)` | Prints every state step by step. |

## 15. Sample Output

```text
Moves: 8 | States explored: 311

Step 0
1 3 6
5 0 2
4 7 8

Step 1
1 3 6
5 2 0
4 7 8

Step 2
1 3 0
5 2 6
4 7 8

Step 3
1 0 3
5 2 6
4 7 8

Step 4
1 2 3
5 0 6
4 7 8

Step 5
1 2 3
0 5 6
4 7 8

Step 6
1 2 3
4 5 6
0 7 8

Step 7
1 2 3
4 5 6
7 0 8

Step 8
1 2 3
4 5 6
7 8 0
```
