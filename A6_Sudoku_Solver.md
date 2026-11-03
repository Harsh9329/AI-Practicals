# Assignment A6 — Sudoku Solver using Backtracking and Constraint Propagation

> **Selected practical:** This is one of the bold / highlighted practicals in **Part A** of the manual. Mini-project is not included.

## 1. Aim

To implement a Sudoku solver using backtracking and constraint propagation techniques.

## 2. Prerequisite

Concept of Constraint Satisfaction Problems (CSP), recursion/backtracking, 2-D arrays.

## 3. Objective

- To understand Constraint Satisfaction Problems (CSPs) and their formulation.
- To study backtracking search combined with constraint propagation for CSPs.
- To design and implement a Sudoku solver using backtracking with row/column/sub-grid constraint checking.
- To analyse how constraint propagation reduces the search space compared to plain backtracking.

## 4. Outcome

Students will be able to formulate Sudoku as a Constraint Satisfaction Problem and implement a backtracking algorithm enhanced with constraint propagation to efficiently solve any valid Sudoku puzzle.

## 5. Software and Hardware Requirements

Python 3.x / Java / C++ compiler, Windows/Linux OS, 4 GB RAM minimum.

## 6. Theory

### Constraint Satisfaction Problems (CSP)

A CSP consists of:

- a set of **variables**, each with a **domain** of possible values, and
- a set of **constraints** specifying allowable combinations of values.

The task is to find an assignment of values to all variables that satisfies every constraint. Sudoku is a classical example of a CSP.

### Sudoku as a CSP

- **Variables:** each of the 81 cells in a 9 × 9 grid.
- **Domain:** integers 1 to 9 for each empty cell (a fixed clue cell has a domain of exactly one value).
- **Constraints (all-different constraints):**
  1. Every row must contain the digits 1–9 without repetition.
  2. Every column must contain the digits 1–9 without repetition.
  3. Every 3 × 3 sub-grid must contain the digits 1–9 without repetition.

### Backtracking Search

Backtracking is a depth-first search over partial assignments. At each step it:

1. Selects an unassigned variable.
2. Tries a value from its domain that is consistent with the constraints given the current partial assignment.
3. Recursively continues.
4. If no consistent value can be found for a variable, the algorithm **backtracks** — it undoes the most recent assignment and tries the next alternative value.

### Constraint Propagation

Constraint propagation techniques reduce the search space before/during backtracking by eliminating values from a variable's domain that cannot possibly satisfy the constraints. Two commonly combined techniques:

- **Forward checking:** whenever a value is assigned to a cell, remove that value from the domains of all cells sharing its row, column, or sub-grid.
- **Naked singles / constraint propagation:** if forward checking reduces a cell's domain to a single value, that value can be assigned immediately without further search, which cascades and often solves large portions of the grid without any guessing.

### Variable and Value Ordering Heuristics

- **Minimum Remaining Values (MRV):** choose the unassigned cell with the fewest legal values remaining — this fails fast and prunes the search tree earlier.
- **Least Constraining Value:** when trying values for a chosen cell, prefer the value that rules out the fewest choices for neighbouring cells.

## 7. Algorithm Steps

1. Represent the Sudoku grid as a 9 × 9 array; initialise it with the given clues.
2. Find the next empty cell (optionally using MRV).
3. For each candidate digit 1–9 consistent with row/column/sub-grid constraints:
   1. Place the digit (apply constraint propagation / forward checking).
   2. Recursively attempt to solve the rest of the grid.
   3. If the recursive call succeeds, return success.
   4. Otherwise, undo the digit (backtrack) and try the next candidate.
4. If no candidate works, return failure and backtrack to the previous cell.
5. If all cells are filled consistently, the puzzle is solved.

## 8. Diagram

The manual shows a 9 × 9 Sudoku grid divided into nine 3 × 3 boxes. In simple words:

```text
+-------+-------+-------+
| 5 3 . | . 7 . | . . . |
| 6 . . | 1 9 5 | . . . |
| . 9 8 | . . . | . 6 . |
+-------+-------+-------+
| 8 . . | . 6 . | . . 3 |
| 4 . . | 8 . 3 | . . 1 |
| 7 . . | . 2 . | . . 6 |
+-------+-------+-------+
| . 6 . | . . . | 2 8 . |
| . . . | 4 1 9 | . . 5 |
| . . . | . 8 . | . 7 9 |
+-------+-------+-------+
```

The dot (`.`) or `0` represents an empty cell. The solver fills every empty cell so that each row, column, and 3 × 3 box contains 1–9 exactly once.

## 9. Conclusion

A Sudoku solver was implemented using backtracking combined with constraint propagation. Before trying a value, `candidates()` builds the list of values allowed by the row, column and 3 x 3 box constraints, so illegal values are never tried. The program correctly solved the standard 9 x 9 Sudoku puzzle and produced the expected solution.

## 10. Viva Voce Questions

1. Define a Constraint Satisfaction Problem (CSP) formally.
2. Formulate Sudoku in terms of variables, domains, and constraints.
3. What is forward checking and how does it speed up backtracking?
4. Explain the Minimum Remaining Values (MRV) heuristic.
5. What is arc consistency? How does it relate to Sudoku constraint propagation?
6. Why does plain backtracking without any propagation become slow for hard puzzles?
7. What is the difference between backtracking search and local search (e.g., hill climbing) for CSPs?
8. Give another real-world problem that can be modelled as a CSP.

## 11. How to Run

### Python

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_A\A6_Sudoku\python"
python sudoku_solver.py
```

### Java

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_A\A6_Sudoku\java"
javac SudokuSolver.java
java SudokuSolver
```

## 12. Python Code

```python
# A6 - Sudoku Solver: backtracking + constraint propagation

BOARD = [
    [5, 3, 0, 0, 7, 0, 0, 0, 0],
    [6, 0, 0, 1, 9, 5, 0, 0, 0],
    [0, 9, 8, 0, 0, 0, 0, 6, 0],
    [8, 0, 0, 0, 6, 0, 0, 0, 3],
    [4, 0, 0, 8, 0, 3, 0, 0, 1],
    [7, 0, 0, 0, 2, 0, 0, 0, 6],
    [0, 6, 0, 0, 0, 0, 2, 8, 0],
    [0, 0, 0, 4, 1, 9, 0, 0, 5],
    [0, 0, 0, 0, 8, 0, 0, 7, 9]
]

def candidates(r, c):
    """Values already used in this row, column and 3x3 box."""
    used = set(BOARD[r]) | {BOARD[i][c] for i in range(9)}
    br, bc = (r // 3) * 3, (c // 3) * 3
    used |= {BOARD[i][j] for i in range(br, br + 3)
             for j in range(bc, bc + 3)}
    return [v for v in range(1, 10) if v not in used]


def solve():
    for r in range(9):
        for c in range(9):
            if BOARD[r][c] == 0:
                for v in candidates(r, c):
                    BOARD[r][c] = v
                    if solve():
                        return True
                    BOARD[r][c] = 0     # backtrack
                return False
    return True


def show():
    for r in range(9):
        if r in (3, 6):
            print("-" * 21)
        print(" ".join(str(v) if v else "." for v in BOARD[r]))


show()
print("Solved:", solve())
show()
```

## 13. Java Code

```java
public class SudokuSolver {
    static int[][] board = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };
    static boolean[] candidates(int r, int c) {
        boolean[] used = new boolean[10];
        for (int i = 0; i < 9; i++) {
            used[board[r][i]] = true;
            used[board[i][c]] = true;
        }
        int br = (r / 3) * 3, bc = (c / 3) * 3;
        for (int i = br; i < br + 3; i++)
            for (int j = bc; j < bc + 3; j++)
                used[board[i][j]] = true;
        return used;
    }

    static boolean solve() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    boolean[] used = candidates(r, c);
                    for (int v = 1; v <= 9; v++) {
                        if (!used[v]) {
                            board[r][c] = v;
                            if (solve()) return true;
                            board[r][c] = 0;    // backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    static void show() {
        for (int r = 0; r < 9; r++) {
            if (r == 3 || r == 6) System.out.println("---------------------");
            for (int c = 0; c < 9; c++)
                System.out.print(board[r][c] == 0 ? ". " : board[r][c] + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        show();
        System.out.println("Solved: " + solve());
        show();
    }
}
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `BOARD = [...]` | The puzzle as a 9×9 list. `0` means an empty cell. |
| `def candidates(r, c):` | **Constraint propagation**: finds all values still legal for cell `(r, c)`. |
| `used = set(BOARD[r]) \| {BOARD[i][c] for i in range(9)}` | Collects values already used in the same row and column. |
| `br, bc = (r // 3) * 3, (c // 3) * 3` | Top-left corner of the 3×3 box that contains the cell. |
| `used \|= {...}` | Adds all values already used inside that box. |
| `return [v for v in range(1, 10) if v not in used]` | The candidate list: digits 1–9 minus the used values. |
| `def solve():` | Recursive backtracking function. |
| `for r in range(9): for c in range(9):` | Scans for the first empty cell. |
| `if BOARD[r][c] == 0:` | Found an empty cell, so try to fill it. |
| `for v in candidates(r, c):` | Tries only legal values — illegal ones are never tried. |
| `BOARD[r][c] = v` | Places the candidate. |
| `if solve(): return True` | Recurses; if the rest solves, keep this value. |
| `BOARD[r][c] = 0` | **Backtrack**: undo the guess. |
| `return False` | No value works, so the caller must backtrack. |
| `def show():` | Prints the board with 3×3 separators. |

### Java code

| Code block | Explanation |
|---|---|
| `static int[][] board` | The puzzle. `0` = empty. |
| `static boolean[] candidates(int r, int c)` | Builds a boolean array of values already used. |
| `used[board[r][i]] = true;` / `used[board[i][c]] = true;` | Marks values used in the row and column. |
| `int br = (r / 3) * 3, bc = (c / 3) * 3;` | Top-left corner of the 3×3 box. |
| `used[board[i][j]] = true;` | Marks values used inside the box. |
| `static boolean solve()` | Recursive backtracking. |
| `if (board[r][c] == 0)` | Finds the first empty cell. |
| `for (int v = 1; v <= 9; v++) if (!used[v])` | Tries only legal values. |
| `board[r][c] = v; if (solve()) return true;` | Place and recurse. |
| `board[r][c] = 0;` | Backtrack. |
| `return false;` | No candidate worked. |
| `static void show()` | Prints the grid with separators. |

## 15. Sample Output

```text
5 3 . . 7 . . . .
6 . . 1 9 5 . . .
. 9 8 . . . . 6 .
---------------------
8 . . . 6 . . . 3
4 . . 8 . 3 . . 1
7 . . . 2 . . . 6
---------------------
. 6 . . . . 2 8 .
. . . 4 1 9 . . 5
. . . . 8 . . 7 9
Solved: True
5 3 4 6 7 8 9 1 2
6 7 2 1 9 5 3 4 8
1 9 8 3 4 2 5 6 7
---------------------
8 5 9 7 6 1 4 2 3
4 2 6 8 5 3 7 9 1
7 1 3 9 2 4 8 5 6
---------------------
9 6 1 5 3 7 2 8 4
2 8 7 4 1 9 6 3 5
3 4 5 2 8 6 1 7 9
```
