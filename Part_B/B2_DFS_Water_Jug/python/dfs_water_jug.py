# B2 - DFS for the Water Jug Problem

CAP_A, CAP_B = 4, 3
TARGET = 2


def moves(a, b):
    """The six legal actions from state (a, b)."""
    p = min(a, CAP_B - b)       # water poured A -> B
    q = min(b, CAP_A - a)       # water poured B -> A
    return [
        (CAP_A, b, "Fill A"),
        (a, CAP_B, "Fill B"),
        (0, b, "Empty A"),
        (a, 0, "Empty B"),
        (a - p, b + p, "Pour A to B"),
        (a + q, b - q, "Pour B to A"),
    ]


stack = [((0, 0), [])]          # (state, path so far)
visited = {(0, 0)}

while stack:
    (a, b), path = stack.pop()  # LIFO = depth first
    if a == TARGET or b == TARGET:
        for action, state in path:
            print(action, "->", state)
        print("Goal reached in", len(path), "steps")
        break

    for na, nb, action in moves(a, b):
        if (na, nb) not in visited:
            visited.add((na, nb))
            stack.append(((na, nb), path + [(action, (na, nb))]))
else:
    print("No solution found")
