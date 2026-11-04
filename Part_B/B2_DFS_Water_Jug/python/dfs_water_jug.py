# Water Jug Problem using DFS

def dfs(jug1, jug2, target):
    stack = [((0, 0), [])]      # (state, path)
    visited = {(0, 0)}

    while stack:
        (x, y), path = stack.pop()

        # Check if target is reached
        if x == target or y == target:
            for action, state in path:
                print(action, "->", state)
            print("\nTarget achieved in", len(path), "steps!")
            return

        # Possible moves: (new state, action name)
        next_states = [
            ((jug1, y), "Fill Jug1"),
            ((x, jug2), "Fill Jug2"),
            ((0, y), "Empty Jug1"),
            ((x, 0), "Empty Jug2"),
            ((max(0, x - (jug2 - y)), min(jug2, y + x)), "Pour Jug1 -> Jug2"),
            ((min(jug1, x + y), max(0, y - (jug1 - x))), "Pour Jug2 -> Jug1")
        ]

        for state, action in next_states:
            if state not in visited:
                visited.add(state)
                stack.append((state, path + [(action, state)]))

    print("No solution found")


# Driver Code
jug1_capacity = 4
jug2_capacity = 3
target = 2

dfs(jug1_capacity, jug2_capacity, target)
