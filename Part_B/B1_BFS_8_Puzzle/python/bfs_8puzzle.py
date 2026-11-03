# B1 - BFS for the 8-Puzzle

from collections import deque

GOAL = (1, 2, 3, 4, 5, 6, 7, 8, 0)
START = (1, 3, 6, 5, 0, 2, 4, 7, 8)      # 0 is the blank


def neighbours(state):
    """All states reachable by moving one tile into the blank."""
    blank = state.index(0)
    r, c = divmod(blank, 3)
    for dr, dc in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
        nr, nc = r + dr, c + dc
        if 0 <= nr < 3 and 0 <= nc < 3:
            i = nr * 3 + nc
            s = list(state)
            s[blank], s[i] = s[i], s[blank]
            yield tuple(s)


def bfs(start):
    queue = deque([start])
    parent = {start: None}       # state -> state it came from
    explored = 0

    while queue:
        state = queue.popleft()  # FIFO = breadth first
        explored += 1
        if state == GOAL:
            path = []
            while state is not None:
                path.append(state)
                state = parent[state]
            return path[::-1], explored

        for nxt in neighbours(state):
            if nxt not in parent:
                parent[nxt] = state
                queue.append(nxt)
    return None, explored


def show(state):
    for i in range(0, 9, 3):
        print(state[i], state[i + 1], state[i + 2])


path, explored = bfs(START)
print("Moves:", len(path) - 1, "| States explored:", explored)
for step, state in enumerate(path):
    print("\nStep", step)
    show(state)
