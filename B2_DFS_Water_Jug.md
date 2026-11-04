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

DFS was implemented using a LIFO stack. It found a valid 4-step solution for the 4-litre / 3-litre jug problem and printed the action that produced every state. The visited set prevented the same state from being pushed twice.

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
cd Part_B/B2_DFS_Water_Jug/python
python dfs_water_jug.py
```

### Java

```bash
cd Part_B/B2_DFS_Water_Jug/java
javac DFSWaterJug.java
java DFSWaterJug
```

## 12. Python Code

```python
# Water Jug Problem using DFS

def dfs(jug1, jug2, target):
    stack = [((0, 0), [])]      # (state, path)
    visited = {(0, 0)}

    while stack:
        (x, y), path = stack.pop()

        # Check if target is reached
        if x == target or y == target:
            for action, state in path:
                print(action, "->", state)
            print("\nTarget achieved in", len(path), "steps!")
            return

        # Possible moves: (new state, action name)
        next_states = [
            ((jug1, y), "Fill Jug1"),
            ((x, jug2), "Fill Jug2"),
            ((0, y), "Empty Jug1"),
            ((x, 0), "Empty Jug2"),
            ((max(0, x - (jug2 - y)), min(jug2, y + x)), "Pour Jug1 -> Jug2"),
            ((min(jug1, x + y), max(0, y - (jug1 - x))), "Pour Jug2 -> Jug1")
        ]

        for state, action in next_states:
            if state not in visited:
                visited.add(state)
                stack.append((state, path + [(action, state)]))

    print("No solution found")


# Driver Code
jug1_capacity = 4
jug2_capacity = 3
target = 2

dfs(jug1_capacity, jug2_capacity, target)
```

## 13. Java Code

```java
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
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `def dfs(jug1, jug2, target):` | Depth-first search over the jug states. |
| `stack = [((0, 0), [])]` | **Stack** for DFS; each item is `(state, path taken so far)`. |
| `visited = {(0, 0)}` | Both jugs start empty, and that state is already seen. |
| `(x, y), path = stack.pop()` | **LIFO**: takes the most recently added state, so the search goes deep first. |
| `if x == target or y == target:` | Goal test: either jug contains the target amount. |
| `for action, state in path: print(action, "->", state)` | Prints the action and the state it produced, in order. |
| `print("\nTarget achieved in", len(path), "steps!")` | Prints the number of actions in the solution. |
| `return` | Stops the search once a solution is found. |
| `next_states = [...]` | The six legal actions, each stored with its name. |
| `((jug1, y), "Fill Jug1")` / `((x, jug2), "Fill Jug2")` | Fill either jug completely. |
| `((0, y), "Empty Jug1")` / `((x, 0), "Empty Jug2")` | Empty either jug. |
| `max(0, x - (jug2 - y))` / `min(jug2, y + x)` | Pour Jug1 -> Jug2; only the amount that fits in Jug2 moves. |
| `min(jug1, x + y)` / `max(0, y - (jug1 - x))` | Pour Jug2 -> Jug1 without overflowing Jug1. |
| `for state, action in next_states:` | Tries all six actions. |
| `if state not in visited:` | Skips states already reached. |
| `visited.add(state)` | Marks the state before pushing. |
| `stack.append((state, path + [(action, state)]))` | Pushes the new state with the action added to its path. |
| `print("No solution found")` | Runs when the stack empties without a solution. |
| `jug1_capacity = 4` / `jug2_capacity = 3` / `target = 2` | Problem instance used in the lab. |

### Java code

| Code block | Explanation |
|---|---|
| `static class Node` | Stores `x`, `y` and the path of actions used to reach this state. |
| `Stack<Node> stack = new Stack<>();` | **LIFO** structure for DFS. |
| `Set<String> visited = new HashSet<>();` | States already reached, stored as `"x,y"`. |
| `stack.push(new Node(0, 0, new ArrayList<>()));` | Start with both jugs empty. |
| `visited.add("0,0");` | Marks the start state. |
| `Node current = stack.pop();` | Takes the deepest state. |
| `if (x == target || y == target)` | Goal test. |
| `for (String line : current.path)` | Prints the action path in order. |
| `System.out.println("\nTarget achieved in " + current.path.size() + " steps!");` | Prints the solution length. |
| `int[][] states = {...};` | The six resulting states after the six legal actions. |
| `String[] actions = {...};` | Names of the six actions. |
| `{Math.max(0, x - (jug2 - y)), Math.min(jug2, y + x)}` | Pour Jug1 -> Jug2, limited by the space in Jug2. |
| `{Math.min(jug1, x + y), Math.max(0, y - (jug1 - x))}` | Pour Jug2 -> Jug1, limited by the space in Jug1. |
| `for (int i = 0; i < states.length; i++)` | Tries all six actions. |
| `if (!visited.contains(key))` | Skips repeated states. |
| `List<String> newPath = new ArrayList<>(current.path);` | Copies the path before adding the new action. |
| `newPath.add(actions[i] + " -> (" + nx + ", " + ny + ")");` | Records the action and the state it produced. |
| `stack.push(new Node(nx, ny, newPath));` | Pushes the successor. |
| `System.out.println("No solution found");` | Runs when the stack becomes empty. |

## 15. Sample Output

### Python

```text
Fill Jug2 -> (0, 3)
Pour Jug2 -> Jug1 -> (3, 0)
Fill Jug2 -> (3, 3)
Pour Jug2 -> Jug1 -> (4, 2)

Target achieved in 4 steps!
```

### Java

```text
Fill Jug2 -> (0, 3)
Pour Jug2 -> Jug1 -> (3, 0)
Fill Jug2 -> (3, 3)
Pour Jug2 -> Jug1 -> (4, 2)

Target achieved in 4 steps!
```
