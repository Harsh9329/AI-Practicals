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

Backtracking search was implemented for the 8-Queens problem. The program correctly found valid queen placements satisfying all row, column and diagonal constraints, and was extended to enumerate all 92 solutions, confirming the correctness and effectiveness of constraint-based backtracking.

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
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_B\B3_8_Queens\python"
python eight_queens.py
```

### Java

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_B\B3_8_Queens\java"
javac EightQueens.java
java EightQueens
```

## 12. Python Code

```python
# B3 - 8-Queens using backtracking

N = 8
pos = [-1] * N          # pos[col] = row of the queen
count = 0


def safe(row, col):
    """Check row and diagonal conflicts with earlier columns."""
    for c in range(col):
        if pos[c] == row or abs(pos[c] - row) == col - c:
            return False
    return True


def solve(col):
    global count
    if col == N:                    # all queens placed
        count += 1
        if count == 1:
            show()
        return

    for row in range(N):
        if safe(row, col):
            pos[col] = row
            solve(col + 1)
            pos[col] = -1           # backtrack


def show():
    print("First solution:")
    for r in range(N):
        print(" ".join("Q" if pos[c] == r else "." for c in range(N)))


solve(0)
print("Total solutions:", count)
```

## 13. Java Code

```java
import java.util.Arrays;

public class EightQueens {
    static final int N = 8;
    static int[] pos = new int[N];   // pos[col] = row
    static int count = 0;

    static boolean safe(int row, int col) {
        for (int c = 0; c < col; c++)
            if (pos[c] == row || Math.abs(pos[c] - row) == col - c)
                return false;
        return true;
    }

    static void solve(int col) {
        if (col == N) {              // all queens placed
            count++;
            if (count == 1) show();
            return;
        }

        for (int row = 0; row < N; row++) {
            if (safe(row, col)) {
                pos[col] = row;
                solve(col + 1);
                pos[col] = -1;       // backtrack
            }
        }
    }

    static void show() {
        System.out.println("First solution:");
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++)
                System.out.print(pos[c] == r ? "Q " : ". ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Arrays.fill(pos, -1);
        solve(0);
        System.out.println("Total solutions: " + count);
    }
}
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `N = 8` | Board size and number of queens. |
| `pos = [-1] * N` | `pos[col]` stores the row of the queen in that column. |
| `count = 0` | Counts all valid solutions. |
| `def safe(row, col):` | Checks whether a queen can be placed at `(row, col)`. |
| `for c in range(col):` | Only earlier columns need to be checked (one queen per column). |
| `if pos[c] == row` | Same row → conflict. |
| `abs(pos[c] - row) == col - c` | Same diagonal → conflict (row distance = column distance). |
| `def solve(col):` | Recursive backtracking, column by column. |
| `if col == N:` | Base case: all 8 queens are placed. |
| `count += 1` / `show()` | Count the solution; print the first one. |
| `for row in range(N):` | Try every row in the current column. |
| `if safe(row, col):` | Place only if there is no conflict. |
| `pos[col] = row` / `solve(col + 1)` | Place the queen and recurse. |
| `pos[col] = -1` | **Backtrack**: remove the queen and try the next row. |
| `def show():` | Prints `Q` and `.` for the board. |

### Java code

| Code block | Explanation |
|---|---|
| `static final int N = 8;` | Board size. |
| `static int[] pos = new int[N];` | `pos[col]` = row of the queen. |
| `static boolean safe(int row, int col)` | Same safety check as Python. |
| `pos[c] == row` | Row conflict. |
| `Math.abs(pos[c] - row) == col - c` | Diagonal conflict. |
| `static void solve(int col)` | Recursive backtracking. |
| `if (col == N)` | All queens placed. |
| `count++; if (count == 1) show();` | Count and print the first solution. |
| `pos[col] = row; solve(col + 1);` | Place and recurse. |
| `pos[col] = -1;` | Backtrack. |
| `Arrays.fill(pos, -1);` | Initialise every column as empty. |
| `System.out.println("Total solutions: " + count);` | Prints 92. |

## 15. Sample Output

```text
First solution:
Q . . . . . . .
. . . . . . Q .
. . . . Q . . .
. . . . . . . Q
. Q . . . . . .
. . . Q . . . .
. . . . . Q . .
. . Q . . . . .
Total solutions: 92
```
