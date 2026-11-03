# Assignment A2 — Model-Based Intelligent Agent for Grid Navigation with Obstacles

> **Selected practical:** This is one of the bold / highlighted practicals in **Part A** of the manual. Mini-project is not included.

## 1. Aim

To design and implement a model-based intelligent agent capable of navigating a grid environment with obstacles using an internal state representation.

## 2. Prerequisite

Concepts of agents and environments; arrays/matrices; basic graph or grid representation; Assignment A1 (Simple Reflex Agent).

## 3. Objective

- To understand the limitations of simple reflex agents in partially observable environments.
- To study the concept of internal state and world model maintained by a model-based agent.
- To design and implement a model-based agent that can navigate a grid environment containing static obstacles.
- To analyse how the internal state helps the agent make better decisions than a purely reactive agent.

## 4. Outcome

Students will be able to build and maintain an internal state/model of a partially observable environment and use it to drive an agent's navigation decisions around obstacles toward a goal cell.

## 5. Software and Hardware Requirements

Python 3.x / Java / C++ compiler, matplotlib (optional, for visualisation), Windows/Linux OS, 4 GB RAM minimum.

## 6. Theory

### Limitation of Simple Reflex Agents

Simple reflex agents work well only when the environment is **fully observable** — that is, the current percept contains all information needed to choose the correct action. Many real-world environments (e.g., a robot in a room it cannot fully see) are only partially observable. In such cases, an agent that reacts to the current percept alone can make poor or repetitive decisions because it lacks memory of what it has already seen or done.

### Model-Based Reflex Agents

A model-based reflex agent overcomes this limitation by maintaining an **internal state** that depends on the percept history. To update this internal state, the agent needs two kinds of knowledge encoded in the agent program:

1. Information about how the world evolves independently of the agent — the **transition model**.
2. Information about how the agent's own actions affect the world — the **action model**.

Together these constitute the agent's **model of the world** — hence the name "model-based agent".

### Working Cycle

At each time step the model-based agent:

1. Receives a percept.
2. Updates its internal state using the previous internal state, the transition model, the action just performed, and the new percept.
3. Uses condition-action rules matched against the **internal state** (rather than the raw percept) to choose the next action.

### Grid Navigation Problem

In this assignment the environment is represented as an **N × N grid**. Some cells are marked as obstacles (blocked) and cannot be entered. The agent starts at a given cell and must reach a goal cell by moving **Up, Down, Left, or Right**, one cell at a time, while avoiding obstacles.

The agent maintains an internal grid-map (its model of the world) that it updates as it senses neighbouring cells, marking them **Free**, **Blocked**, or **Unknown**.

### Representation

The grid can be represented as a 2-D array/matrix where:

- `0` = free cell
- `1` = obstacle
- `S` = start
- `G` = goal

The internal state of the agent is a similarly sized matrix that starts as **Unknown** everywhere except cells the agent has already sensed, and is progressively filled in as the agent explores.

### Advantage over Simple Reflex Agent

Because the model-based agent remembers previously sensed obstacle information, it avoids revisiting known blocked cells and can plan more effectively even though the grid is only partially observable at any instant. This makes it strictly more capable than a simple reflex agent in environments that are not fully observable.

## 7. Algorithm

1. Initialize the grid environment and the agent's internal model (all cells Unknown).
2. Place the agent at the Start cell; sense the status of adjacent cells and update the internal model.
3. Repeat until the Goal is reached or no path remains:
   1. Using the internal model, choose the next move that makes progress toward the goal while avoiding known obstacles (e.g., a simple greedy Manhattan-distance rule, or BFS over the known-free cells).
   2. Execute the move; sense new percepts and update the internal model.
4. Report the path taken and whether the goal was reached.

## 8. Diagram

The manual shows the grid navigation problem and the agent's internal map. In simple words:

```text
Real grid (agent cannot see all cells)       Agent's internal map
S . . # . .                                  ? ? ? ? ? ?
. # . # . .        sense 4 neighbours        ? ? ? ? ? ?
. # . . . .       ------------------->       ? ? ? ? ? ?
. . . # . .                                  ? ? ? ? ? ?
# # . # . .                                  ? ? ? ? ? ?
. . . . . G                                  ? ? ? ? ? ?
```

The agent starts with mostly `?` (unknown) cells. As it moves, it replaces `?` with `0` (free) or `1` (blocked). It plans on this internal map, not on the real grid.

## 9. Conclusion

A model-based intelligent agent was implemented for grid navigation with static obstacles. The agent successfully maintained and updated an internal state (world model) while navigating, and reached the goal cell while avoiding all obstacles, demonstrating the advantage of internal state over pure reactive behaviour.

## 10. Viva Voce Questions

1. What is the role of an internal state in a model-based agent?
2. Differentiate between a transition model and a sensor model.
3. Why is a model-based agent more suitable than a simple reflex agent for partially observable environments?
4. How would you represent the agent's internal world model in memory?
5. What is the difference between a model-based reflex agent and a goal-based agent?
6. How does the agent handle a dynamic environment where obstacles can move?
7. What is Manhattan distance and how is it used as a heuristic here?
8. What happens if the goal cell is completely enclosed by obstacles?

## 11. How to Run

### Python

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_A\A2_Model_Based_Grid\python"
python model_based_agent.py
```

### Java

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_A\A2_Model_Based_Grid\java"
javac ModelBasedAgent.java
java ModelBasedAgent
```

## 12. Python Code

```python
# A2 - Model-Based Agent: Grid Navigation with Obstacles

from collections import deque

GRID = [
    "S...",
    ".X..",
    ".X..",
    "...G"
]
N = 4
START, GOAL = (0, 0), (3, 3)
DIRS = [(-1, 0), (1, 0), (0, -1), (0, 1)]   # up, down, left, right

# model[r][c] = -1 unknown, 0 free, 1 blocked
model = [[-1] * N for _ in range(N)]


def sense(r, c):
    """Sense the current cell and its four neighbours."""
    for dr, dc in DIRS + [(0, 0)]:
        nr, nc = r + dr, c + dc
        if 0 <= nr < N and 0 <= nc < N:
            model[nr][nc] = 1 if GRID[nr][nc] == "X" else 0


def plan(start):
    """BFS on the internal model. Unknown cells are treated as free."""
    queue = deque([start])
    parent = {start: None}

    while queue:
        r, c = queue.popleft()
        if (r, c) == GOAL:
            path = []
            while (r, c) != start:
                path.append((r, c))
                r, c = parent[(r, c)]
            return path[::-1]

        for dr, dc in DIRS:
            nr, nc = r + dr, c + dc
            if 0 <= nr < N and 0 <= nc < N and (nr, nc) not in parent:
                if model[nr][nc] != 1:          # free or unknown
                    parent[(nr, nc)] = (r, c)
                    queue.append((nr, nc))
    return []


position = START
sense(*position)
steps = 0

while position != GOAL:
    path = plan(position)
    if not path:
        print("No path to the goal.")
        break

    position = path[0]          # take the first step of the plan
    sense(*position)
    steps += 1
    print("Step", steps, ": move to", position)

print("Goal reached:", position == GOAL)
print("Model (-1 unknown, 0 free, 1 blocked):")
for row in model:
    print(row)
```

## 13. Java Code

```java
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
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `GRID = ["S...", ".X..", ".X..", "...G"]` | The real 4×4 environment (`S` start, `G` goal, `X` obstacle). The agent cannot read it all at once. |
| `DIRS = [...]` | The four moves: up, down, left, right. |
| `model = [[-1] * N ...]` | The **internal state**. `-1` = unknown, `0` = free, `1` = blocked. |
| `def sense(r, c):` | The sensor model. It reads only the current cell and its four neighbours. |
| `model[nr][nc] = 1 if GRID[nr][nc] == "X" else 0` | Saves each sensed cell into the internal map. |
| `def plan(start):` | Plans on the **internal model**, not on `GRID`. |
| `queue = deque([start])` / `parent = {start: None}` | Standard BFS queue and parent map for path reconstruction. |
| `if model[nr][nc] != 1:` | Unknown cells are treated as free (optimistic planning); known obstacles are avoided. |
| `return path[::-1]` | Rebuilds the path from start to goal. |
| `while position != GOAL:` | One loop iteration = one move. |
| `position = path[0]` | Takes only the first step of the plan, then re-plans. |
| `sense(*position)` | **Model update** — this is what makes the agent model-based. |
| `print(...)` | Shows the moves and the final internal map. |

### Java code

| Code block | Explanation |
|---|---|
| `static final String[] GRID = {...}` | The real environment. |
| `static int[][] model = new int[N][N];` | Internal map: `-1` unknown, `0` free, `1` blocked. |
| `static void sense(int r, int c)` | Reads the current cell and its four neighbours only. |
| `model[nr][nc] = GRID[nr].charAt(nc) == 'X' ? 1 : 0;` | Stores the sensed value in the model. |
| `static List<int[]> plan(int sr, int sc)` | BFS on the model; returns the path as a list of cells. |
| `int[][] parent` | Stores the parent cell of each visited cell (encoded as `r * N + c`). |
| `if (... && model[nr][nc] != 1)` | Unknown and free cells are allowed; known obstacles are skipped. |
| `path.addFirst(...)` | Builds the path from goal back to start. |
| `while (!(r == GOAL[0] && c == GOAL[1]))` | Main agent loop. |
| `List<int[]> path = plan(r, c);` | Re-plan on every step using the latest model. |
| `r = next[0]; c = next[1]; sense(r, c);` | Move, then sense and update the model. |
| `System.out.println(...)` | Prints the moves and the final model. |

## 15. Sample Output

```text
Step 1 : move to (1, 0)
Step 2 : move to (2, 0)
Step 3 : move to (3, 0)
Step 4 : move to (3, 1)
Step 5 : move to (3, 2)
Step 6 : move to (3, 3)
Goal reached: True
Model (-1 unknown, 0 free, 1 blocked):
[0, 0, -1, -1]
[0, 1, -1, -1]
[0, 1, 0, 0]
[0, 0, 0, 0]
```
