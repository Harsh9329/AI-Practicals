# Assignment B2 — DFS for Problem Solving (Water Jug Problem)

> **Selected practical:** This is one of the bold / highlighted practicals in **Part B** of the manual. Mini-project is not included.

## 1. Aim

To implement Depth-First Search (DFS) to solve a problem-solving task such as the Water Jug problem, Missionaries and Cannibals problem, maze solving, or Blocks World problem.

## 2. Prerequisite

State-space representation, stack/recursion, basics of graph traversal.

## 3. Objective

- To understand the Depth First Search (DFS) strategy and its trade-offs versus BFS.
- To formulate a classic AI puzzle (Water Jug / Missionaries and Cannibals / Maze) as a state-space search problem.
- To implement DFS to find a solution path for the chosen problem.
- To analyse completeness, optimality, time and space complexity of DFS.

## 4. Outcome

Students will be able to formulate a classic search puzzle as a state-space problem and implement DFS to explore the state space and find a valid solution path.

## 5. Software and Hardware Requirements

Python 3.x / Java / C++ compiler (stack via recursion or explicit stack), Windows/Linux OS, 4 GB RAM minimum.

## 6. Theory

### Depth First Search (DFS)

DFS always expands the deepest unexpanded node in the current search tree first. It uses a **LIFO stack** (or equivalently, recursion) for the frontier: the most recently generated node is expanded next. When a branch reaches a dead end (no more unexplored successors), the search backtracks to the shallowest unexpanded node.

### Properties of DFS

- **Completeness:** DFS is not complete in infinite state spaces or graphs with cycles (unless cycle/repeated-state checking is used) — it can go down an infinite branch and never find the solution.
- **Optimality:** DFS is not optimal — it may return a longer path even though a shorter one exists, because it stops at the first goal found regardless of depth.
- **Time Complexity:** `O(b^m)`, where `m` is the maximum depth of the search tree (which may be much larger than the solution depth).
- **Space Complexity:** `O(b*m)` — DFS only needs to store the nodes on the current path plus their unexpanded siblings, which is a major memory advantage over BFS.

### Example: Water Jug Problem

Given two jugs of known capacities (e.g., 4 litres and 3 litres) and no measuring marks, the goal is to measure out a target quantity (e.g., 2 litres) using operations:

- Fill a jug completely.
- Empty a jug completely.
- Pour from one jug into the other until either the source is empty or the destination is full.

Each state is the pair `(amount in jug 1, amount in jug 2)`. DFS explores this state graph looking for a state where one jug holds the target amount.

### Example: Missionaries and Cannibals

Three missionaries and three cannibals must cross a river using a boat that holds at most two people, such that cannibals never outnumber missionaries on either bank. A state is `(#missionaries, #cannibals, boat position)` on the starting bank. DFS explores possible boat crossings, backtracking whenever an unsafe state (cannibals > missionaries on a bank, with missionaries present) is reached.

### Example: Maze Solving

A maze is represented as a 2-D grid with walls and open paths. DFS explores one path as deep as possible, backtracking upon hitting a dead end, until the exit is found. This mirrors the natural "follow-the-wall" exploration strategy used in physical maze solving.

## 7. Algorithm Steps

1. Push the start state onto a stack (or begin recursion); mark it visited.
2. While the stack is not empty:
   1. Pop the top state `n`.
   2. If `n` is the goal, reconstruct and return the solution path.
   3. Otherwise, generate valid, unvisited successors of `n`; push them onto the stack and mark visited.
3. If the stack empties without finding the goal, report failure.

## 8. Diagram

The manual shows the DFS search tree. In simple words:

```text
                 Start
                /     \
             S1        S2
            /  \        \
         S1a  S1b       S2a
          |
        Goal   <-- DFS goes deep here first
```

DFS follows one branch as far as possible before backtracking. It does **not** explore level by level like BFS.

For the Water Jug problem, each state is a pair:

```text
(0, 0)  ->  (0, 3)  ->  (3, 0)  ->  (3, 3)  ->  (4, 2)
```

Each arrow is one of the six allowed actions.

## 9. Conclusion

DFS was implemented and used to solve the chosen classic AI puzzle. The algorithm successfully found a valid solution path by exploring the state space depth-first with backtracking, and its memory efficiency compared to BFS was observed on the given problem instances.

## 10. Viva Voce Questions

1. Why is DFS not guaranteed to be optimal?
2. How does DFS achieve better space complexity than BFS?
3. What problems can arise if DFS is applied to a graph with cycles, and how is it fixed?
4. Formulate the Water Jug problem's states, actions, and goal test.
5. What safety constraint must hold at every state of the Missionaries and Cannibals problem?
6. Compare Depth-Limited Search and Iterative Deepening Search with plain DFS.
7. In what kind of problems would you prefer DFS over BFS, and why?
8. How would you avoid revisiting the same state repeatedly in DFS (cycle checking)?

## 11. How to Run

### Python

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_B\B2_DFS_Water_Jug\python"
python dfs_water_jug.py
```

### Java

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_B\B2_DFS_Water_Jug\java"
javac DFSWaterJug.java
java DFSWaterJug
```

## 12. Python Code

```python
# B2 - DFS for the Water Jug Problem

CAP_A, CAP_B = 4, 3
TARGET = 2


def moves(a, b):
    """The six legal actions from state (a, b)."""
    p = min(a, CAP_B - b)       # water poured A -> B
    q = min(b, CAP_A - a)       # water poured B -> A
    return [
        (CAP_A, b, "Fill A"),
        (a, CAP_B, "Fill B"),
        (0, b, "Empty A"),
        (a, 0, "Empty B"),
        (a - p, b + p, "Pour A to B"),
        (a + q, b - q, "Pour B to A"),
    ]


stack = [((0, 0), [])]          # (state, path so far)
visited = {(0, 0)}

while stack:
    (a, b), path = stack.pop()  # LIFO = depth first
    if a == TARGET or b == TARGET:
        for action, state in path:
            print(action, "->", state)
        print("Goal reached in", len(path), "steps")
        break

    for na, nb, action in moves(a, b):
        if (na, nb) not in visited:
            visited.add((na, nb))
            stack.append(((na, nb), path + [(action, (na, nb))]))
else:
    print("No solution found")
```

## 13. Java Code

```java
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
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `CAP_A, CAP_B = 4, 3` | Capacities of the two jugs. |
| `TARGET = 2` | We want exactly 2 litres in either jug. |
| `def moves(a, b):` | Returns the six legal actions from state `(a, b)`. |
| `p = min(a, CAP_B - b)` | Water that can be poured from A to B. |
| `q = min(b, CAP_A - a)` | Water that can be poured from B to A. |
| `(CAP_A, b, "Fill A")` | Fill jug A completely. |
| `(a, CAP_B, "Fill B")` | Fill jug B completely. |
| `(0, b, "Empty A")` | Empty jug A. |
| `(a, 0, "Empty B")` | Empty jug B. |
| `(a - p, b + p, "Pour A to B")` | Pour A into B until A is empty or B is full. |
| `(a + q, b - q, "Pour B to A")` | Pour B into A until B is empty or A is full. |
| `stack = [((0, 0), [])]` | **Stack** for DFS; each item is `(state, path)`. |
| `visited = {(0, 0)}` | Cycle check: never push the same state twice. |
| `(a, b), path = stack.pop()` | **LIFO**: takes the most recent state → depth-first. |
| `if a == TARGET or b == TARGET:` | Goal test. |
| `for action, state in path:` | Prints the solution actions in order. |
| `for na, nb, action in moves(a, b):` | Tries every legal next state. |
| `if (na, nb) not in visited:` | Skips states already reached. |
| `visited.add((na, nb))` | Marks the state before pushing. |
| `stack.append(((na, nb), path + [...]))` | Pushes the successor with its updated path. |
| `else: print("No solution found")` | Runs when the stack empties without reaching the goal. |

### Java code

| Code block | Explanation |
|---|---|
| `static final int CAP_A = 4, CAP_B = 3, TARGET = 2;` | Problem constants. |
| `static class Node` | Stores `a`, `b` and the path to this state. |
| `static void push(...)` | Helper that checks `visited`, copies the path, adds the action and pushes. |
| `String key = a + "," + b;` | One string key for the `Set`. |
| `if (visited.contains(key)) return;` | Cycle check. |
| `new ArrayList<>(path)` | Copies the path so branches do not share the same list. |
| `ArrayDeque<Node> stack` | Stack for DFS (`push`/`pop` at the front). |
| `Node cur = stack.pop();` | Takes the deepest state. |
| `if (cur.a == TARGET \|\| cur.b == TARGET)` | Goal test. |
| `int p = Math.min(cur.a, CAP_B - cur.b);` | Amount poured A → B. |
| `int q = Math.min(cur.b, CAP_A - cur.a);` | Amount poured B → A. |
| `push(...)` six times | Generates all six legal actions. |
| `System.out.println(...)` | Prints the path and the number of steps. |

## 15. Sample Output

```text
Fill B -> (0, 3)
Pour B to A -> (3, 0)
Fill B -> (3, 3)
Pour B to A -> (4, 2)
Goal reached in 4 steps
```
