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

Constraint propagation reduces the search space by eliminating values that cannot possibly satisfy the constraints. In this program, before any value is tried in a cell, `candidates(board, row, col)` builds the list of digits that do **not** already appear in that cell's row, column or 3 × 3 box. Illegal values are removed before recursion, so the branching factor is much smaller than trying all digits 1–9.

More advanced solvers also use **forward checking** (removing a placed value from neighbouring domains) and **naked singles** (filling a cell when only one value remains), but the candidate-list check above is enough for this lab.

### Variable and Value Ordering Heuristics

- **Minimum Remaining Values (MRV):** choose the unassigned cell with the fewest legal values remaining — this fails fast and prunes the search tree earlier.
- **Least Constraining Value:** when trying values for a chosen cell, prefer the value that rules out the fewest choices for neighbouring cells.

## 7. Algorithm Steps

1. Represent the Sudoku grid as a 9 × 9 array; initialise it with the given clues.
2. Find the first empty cell (scanning row by row).
3. Build the candidate list for that cell with `candidates()` — only digits allowed by the row, column and 3 × 3 box.
4. For each candidate digit:
   1. Place the digit in the cell.
   2. Recursively attempt to solve the rest of the grid.
   3. If the recursive call succeeds, return success.
   4. Otherwise, undo the digit (backtrack) and try the next candidate.
5. If no candidate works, return failure and backtrack to the previous cell.
6. If no empty cell remains, the puzzle is solved.

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
cd Part_A/A6_Sudoku/python
python sudoku_solver.py
```

### Java

```bash
cd Part_A/A6_Sudoku/java
javac SudokuSolver.java
java SudokuSolver
```

## 12. Python Code

```python
# Sudoku Solver using Backtracking and Constraint Propagation

def print_board(board):
    for row in board:
        print(row)


def find_empty(board):
    for i in range(9):
        for j in range(9):
            if board[i][j] == 0:
                return (i, j)
    return None


def is_valid(board, num, pos):
    row, col = pos

    # Check row
    for j in range(9):
        if board[row][j] == num and j != col:
            return False

    # Check column
    for i in range(9):
        if board[i][col] == num and i != row:
            return False

    # Check 3x3 box
    box_x = col // 3
    box_y = row // 3

    for i in range(box_y * 3, box_y * 3 + 3):
        for j in range(box_x * 3, box_x * 3 + 3):
            if board[i][j] == num and (i, j) != pos:
                return False

    return True


def candidates(board, row, col):
    # Constraint propagation: only legal numbers for this cell
    return [num for num in range(1, 10) if is_valid(board, num, (row, col))]


def solve(board):
    empty = find_empty(board)

    if not empty:
        return True

    row, col = empty

    for num in candidates(board, row, col):
        board[row][col] = num

        if solve(board):
            return True

        # Backtrack
        board[row][col] = 0

    return False


# Sudoku Puzzle (0 represents empty cell)
board = [
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

print("Sudoku Puzzle:")
print_board(board)

if solve(board):
    print("\nSolved Sudoku:")
    print_board(board)
else:
    print("No solution exists.")
```

## 13. Java Code

```java
import java.util.*;

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

    static void printBoard() {
        for (int[] row : board) System.out.println(Arrays.toString(row));
    }

    static int[] findEmpty() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (board[i][j] == 0) return new int[]{i, j};
        return null;
    }

    static boolean isValid(int num, int row, int col) {
        // Check row
        for (int j = 0; j < 9; j++)
            if (board[row][j] == num && j != col) return false;

        // Check column
        for (int i = 0; i < 9; i++)
            if (board[i][col] == num && i != row) return false;

        // Check 3x3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        for (int i = boxRow; i < boxRow + 3; i++)
            for (int j = boxCol; j < boxCol + 3; j++)
                if (board[i][j] == num && !(i == row && j == col)) return false;

        return true;
    }

    static List<Integer> candidates(int row, int col) {
        // Constraint propagation: only legal numbers for this cell
        List<Integer> list = new ArrayList<>();
        for (int num = 1; num <= 9; num++)
            if (isValid(num, row, col)) list.add(num);
        return list;
    }

    static boolean solve() {
        int[] empty = findEmpty();
        if (empty == null) return true;

        int row = empty[0], col = empty[1];

        for (int num : candidates(row, col)) {
            board[row][col] = num;

            if (solve()) return true;

            // Backtrack
            board[row][col] = 0;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Sudoku Puzzle:");
        printBoard();

        if (solve()) {
            System.out.println("\nSolved Sudoku:");
            printBoard();
        } else {
            System.out.println("No solution exists.");
        }
    }
}
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `def print_board(board):` | Prints the 9 rows of the puzzle. |
| `def find_empty(board):` | Scans row by row for the first cell containing `0`. |
| `return (i, j)` | Position of the empty cell. |
| `return None` | No empty cell found, so the puzzle is full. |
| `def is_valid(board, num, pos):` | Checks whether `num` can be placed at `pos` without breaking Sudoku rules. |
| row loop | Is `num` already in the same row? |
| column loop | Is `num` already in the same column? |
| `box_x = col // 3` / `box_y = row // 3` | Finds which 3x3 box the cell belongs to. |
| box loops | Is `num` already inside that 3x3 box? |
| `return True` | All three constraints passed, so the value is legal. |
| `def candidates(board, row, col):` | **Constraint propagation**: builds the list of values still legal for this cell. |
| `return [num for num in range(1, 10) if is_valid(...)]` | Keeps only the values that pass all constraints. |
| `def solve(board):` | Recursive backtracking function. |
| `empty = find_empty(board)` | Chooses the next empty cell. |
| `if not empty: return True` | Every cell is filled, so the puzzle is solved. |
| `for num in candidates(board, row, col):` | Tries only legal values. |
| `board[row][col] = num` | Places the candidate. |
| `if solve(board): return True` | Recurses; keeps this value if the rest of the board solves. |
| `board[row][col] = 0` | **Backtrack**: undo the guess. |
| `return False` | No candidate works, so the caller must backtrack. |
| `if solve(board): print_board(board)` | Solves the puzzle and prints the result. |

### Java code

| Code block | Explanation |
|---|---|
| `static int[][] board = {...};` | The puzzle as a 9x9 array; `0` means empty. |
| `static void printBoard()` | Prints the 9 rows. |
| `static int[] findEmpty()` | Scans for the first `0`; returns `{row, col}` or `null`. |
| `static boolean isValid(int num, int row, int col)` | Checks the three Sudoku constraints. |
| row loop | Is `num` already in this row? |
| column loop | Is `num` already in this column? |
| `int boxRow = (row / 3) * 3;` / `int boxCol = (col / 3) * 3;` | Top-left corner of the 3x3 box. |
| box loops | Is `num` already inside that box? |
| `return true;` | The value is legal. |
| `static List<Integer> candidates(int row, int col)` | **Constraint propagation**: collects every legal value for the cell. |
| `for (int num = 1; num <= 9; num++)` | Checks digits 1 to 9. |
| `if (isValid(num, row, col)) list.add(num);` | Keeps only legal values. |
| `static boolean solve()` | Recursive backtracking. |
| `if (empty == null) return true;` | No empty cell, so the puzzle is solved. |
| `for (int num : candidates(row, col))` | Tries only legal values. |
| `board[row][col] = num;` | Places the candidate. |
| `if (solve()) return true;` | Keeps the value if the rest of the board solves. |
| `board[row][col] = 0;` | **Backtrack**. |
| `return false;` | No candidate works, so the caller backtracks. |
| `main(...)` | Prints the puzzle, solves it and prints the solution. |

## 15. Sample Output

### Python

```text
Sudoku Puzzle:
[5, 3, 0, 0, 7, 0, 0, 0, 0]
[6, 0, 0, 1, 9, 5, 0, 0, 0]
[0, 9, 8, 0, 0, 0, 0, 6, 0]
[8, 0, 0, 0, 6, 0, 0, 0, 3]
[4, 0, 0, 8, 0, 3, 0, 0, 1]
[7, 0, 0, 0, 2, 0, 0, 0, 6]
[0, 6, 0, 0, 0, 0, 2, 8, 0]
[0, 0, 0, 4, 1, 9, 0, 0, 5]
[0, 0, 0, 0, 8, 0, 0, 7, 9]

Solved Sudoku:
[5, 3, 4, 6, 7, 8, 9, 1, 2]
[6, 7, 2, 1, 9, 5, 3, 4, 8]
[1, 9, 8, 3, 4, 2, 5, 6, 7]
[8, 5, 9, 7, 6, 1, 4, 2, 3]
[4, 2, 6, 8, 5, 3, 7, 9, 1]
[7, 1, 3, 9, 2, 4, 8, 5, 6]
[9, 6, 1, 5, 3, 7, 2, 8, 4]
[2, 8, 7, 4, 1, 9, 6, 3, 5]
[3, 4, 5, 2, 8, 6, 1, 7, 9]
```

### Java

```text
Sudoku Puzzle:
[5, 3, 0, 0, 7, 0, 0, 0, 0]
[6, 0, 0, 1, 9, 5, 0, 0, 0]
[0, 9, 8, 0, 0, 0, 0, 6, 0]
[8, 0, 0, 0, 6, 0, 0, 0, 3]
[4, 0, 0, 8, 0, 3, 0, 0, 1]
[7, 0, 0, 0, 2, 0, 0, 0, 6]
[0, 6, 0, 0, 0, 0, 2, 8, 0]
[0, 0, 0, 4, 1, 9, 0, 0, 5]
[0, 0, 0, 0, 8, 0, 0, 7, 9]

Solved Sudoku:
[5, 3, 4, 6, 7, 8, 9, 1, 2]
[6, 7, 2, 1, 9, 5, 3, 4, 8]
[1, 9, 8, 3, 4, 2, 5, 6, 7]
[8, 5, 9, 7, 6, 1, 4, 2, 3]
[4, 2, 6, 8, 5, 3, 7, 9, 1]
[7, 1, 3, 9, 2, 4, 8, 5, 6]
[9, 6, 1, 5, 3, 7, 2, 8, 4]
[2, 8, 7, 4, 1, 9, 6, 3, 5]
[3, 4, 5, 2, 8, 6, 1, 7, 9]
```
