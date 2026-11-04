# BFS to solve a planning problem (8-Puzzle)

from collections import deque

GOAL = [[1, 2, 3],
        [4, 5, 6],
        [7, 8, 0]]

START = [[1, 3, 6],
         [5, 0, 2],
         [4, 7, 8]]


def find_blank(state):
    for i in range(3):
        for j in range(3):
            if state[i][j] == 0:
                return i, j


def state_to_tuple(state):
    return tuple(tuple(row) for row in state)


def get_neighbors(state):
    neighbors = []
    x, y = find_blank(state)
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]

    for dx, dy in directions:
        nx, ny = x + dx, y + dy

        if 0 <= nx < 3 and 0 <= ny < 3:
            new_state = [row[:] for row in state]
            new_state[x][y], new_state[nx][ny] = new_state[nx][ny], new_state[x][y]
            neighbors.append(new_state)

    return neighbors


def bfs(start):
    queue = deque([(start, [])])
    visited = {state_to_tuple(start)}

    while queue:
        current_state, path = queue.popleft()

        if current_state == GOAL:
            return path + [current_state]

        for neighbor in get_neighbors(current_state):
            state_tuple = state_to_tuple(neighbor)

            if state_tuple not in visited:
                visited.add(state_tuple)
                queue.append((neighbor, path + [current_state]))

    return None


def print_solution(solution):
    if solution:
        print("\nSolution found in", len(solution) - 1, "moves:\n")

        for step, state in enumerate(solution):
            print("Step", step)
            for row in state:
                print(row)
            print()
    else:
        print("No solution found.")


solution = bfs(START)
print_solution(solution)
