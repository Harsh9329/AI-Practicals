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
