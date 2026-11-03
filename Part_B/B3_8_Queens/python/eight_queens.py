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
