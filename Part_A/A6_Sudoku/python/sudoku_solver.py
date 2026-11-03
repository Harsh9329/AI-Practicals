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
