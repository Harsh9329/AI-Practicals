# Assignment B3 — 8-Queens Problem using Backtracking Search (CSP)

> **Selected practical:** This is one of the bold / highlighted practicals in **Part B** of the manual. Mini-project is not included.

## 1. Aim

To implement a backtracking search algorithm to solve the 8-Queens Constraint Satisfaction Problem.

## 2. Prerequisite

Concept of Constraint Satisfaction Problems, recursion, 2-D board representation.

## 3. Objective

- To understand the N-Queens problem as a Constraint Satisfaction Problem (CSP).
- To study the backtracking search technique for solving CSPs.
- To implement backtracking search to solve the 8-Queens problem.
- To analyse pruning strategies that improve backtracking efficiency for N-Queens.

## 4. Outcome

Students will be able to formulate the 8-Queens problem as a CSP and implement backtracking search to find one or all valid solutions where no two queens attack each other.

## 5. Software and Hardware Requirements

Python 3.x / Java / C++ compiler, Windows/Linux OS, 4 GB RAM minimum.

## 6. Theory

### The 8-Queens Problem

The 8-Queens problem asks: how can 8 queens be placed on an 8 × 8 chessboard so that no two queens attack each other? Two queens attack each other if they share the same **row**, **column**, or **diagonal**. The problem generalises to N-Queens on an N × N board.

### CSP Formulation

- **Variables:** one variable per column, `Q1 … Q8`, representing the row position of the queen placed in that column. (This formulation, by construction, already avoids column conflicts.)
- **Domain:** `{1, 2, …, 8}` — the possible row for each queen.
- **Constraints:** for every pair of columns `i != j`:
  - `Qi != Qj` (no same row)
  - `|Qi - Qj| != |i - j|` (no same diagonal)

### Backtracking Search

Backtracking places queens column by column. For each column, it tries every row in the domain and checks whether the placement is **safe** — consistent with all previously placed queens (no same row, no same diagonal). If a safe row is found, the queen is placed and the search proceeds to the next column. If no row in the current column is safe, the algorithm backtracks to the previous column and tries its next alternative row.

### Safety Check

A placement of a queen at `(row, col)` is safe with respect to already-placed queens if, for every previously placed queen at `(r, c)`:

- `row != r` (different row)
- `abs(row - r) != abs(col - c)` (not on the same diagonal)

Since queens are placed one per column, same-column conflicts are avoided automatically by construction.

### Number of Solutions

For `N = 8`, backtracking finds **92 distinct solutions** (12 of which are "fundamental" — the rest are rotations/reflections of these 12). Backtracking with the safety check dramatically reduces the search compared to a brute-force check of all `8^8` (over 16 million) row assignments.

## 7. Algorithm Steps

1. Start at column 0 with an empty board.
2. If all N queens have been placed (`column == N`), record/return the current solution.
3. Otherwise, for `row = 0` to `N-1`:
   1. If placing a queen at `(row, column)` is safe, place it.
   2. Recursively attempt to solve for `column + 1`.
   3. If the recursive call succeeds (a full solution was found) and only one solution is required, return success.
   4. Otherwise, remove the queen from `(row, column)` — backtrack — and try the next row.
4. If no row works for this column, return failure to the caller (backtrack further).

## 8. Diagram

The manual shows an 8 × 8 chessboard with queens placed so that no two attack each other. In simple words:

```text
Q . . . . . . .
. . . . . . Q .
. . . . Q . . .
. . . . . . . Q
. Q . . . . . .
. . . Q . . . .
. . . . . Q . .
. . Q . . . . .
```

`Q` is a queen and `.` is an empty square. Check any row, column, or diagonal — it contains at most one queen.

## 9. Conclusion

Backtracking search was implemented for the 8-Queens problem. The program placed one queen per column, checked row and diagonal conflicts before every placement, and backtracked whenever a partial board could not be completed. It correctly produced one valid arrangement of 8 queens on the 8 x 8 board.

## 10. Viva Voce Questions

1. Formulate the N-Queens problem as a CSP (variables, domains, constraints).
2. How many total solutions exist for the standard 8-Queens problem?
3. What check determines whether a queen placement is "safe"?
4. How does representing one queen per column avoid the need for a row/column check?
5. How does backtracking prune the search tree compared to generating all `N^N` placements?
6. What is the difference between backtracking and brute-force search?
7. How would you modify the algorithm to find all solutions instead of just one?
8. How does the MRV (Minimum Remaining Values) heuristic apply to N-Queens?

## 11. How to Run

### Python

```bash
cd Part_B/B3_8_Queens/python
python eight_queens.py
```

### Java

```bash
cd Part_B/B3_8_Queens/java
javac EightQueens.java
java EightQueens
```

## 12. Python Code

```python
# Backtracking search for the 8-Queens problem

N = 8


def print_board(board):
    for row in board:
        print(" ".join("Q" if cell else "." for cell in row))
    print()


def is_safe(board, row, col):
    # Check left side of current row
    for i in range(col):
        if board[row][i]:
            return False

    # Check upper-left diagonal
    i, j = row, col
    while i >= 0 and j >= 0:
        if board[i][j]:
            return False
        i -= 1
        j -= 1

    # Check lower-left diagonal
    i, j = row, col
    while i < N and j >= 0:
        if board[i][j]:
            return False
        i += 1
        j -= 1

    return True


def solve_queens(board, col):
    # All queens placed
    if col >= N:
        return True

    for row in range(N):
        if is_safe(board, row, col):
            # Place queen
            board[row][col] = 1

            # Recur for next column
            if solve_queens(board, col + 1):
                return True

            # Backtrack
            board[row][col] = 0

    return False


# Main program
board = [[0 for _ in range(N)] for _ in range(N)]

if solve_queens(board, 0):
    print("Solution Found:\n")
    print_board(board)
else:
    print("No Solution Exists")
```

## 13. Java Code

```java
public class EightQueens {

    static final int N = 8;
    static int[][] board = new int[N][N];

    static void printBoard() {
        for (int[] row : board) {
            for (int col = 0; col < N; col++) {
                System.out.print((col == 0 ? "" : " ") + (row[col] == 1 ? "Q" : "."));
            }
            System.out.println();
        }
        System.out.println();
    }

    static boolean isSafe(int row, int col) {
        // Check left side of current row
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }

        // Check upper-left diagonal
        int i = row, j = col;
        while (i >= 0 && j >= 0) {
            if (board[i][j] == 1) {
                return false;
            }
            i--;
            j--;
        }

        // Check lower-left diagonal
        i = row;
        j = col;
        while (i < N && j >= 0) {
            if (board[i][j] == 1) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    static boolean solveQueens(int col) {
        // All queens placed
        if (col >= N) {
            return true;
        }

        for (int row = 0; row < N; row++) {
            if (isSafe(row, col)) {
                // Place queen
                board[row][col] = 1;

                // Recur for next column
                if (solveQueens(col + 1)) {
                    return true;
                }

                // Backtrack
                board[row][col] = 0;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        if (solveQueens(0)) {
            System.out.println("Solution Found:\n");
            printBoard();
        } else {
            System.out.println("No Solution Exists");
        }
    }
}
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `N = 8` | Board size and number of queens. |
| `def print_board(board):` | Prints the board using `Q` for a queen and `.` for an empty cell. |
| `" ".join("Q" if cell else "." for cell in row)` | Builds one row as text. |
| `def is_safe(board, row, col):` | Checks whether a queen can be placed at `(row, col)`. |
| left row loop | Is there a queen in the same row to the left? |
| upper-left `while` loop | Is there a queen on the upper-left diagonal? |
| lower-left `while` loop | Is there a queen on the lower-left diagonal? |
| `return True` | No conflict, so the cell is safe. |
| `def solve_queens(board, col):` | Places queens column by column. |
| `if col >= N: return True` | **Base case**: all 8 queens are placed. |
| `for row in range(N):` | Tries every row in the current column. |
| `if is_safe(board, row, col):` | Only places a queen if there is no conflict. |
| `board[row][col] = 1` | Places the queen. |
| `if solve_queens(board, col + 1): return True` | Recurses; keeps the queen if the rest of the board solves. |
| `board[row][col] = 0` | **Backtrack**: removes the queen and tries the next row. |
| `return False` | No row in this column works, so the caller must backtrack. |
| `board = [[0 for _ in range(N)] for _ in range(N)]` | Creates an empty 8x8 board. |
| `if solve_queens(board, 0): print_board(board)` | Starts at column 0 and prints the solution. |

### Java code

| Code block | Explanation |
|---|---|
| `static final int N = 8;` | Board size and number of queens. |
| `static int[][] board = new int[N][N];` | Empty 8x8 board; `1` means a queen. |
| `static void printBoard()` | Prints `Q` and `.` for each cell. |
| `static boolean isSafe(int row, int col)` | Checks whether a queen can go at `(row, col)`. |
| left row loop | Same row to the left? |
| upper-left `while` loop | Upper-left diagonal? |
| lower-left `while` loop | Lower-left diagonal? |
| `return true;` | No conflict. |
| `static boolean solveQueens(int col)` | Places queens column by column. |
| `if (col >= N) return true;` | **Base case**: all queens placed. |
| `for (int row = 0; row < N; row++)` | Tries every row. |
| `if (isSafe(row, col))` | Checks for conflicts first. |
| `board[row][col] = 1;` | Places the queen. |
| `if (solveQueens(col + 1)) return true;` | Recurses and keeps the queen if the rest solves. |
| `board[row][col] = 0;` | **Backtrack**. |
| `return false;` | No row works in this column. |
| `main(...)` | Calls `solveQueens(0)` and prints the solution. |

## 15. Sample Output

### Python

```text
Solution Found:

Q . . . . . . .
. . . . . . Q .
. . . . Q . . .
. . . . . . . Q
. Q . . . . . .
. . . Q . . . .
. . . . . Q . .
. . Q . . . . .
```

### Java

```text
Solution Found:

Q . . . . . . .
. . . . . . Q .
. . . . Q . . .
. . . . . . . Q
. Q . . . . . .
. . . Q . . . .
. . . . . Q . .
. . Q . . . . .
```
