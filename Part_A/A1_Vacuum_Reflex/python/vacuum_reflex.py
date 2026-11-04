# Simple Reflex Agent for Vacuum Cleaner World

class VacuumCleanerAgent:
    def __init__(self):
        self.performance = 0

    def act(self, location, status):
        if status == "Dirty":
            return "Suck"
        elif location == "A":
            return "Right"
        else:
            return "Left"


# Environment
environment = {
    "A": "Dirty",
    "B": "Dirty"
}

agent = VacuumCleanerAgent()
location = "A"

print("Initial Environment:", environment)
print("Starting Location:", location)
print()

# Run for a fixed number of steps
for step in range(4):
    status = environment[location]
    action = agent.act(location, status)

    print(f"Step {step + 1}")
    print(f"Location: {location}")
    print(f"Status: {status}")
    print(f"Action: {action}")

    # Execute action
    if action == "Suck":
        environment[location] = "Clean"
        agent.performance += 10

    elif action == "Right":
        location = "B"

    elif action == "Left":
        location = "A"

    print("Environment:", environment)
    print()

print("Final Environment:", environment)
print("Performance Score:", agent.performance)
