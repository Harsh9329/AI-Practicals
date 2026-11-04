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

- `'O'` = free cell
- `'X'` = obstacle
- start and goal are `(row, column)` tuples

The internal state of the agent is a similarly sized matrix that starts as **Unknown** (`'?'`) everywhere except cells the agent has already sensed, and is progressively filled in as the agent explores. Sensed free cells are stored as `'O'` and sensed obstacles as `'X'`.

### Advantage over Simple Reflex Agent

Because the model-based agent remembers previously sensed obstacle information, it avoids revisiting known blocked cells and can plan more effectively even though the grid is only partially observable at any instant. This makes it strictly more capable than a simple reflex agent in environments that are not fully observable.

## 7. Algorithm

1. Initialize the grid environment and the agent's internal model (all cells Unknown).
2. Place the agent at the Start cell; sense the status of adjacent cells and update the internal model.
3. Repeat until the Goal is reached or no path remains:
   1. Using the internal model, try moves that reduce the distance to the goal first; if those cells are blocked or already visited, fall back to any unvisited free neighbour (a greedy rule with a visited set).
   2. Execute the move; sense new percepts and update the internal model.
   3. Stop when the goal is reached, when no move is possible, or after 50 steps as a safety cap.
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

The agent starts with mostly `?` (unknown) cells. As it moves, it replaces `?` with `O` (free) or `X` (blocked). Every move is chosen using this internal map, not the real grid.

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
# Model-Based Intelligent Agent for Grid Navigation

class ModelBasedAgent:
    def __init__(self, grid, start, goal):
        self.grid = grid
        self.position = start
        self.goal = goal

        # Internal model: '?' = not sensed yet, 'O' = free, 'X' = obstacle
        self.model = [['?' for _ in row] for row in grid]
        self.visited = {start}
        self.sense()

    def sense(self):
        x, y = self.position
        for dx, dy in [(0, 0), (-1, 0), (1, 0), (0, -1), (0, 1)]:
            nx, ny = x + dx, y + dy
            if 0 <= nx < len(self.grid) and 0 <= ny < len(self.grid[0]):
                self.model[nx][ny] = self.grid[nx][ny]

    def display_grid(self):
        for row in self.model:
            print(row)
        print()

    def move(self):
        x, y = self.position
        gx, gy = self.goal

        # First try moves that reduce the distance to the goal
        moves = []
        if x < gx:
            moves.append((x + 1, y))
        if y < gy:
            moves.append((x, y + 1))
        if x > gx:
            moves.append((x - 1, y))
        if y > gy:
            moves.append((x, y - 1))

        # Then try any other neighbour
        moves += [(x + 1, y), (x, y + 1), (x - 1, y), (x, y - 1)]

        for nx, ny in moves:
            if 0 <= nx < len(self.grid) and 0 <= ny < len(self.grid[0]):
                if self.model[nx][ny] != 'X' and (nx, ny) not in self.visited:
                    self.position = (nx, ny)
                    self.visited.add((nx, ny))
                    self.sense()
                    return True
        return False

    def run(self):
        step = 1
        while self.position != self.goal and step <= 50:
            print(f"Step {step}: Current Position = {self.position}")
            if not self.move():
                print("No path to the goal.")
                return
            step += 1

        print(f"\nGoal Reached at {self.position}")


# Grid Environment
# O = Free Cell
# X = Obstacle

grid = [
    ['O', 'O', 'O', 'O'],
    ['O', 'X', 'O', 'O'],
    ['O', 'X', 'O', 'O'],
    ['O', 'O', 'O', 'O']
]

start = (0, 0)
goal = (3, 3)

agent = ModelBasedAgent(grid, start, goal)

print("Agent's initial model ('?' = not sensed yet):")
agent.display_grid()

agent.run()
```

## 13. Java Code

```java
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
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `class ModelBasedAgent:` | Keeps the grid, position, goal and internal model together in one object. |
| `self.model = [['?' for _ in row] for row in grid]` | The **internal state**: every cell starts unknown (`?`). |
| `self.visited = {start}` | Remembers visited cells so the agent does not move back and forth. |
| `self.sense()` | Senses the starting cell before the first move. |
| `def sense(self):` | The sensor model: reads only the current cell and its four neighbours. |
| `for dx, dy in [(0, 0), (-1, 0), (1, 0), (0, -1), (0, 1)]:` | Current cell plus up, down, left and right. |
| `if 0 <= nx < len(self.grid) and ...` | Stays inside the grid. |
| `self.model[nx][ny] = self.grid[nx][ny]` | Writes each sensed value into the internal model. |
| `def display_grid(self):` | Prints the agent's model, not the real grid. |
| `def move(self):` | Chooses one move towards the goal. |
| `moves = []` then `if x < gx: moves.append(...)` | First tries directions that reduce the distance to the goal. |
| `moves += [(x + 1, y), (x, y + 1), (x - 1, y), (x, y - 1)]` | Fallback: any neighbour, used when the direct route is blocked. |
| `for nx, ny in moves:` | Tries the candidate moves in order. |
| `if self.model[nx][ny] != 'X'` | Skips cells known to be obstacles. |
| `and (nx, ny) not in self.visited` | Skips cells already visited. |
| `self.position = (nx, ny)` / `self.visited.add(...)` / `self.sense()` | Moves, records the cell and **updates the model**. |
| `return True` / `return False` | Reports whether a move was possible. |
| `def run(self):` | The main agent loop. |
| `while self.position != self.goal and step <= 50:` | Stops at the goal, with a 50-step safety cap. |
| `if not self.move():` | Prints "No path to the goal." and exits if the agent is trapped. |
| `print(f"\nGoal Reached at {self.position}")` | Final result. |

### Java code

| Code block | Explanation |
|---|---|
| `static class Agent {` | Keeps the environment, position, goal and internal model together. |
| `char[][] model;` | The **internal state** - the agent's own map of the world. |
| `Set<String> visited = new HashSet<>();` | Cells already visited, stored as `"row,col"` strings. |
| `for (char[] row : model) Arrays.fill(row, '?');` | Starts with every cell unknown (`?`). |
| `sense();` | Senses the starting cell and its neighbours immediately. |
| `void sense() {` | The sensor model: reads only the current cell and its four neighbours. |
| `int[][] dirs = {{0,0},{-1,0},{1,0},{0,-1},{0,1}};` | Current cell plus up, down, left and right. |
| `if (nx >= 0 && nx < grid.length && ...)` | Bounds check before reading a neighbour. |
| `model[nx][ny] = grid[nx][ny];` | Stores each sensed value in the internal model. |
| `boolean move() {` | Chooses one move towards the goal. |
| `if (x < gx) moves.add(...)` etc. | First tries directions that reduce the distance to the goal. |
| `moves.add(...)` four more times | Fallback: any neighbour, used when the direct route is blocked. |
| `for (int[] m : moves) {` | Tries the candidate moves in order. |
| `if (... && model[nx][ny] != 'X')` | Skips cells known to be obstacles. |
| `&& !visited.contains(key)` | Skips cells already visited, so the agent cannot oscillate. |
| `position = ...; visited.add(key); sense();` | Moves, records the cell and **updates the model**. |
| `return true;` / `return false;` | Reports whether a move was possible. |
| `void run() {` | The main agent loop. |
| `while (... && step <= 50)` | Stops at the goal, with a 50-step safety cap. |
| `if (!move()) { ... return; }` | Prints "No path to the goal." and exits if the agent is trapped. |
| `main(...)` | Creates the 4x4 grid and starts the agent at `(0,0)` with goal `(3,3)`. |

## 15. Sample Output

### Python

```text
Agent's initial model ('?' = not sensed yet):
['O', 'O', '?', '?']
['O', '?', '?', '?']
['?', '?', '?', '?']
['?', '?', '?', '?']

Step 1: Current Position = (0, 0)
Step 2: Current Position = (1, 0)
Step 3: Current Position = (2, 0)
Step 4: Current Position = (3, 0)
Step 5: Current Position = (3, 1)
Step 6: Current Position = (3, 2)

Goal Reached at (3, 3)
```

### Java

```text
Agent's initial model ('?' = not sensed yet):
[O, O, ?, ?]
[O, ?, ?, ?]
[?, ?, ?, ?]
[?, ?, ?, ?]

Step 1: Current Position = (0, 0)
Step 2: Current Position = (1, 0)
Step 3: Current Position = (2, 0)
Step 4: Current Position = (3, 0)
Step 5: Current Position = (3, 1)
Step 6: Current Position = (3, 2)

Goal Reached at (3, 3)
```
