# Model-Based Intelligent Agent for Grid Navigation

class ModelBasedAgent:
    def __init__(self, grid, start, goal):
        self.grid = grid
        self.position = start
        self.goal = goal

        # Internal model: '?' = not sensed yet, 'O' = free, 'X' = obstacle
        self.model = [['?' for _ in row] for row in grid]
        self.visited = {start}
        self.sense()

    def sense(self):
        x, y = self.position
        for dx, dy in [(0, 0), (-1, 0), (1, 0), (0, -1), (0, 1)]:
            nx, ny = x + dx, y + dy
            if 0 <= nx < len(self.grid) and 0 <= ny < len(self.grid[0]):
                self.model[nx][ny] = self.grid[nx][ny]

    def display_grid(self):
        for row in self.model:
            print(row)
        print()

    def move(self):
        x, y = self.position
        gx, gy = self.goal

        # First try moves that reduce the distance to the goal
        moves = []
        if x < gx:
            moves.append((x + 1, y))
        if y < gy:
            moves.append((x, y + 1))
        if x > gx:
            moves.append((x - 1, y))
        if y > gy:
            moves.append((x, y - 1))

        # Then try any other neighbour
        moves += [(x + 1, y), (x, y + 1), (x - 1, y), (x, y - 1)]

        for nx, ny in moves:
            if 0 <= nx < len(self.grid) and 0 <= ny < len(self.grid[0]):
                if self.model[nx][ny] != 'X' and (nx, ny) not in self.visited:
                    self.position = (nx, ny)
                    self.visited.add((nx, ny))
                    self.sense()
                    return True
        return False

    def run(self):
        step = 1
        while self.position != self.goal and step <= 50:
            print(f"Step {step}: Current Position = {self.position}")
            if not self.move():
                print("No path to the goal.")
                return
            step += 1

        print(f"\nGoal Reached at {self.position}")


# Grid Environment
# O = Free Cell
# X = Obstacle

grid = [
    ['O', 'O', 'O', 'O'],
    ['O', 'X', 'O', 'O'],
    ['O', 'X', 'O', 'O'],
    ['O', 'O', 'O', 'O']
]

start = (0, 0)
goal = (3, 3)

agent = ModelBasedAgent(grid, start, goal)

print("Agent's initial model ('?' = not sensed yet):")
agent.display_grid()

agent.run()
