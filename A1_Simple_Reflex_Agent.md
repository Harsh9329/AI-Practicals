# Assignment A1 — Simple Reflex Agent for the Vacuum Cleaner World

> **Selected practical:** This is one of the bold / highlighted practicals in **Part A** of the manual. Mini-project is not included.

## 1. Aim

To design and implement a simple reflex agent for the Vacuum Cleaner World and analyse its rational behaviour in a given environment.

## 2. Prerequisite

Basic concepts of Artificial Intelligence, notion of an agent, environment, sensors and actuators; elementary Python/Java/C++ programming.

## 3. Objective

- To understand the concept of an intelligent agent and its interaction with an environment.
- To study the structure and working of a simple reflex agent.
- To design and implement a simple reflex agent for the Vacuum Cleaner world.
- To analyse the rational behaviour of the agent for a given performance measure.

## 4. Outcome

Students will be able to design a simple reflex agent, implement condition-action rules for the Vacuum Cleaner world, and evaluate whether the agent behaves rationally under a given performance measure.

## 5. Software and Hardware Requirements

Python 3.x / Java / C++ compiler, any text editor or IDE (VS Code / PyCharm / Eclipse), Windows/Linux OS, 4 GB RAM minimum.

## 6. Theory

### What is an Intelligent Agent?

An agent is anything that can perceive its environment through **sensors** and act upon that environment through **actuators**. Mathematically, an agent's behaviour can be described by an **agent function** that maps every possible percept sequence to an action. The **agent program** is the concrete implementation of this function running on the agent architecture (hardware/software).

### Structure of Agents

AI agents are broadly classified as:

1. Simple reflex agents
2. Model-based reflex agents
3. Goal-based agents
4. Utility-based agents
5. Learning agents

This assignment focuses on the simplest of these — the **Simple Reflex Agent**.

### Simple Reflex Agent

A simple reflex agent selects actions based only on the **current percept**, ignoring the rest of the percept history. It works on the principle of **condition-action rules** (also called situation-action rules), typically written as:

```text
IF condition THEN action
```

Such agents are fast and easy to implement, but they only work correctly if the environment is **fully observable**, and they can get stuck in infinite loops in partially observable environments.

### The Vacuum Cleaner World

The Vacuum Cleaner world is a classic toy problem used to illustrate agent design. It typically consists of two locations, **A** and **B**. Each location can be either **Clean** or **Dirty**.

The agent (vacuum cleaner) perceives its current location and whether that location is dirty, and it can perform one of four actions:

- **Suck**
- **Move Left**
- **Move Right**
- **NoOp** (do nothing)

### Condition-Action Rule Table

| Percept: [Location, Status] | Action |
|---|---|
| [A, Dirty] | Suck |
| [A, Clean] | Move Right |
| [B, Dirty] | Suck |
| [B, Clean] | Move Left |

### Performance Measure and Rationality

A **performance measure** evaluates a sequence of environment states — for example, `+1` point for every clean square at every time step, minus a penalty for every movement.

An agent is **rational** if, for every possible percept sequence, it selects an action expected to maximise its performance measure given the evidence provided by the percept sequence and whatever built-in knowledge it has.

The simple reflex agent above is rational for a fully observable, deterministic two-square world, but may behave irrationally (e.g., oscillate forever) if the world has more than two locations or is only partially observable.

## 7. Algorithm

1. Initialize the environment with random dirt status at each location.
2. Repeat for a fixed number of steps or until both locations are clean:
   1. Sense current location and its status.
   2. Apply the condition-action rule table to select an action.
   3. Execute the action and update the environment.
   4. Update the performance measure.
3. Report the final environment state and total performance score.

## 8. Diagram

The manual shows the agent-environment interaction. In simple words:

```text
Environment --(percept)--> Sensors --> Agent program --> Actuators --(action)--> Environment
                                      (condition-action rules)
```

The agent senses `[location, status]`, applies a rule, and performs an action. The action changes the environment, which produces the next percept.

## 9. Conclusion

The simple reflex agent for the Vacuum Cleaner world was designed and implemented successfully using condition-action rules. The agent's behaviour was traced for different initial configurations and found to be rational for the given fully observable two-square environment, cleaning both squares in a bounded number of steps.

## 10. Viva Voce Questions

1. What is a rational agent? Give the general formula for rationality.
2. Differentiate between a simple reflex agent and a model-based reflex agent.
3. Why can a simple reflex agent fail in a partially observable environment?
4. What are the components of a task environment? (Use the PEAS description.)
5. Give the PEAS description for the Vacuum Cleaner world.
6. What is the difference between an agent function and an agent program?
7. Can a simple reflex agent have internal state? Justify.
8. What happens if the vacuum world has more than two rooms — how would you extend the rule table?

## 11. How to Run

### Python

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_A\A1_Vacuum_Reflex\python"
python vacuum_reflex.py
```

### Java

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_A\A1_Vacuum_Reflex\java"
javac VacuumReflex.java
java VacuumReflex
```

## 12. Python Code

```python
# A1 - Simple Reflex Agent: Vacuum Cleaner World

def agent(location, status):
    if status == "Dirty":
        return "Suck"
    if location == "A":
        return "Right"
    return "Left"


rooms = {"A": "Dirty", "B": "Dirty"}   # environment
location = "A"                          # start room
score = 0

for step in range(1, 7):                # at most 6 steps
    status = rooms[location]            # SENSE
    action = agent(location, status)    # DECIDE
    print("Step", step, "|", location, status, "->", action)

    if action == "Suck":                # ACT
        rooms[location] = "Clean"
        score += 10
    else:
        location = "B" if action == "Right" else "A"
        score -= 1

    if rooms["A"] == rooms["B"] == "Clean":
        break

print("Final rooms:", rooms)
print("Score:", score)
```

## 13. Java Code

```java
public class VacuumReflex {

    static String agent(String location, String status) {
        if (status.equals("Dirty")) return "Suck";
        if (location.equals("A")) return "Right";
        return "Left";
    }

    public static void main(String[] args) {
        String[] rooms = {"Dirty", "Dirty"};   // index 0 = A, 1 = B
        int location = 0;
        int score = 0;

        for (int step = 1; step <= 7; step++) {
            String status = rooms[location];
            String action = agent(location == 0 ? "A" : "B", status);
            System.out.println("Step " + step + " | "
                    + (location == 0 ? "A" : "B") + " " + status
                    + " -> " + action);

            if (action.equals("Suck")) {
                rooms[location] = "Clean";
                score += 10;
            } else {
                location = action.equals("Right") ? 1 : 0;
                score--;
            }

            if (rooms[0].equals("Clean") && rooms[1].equals("Clean")) break;
        }

        System.out.println("Final rooms: A=" + rooms[0] + ", B=" + rooms[1]);
        System.out.println("Score: " + score);
    }
}
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `def agent(location, status):` | The **agent program**. It receives only the current percept. |
| `if status == "Dirty": return "Suck"` | One rule covers both rooms: a dirty room is always sucked. |
| `if location == "A": return "Right"` | Clean room A → move to room B. |
| `return "Left"` | Clean room B → move back to room A. |
| `rooms = {"A": "Dirty", "B": "Dirty"}` | The environment. Change these values to test other cases. |
| `location = "A"` | The agent starts in room A. |
| `score = 0` | Performance measure. |
| `for step in range(1, 7):` | Time steps. The limit of 6 prevents an infinite loop. |
| `status = rooms[location]` | **SENSE** the current room. |
| `action = agent(location, status)` | **DECIDE** using the condition-action rules. |
| `if action == "Suck":` | **ACT**: clean the room and add 10 points. |
| `else:` | Otherwise the action is a move: −1 point. |
| `location = "B" if action == "Right" else "A"` | Update the agent's position after moving. |
| `if rooms["A"] == rooms["B"] == "Clean": break` | Stop as soon as both rooms are clean. |
| `print(...)` | Prints the trace, final rooms and score for the viva. |

### Java code

| Code block | Explanation |
|---|---|
| `static String agent(String location, String status)` | Java agent program; same three rules. |
| `if (status.equals("Dirty")) return "Suck";` | Dirty room → Suck. |
| `if (location.equals("A")) return "Right";` | Clean A → Right. |
| `return "Left";` | Clean B → Left. |
| `String[] rooms = {"Dirty", "Dirty"};` | Environment array: index 0 = A, index 1 = B. |
| `int location = 0;` | Agent starts in A. |
| `int score = 0;` | Performance measure. |
| `for (int step = 1; step <= 7; step++)` | Bounded time-step loop. |
| `String action = agent(location == 0 ? "A" : "B", status);` | Converts the index to a room name, then decides. |
| `if (action.equals("Suck"))` | Act: clean and score. |
| `location = action.equals("Right") ? 1 : 0;` | Act: move to the other room. |
| `score--;` | Movement penalty. |
| `if (rooms[0].equals("Clean") && rooms[1].equals("Clean")) break;` | Stop when both rooms are clean. |
| `System.out.println(...)` | Prints the trace, final state and score. |

## 15. Sample Output

```text
Step 1 | A Dirty -> Suck
Step 2 | A Clean -> Right
Step 3 | B Dirty -> Suck
Final rooms: {'A': 'Clean', 'B': 'Clean'}
Score: 19
```
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
