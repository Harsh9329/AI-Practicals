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

A **performance measure** evaluates a sequence of environment states. In this program the agent scores `+10` points for every room it sucks clean; moves carry no penalty.

An agent is **rational** if, for every possible percept sequence, it selects an action expected to maximise its performance measure given the evidence provided by the percept sequence and whatever built-in knowledge it has.

The simple reflex agent above is rational for a fully observable, deterministic two-square world, but may behave irrationally (e.g., oscillate forever) if the world has more than two locations or is only partially observable.

## 7. Algorithm

1. Initialize the environment with both rooms Dirty and place the agent in room A.
2. Repeat for exactly 4 time steps (a fixed count prevents an infinite loop):
   1. Sense the current location and its status.
   2. Apply the condition-action rule table to select an action.
   3. Execute the action and update the environment.
   4. Update the performance measure (`+10` for a successful Suck).
3. Report the final environment state and total performance score.

## 8. Diagram

The manual shows the agent-environment interaction. In simple words:

```text
Environment --(percept)--> Sensors --> Agent program --> Actuators --(action)--> Environment
                                      (condition-action rules)
```

The agent senses `[location, status]`, applies a rule, and performs an action. The action changes the environment, which produces the next percept.

## 9. Conclusion

The simple reflex agent for the Vacuum Cleaner world was designed and implemented successfully using condition-action rules. The agent's behaviour was traced for the given initial configuration and found to be rational for the fully observable two-square environment, cleaning both squares in a bounded number of steps.

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
cd Part_A/A1_Vacuum_Reflex/python
python vacuum_reflex.py
```

### Java

```bash
cd Part_A/A1_Vacuum_Reflex/java
javac VacuumReflex.java
java VacuumReflex
```

## 12. Python Code

```python
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
```

## 13. Java Code

```java
public class VacuumReflex {
    static int performance = 0;

    static String act(String location, String status) {
        if (status.equals("Dirty")) {
            return "Suck";
        } else if (location.equals("A")) {
            return "Right";
        } else {
            return "Left";
        }
    }

    public static void main(String[] args) {
        String[] environment = {"Dirty", "Dirty"};   // 0 = A, 1 = B
        int location = 0;

        System.out.println("Initial Environment: A=" + environment[0]
                + ", B=" + environment[1]);
        System.out.println("Starting Location: A");
        System.out.println();

        for (int step = 1; step <= 4; step++) {
            String status = environment[location];
            String action = act(location == 0 ? "A" : "B", status);

            System.out.println("Step " + step);
            System.out.println("Location: " + (location == 0 ? "A" : "B"));
            System.out.println("Status: " + status);
            System.out.println("Action: " + action);

            if (action.equals("Suck")) {
                environment[location] = "Clean";
                performance += 10;
            } else if (action.equals("Right")) {
                location = 1;
            } else if (action.equals("Left")) {
                location = 0;
            }

            System.out.println("Environment: A=" + environment[0]
                    + ", B=" + environment[1]);
            System.out.println();
        }

        System.out.println("Final Environment: A=" + environment[0]
                + ", B=" + environment[1]);
        System.out.println("Performance Score: " + performance);
    }
}
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `class VacuumCleanerAgent:` | Defines the agent as a class so its performance score stays inside the agent. |
| `def __init__(self):` | Constructor; runs once when the agent is created. |
| `self.performance = 0` | The performance measure starts at zero. |
| `def act(self, location, status):` | The **agent program**. It receives only the current percept `(location, status)`. |
| `if status == "Dirty":` | **Condition-action rule 1**: if the current room is dirty ... |
| `return "Suck"` | ... the action is Suck. |
| `elif location == "A":` | **Rule 2**: clean room and agent in A ... |
| `return "Right"` | ... move right to room B. |
| `else:` | Otherwise the agent is in B and the room is clean. |
| `return "Left"` | **Rule 3**: move left back to room A. |
| `environment = {` | The environment: a dictionary with two rooms. Change the values to test other cases. |
| `"A": "Dirty",` / `"B": "Dirty"` | Both rooms start dirty. |
| `agent = VacuumCleanerAgent()` | Creates the agent object. |
| `location = "A"` | The agent starts in room A. |
| `for step in range(4):` | Runs for exactly 4 time steps, so the program cannot loop forever. |
| `status = environment[location]` | **SENSE**: read the current room's status. |
| `action = agent.act(location, status)` | **DECIDE**: apply the condition-action rules. |
| `print(f"Step {step + 1}")` ... | Prints the percept and the chosen action for the trace. |
| `if action == "Suck":` | **ACT**: clean the current room ... |
| `environment[location] = "Clean"` | ... update the environment ... |
| `agent.performance += 10` | ... and add 10 points. |
| `elif action == "Right":` / `location = "B"` | **ACT**: move to room B. |
| `elif action == "Left":` / `location = "A"` | **ACT**: move to room A. |
| `print("Environment:", environment)` | Shows the environment after every action. |
| `print("Final Environment:", ...)` | Final state after 4 steps. |
| `print("Performance Score:", ...)` | Final score: 2 sucks x 10 = 20. |

### Java code

| Code block | Explanation |
|---|---|
| `static int performance = 0;` | The performance measure, stored as a class variable. |
| `static String act(String location, String status)` | The **agent program**; it receives only the current percept. |
| `if (status.equals("Dirty"))` | **Rule 1**: dirty room -> ... |
| `return "Suck";` | ... Suck. |
| `else if (location.equals("A"))` | **Rule 2**: clean room A -> ... |
| `return "Right";` | ... move right to B. |
| `else` / `return "Left";` | **Rule 3**: clean room B -> move left to A. |
| `String[] environment = {"Dirty", "Dirty"};` | The environment; index 0 = A, index 1 = B. |
| `int location = 0;` | The agent starts in room A (index 0). |
| `for (int step = 1; step <= 4; step++)` | Exactly 4 time steps, so the loop always terminates. |
| `String status = environment[location];` | **SENSE** the current room. |
| `String action = act(location == 0 ? "A" : "B", status);` | Converts the index to a room name, then **DECIDES**. |
| `if (action.equals("Suck"))` | **ACT**: clean the room ... |
| `environment[location] = "Clean";` / `performance += 10;` | ... and add 10 points. |
| `else if (action.equals("Right"))` / `location = 1;` | Move to room B. |
| `else if (action.equals("Left"))` / `location = 0;` | Move to room A. |
| `System.out.println("Environment: ...")` | Prints the trace after every action. |
| `System.out.println("Final Environment: ...")` | Final state after 4 steps. |
| `System.out.println("Performance Score: ...");` | Final score: 2 sucks x 10 = 20. |

## 15. Sample Output

### Python

```text
Initial Environment: {'A': 'Dirty', 'B': 'Dirty'}
Starting Location: A

Step 1
Location: A
Status: Dirty
Action: Suck
Environment: {'A': 'Clean', 'B': 'Dirty'}

Step 2
Location: A
Status: Clean
Action: Right
Environment: {'A': 'Clean', 'B': 'Dirty'}

Step 3
Location: B
Status: Dirty
Action: Suck
Environment: {'A': 'Clean', 'B': 'Clean'}

Step 4
Location: B
Status: Clean
Action: Left
Environment: {'A': 'Clean', 'B': 'Clean'}

Final Environment: {'A': 'Clean', 'B': 'Clean'}
Performance Score: 20
```

### Java

```text
Initial Environment: A=Dirty, B=Dirty
Starting Location: A

Step 1
Location: A
Status: Dirty
Action: Suck
Environment: A=Clean, B=Dirty

Step 2
Location: A
Status: Clean
Action: Right
Environment: A=Clean, B=Dirty

Step 3
Location: B
Status: Dirty
Action: Suck
Environment: A=Clean, B=Clean

Step 4
Location: B
Status: Clean
Action: Left
Environment: A=Clean, B=Clean

Final Environment: A=Clean, B=Clean
Performance Score: 20
```
