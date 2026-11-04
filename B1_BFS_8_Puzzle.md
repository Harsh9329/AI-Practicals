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
   2. If `n` is the goal, return the path stored with that node (each node carries the path used to reach it).
   3. Otherwise, generate all valid successors of `n`; enqueue each unvisited successor with its extended path and mark it visited.
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

BFS was implemented and applied to the 8-puzzle planning problem. Because BFS expands states level by level using a FIFO queue, it found the shortest solution path of 8 moves for the given start state, and the visited set prevented repeated states from being expanded again.

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
cd Part_B/B1_BFS_8_Puzzle/python
python bfs_8puzzle.py
```

### Java

```bash
cd Part_B/B1_BFS_8_Puzzle/java
javac BFS8Puzzle.java
java BFS8Puzzle
```

## 12. Python Code

```python
# BFS to solve a planning problem (8-Puzzle)

from collections import deque

GOAL = [[1, 2, 3],
        [4, 5, 6],
        [7, 8, 0]]

START = [[1, 3, 6],
         [5, 0, 2],
         [4, 7, 8]]


def find_blank(state):
    for i in range(3):
        for j in range(3):
            if state[i][j] == 0:
                return i, j


def state_to_tuple(state):
    return tuple(tuple(row) for row in state)


def get_neighbors(state):
    neighbors = []
    x, y = find_blank(state)
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]

    for dx, dy in directions:
        nx, ny = x + dx, y + dy

        if 0 <= nx < 3 and 0 <= ny < 3:
            new_state = [row[:] for row in state]
            new_state[x][y], new_state[nx][ny] = new_state[nx][ny], new_state[x][y]
            neighbors.append(new_state)

    return neighbors


def bfs(start):
    queue = deque([(start, [])])
    visited = {state_to_tuple(start)}

    while queue:
        current_state, path = queue.popleft()

        if current_state == GOAL:
            return path + [current_state]

        for neighbor in get_neighbors(current_state):
            state_tuple = state_to_tuple(neighbor)

            if state_tuple not in visited:
                visited.add(state_tuple)
                queue.append((neighbor, path + [current_state]))

    return None


def print_solution(solution):
    if solution:
        print("\nSolution found in", len(solution) - 1, "moves:\n")

        for step, state in enumerate(solution):
            print("Step", step)
            for row in state:
                print(row)
            print()
    else:
        print("No solution found.")


solution = bfs(START)
print_solution(solution)
```

## 13. Java Code

```java
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
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `from collections import deque` | `deque` gives O(1) removal from the front, needed for BFS. |
| `GOAL = [[1, 2, 3], ...]` | The goal board; `0` is the blank tile. |
| `START = [[1, 3, 6], ...]` | The start board used in the demo (8 moves from the goal). |
| `def find_blank(state):` | Finds the row and column of the blank (`0`). |
| `def state_to_tuple(state):` | Converts the board to a tuple so it can be stored in a set. |
| `def get_neighbors(state):` | Generates every board reachable by one legal slide. |
| `directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]` | Up, down, left, right. |
| `if 0 <= nx < 3 and 0 <= ny < 3:` | Keeps the blank inside the board. |
| `new_state = [row[:] for row in state]` | Copies the board so the parent state is not modified. |
| `new_state[x][y], new_state[nx][ny] = new_state[nx][ny], new_state[x][y]` | Swaps the blank with the neighbouring tile - one move. |
| `def bfs(start):` | Breadth-first search. |
| `queue = deque([(start, [])])` | Each queue item is `(state, path taken so far)`. |
| `visited = {state_to_tuple(start)}` | Marks the start state before the loop. |
| `current_state, path = queue.popleft()` | **FIFO**: takes the shallowest state first, so the first solution is shortest. |
| `if current_state == GOAL:` | Goal test. |
| `return path + [current_state]` | Returns the complete solution path. |
| `for neighbor in get_neighbors(current_state):` | Expands the current state. |
| `if state_tuple not in visited:` | Checks **before** enqueueing, so no state enters the queue twice. |
| `visited.add(state_tuple)` | Marks the neighbour as seen. |
| `queue.append((neighbor, path + [current_state]))` | Enqueues the neighbour with its extended path. |
| `return None` | The queue emptied without reaching the goal. |
| `def print_solution(solution):` | Prints the number of moves and every board in the path. |
| `solution = bfs(START)` | Runs BFS from the start state. |

### Java code

| Code block | Explanation |
|---|---|
| `static final String GOAL = "123456780";` | Goal board encoded as 9 characters, row by row. |
| `static final String START = "136502478";` | Start board, also encoded row by row. |
| `static class Node` | Stores a state together with the path used to reach it. |
| `static List<String> getNeighbors(String state)` | Generates every one-slide successor. |
| `int blank = state.indexOf('0');` | Finds the blank position. |
| `int x = blank / 3; int y = blank % 3;` | Converts the index into a row and column. |
| `char[] next = state.toCharArray();` | Strings are immutable, so copy before swapping. |
| `next[blank]` / `next[i]` swap | Swaps the blank with the neighbouring tile. |
| `static List<String> bfs(String start)` | Breadth-first search. |
| `Queue<Node> queue = new LinkedList<>();` | FIFO queue. |
| `Set<String> visited = new HashSet<>();` | States already enqueued. |
| `queue.add(new Node(start, new ArrayList<>()));` | Adds the start with an empty path. |
| `visited.add(start);` | Marks the start before the loop. |
| `Node current = queue.poll();` | Takes the shallowest state. |
| `if (current.state.equals(GOAL))` | Goal test. |
| `if (!visited.contains(neighbor))` | Checks **before** enqueueing. |
| `List<String> newPath = new ArrayList<>(current.path);` | Copies the path so branches do not share a list. |
| `queue.add(new Node(neighbor, newPath));` | Enqueues the neighbour with its extended path. |
| `static void printBoard(String state)` | Prints 3 characters per row. |
| `main(...)` | Runs BFS and prints the solution. |

## 15. Sample Output

### Python

```text

Solution found in 8 moves:

Step 0
[1, 3, 6]
[5, 0, 2]
[4, 7, 8]

Step 1
[1, 3, 6]
[5, 2, 0]
[4, 7, 8]

Step 2
[1, 3, 0]
[5, 2, 6]
[4, 7, 8]

Step 3
[1, 0, 3]
[5, 2, 6]
[4, 7, 8]

Step 4
[1, 2, 3]
[5, 0, 6]
[4, 7, 8]

Step 5
[1, 2, 3]
[0, 5, 6]
[4, 7, 8]

Step 6
[1, 2, 3]
[4, 5, 6]
[0, 7, 8]

Step 7
[1, 2, 3]
[4, 5, 6]
[7, 0, 8]

Step 8
[1, 2, 3]
[4, 5, 6]
[7, 8, 0]
```

### Java

```text

Solution found in 8 moves:

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
