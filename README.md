# AI Practicals — Simple Code + Full Lab Manual Notes

**Location:** `C:\Users\harsh\TE SM\AI-Practicals`

This folder contains only the practicals that are **bold / highlighted** in the AI Lab Manual:

- **Part A (bold):** A1, A2, A5, A6
- **Part B (bold):** B1, B2, B3, B4
- **Mini-project (Part C):** not included, as requested.

Each practical has **one separate `.md` file** containing:

1. All manual sections for that practical — Aim, Prerequisite, Objective, Outcome, Software/Hardware, Theory, Algorithm, Diagram, Conclusion, Viva questions.
2. Python code.
3. Java code.
4. Line-by-line explanation for the examiner.
5. Sample output.
6. How to run commands.

All programs are written in a deliberately **short, commented style**: small functions, simple variable names and no unnecessary libraries, so the code is easy to explain and easy to modify during the viva.

---

## Practical Index

| Part | Practical | Markdown file (manual + code) | Python code | Java code |
|---|---|---|---|---|
| A | A1 — Simple Reflex Agent for Vacuum Cleaner World | [A1_Simple_Reflex_Agent.md](A1_Simple_Reflex_Agent.md) | `Part_A/A1_Vacuum_Reflex/python/vacuum_reflex.py` | `Part_A/A1_Vacuum_Reflex/java/VacuumReflex.java` |
| A | A2 — Model-Based Agent for Grid Navigation | [A2_Model_Based_Agent.md](A2_Model_Based_Agent.md) | `Part_A/A2_Model_Based_Grid/python/model_based_agent.py` | `Part_A/A2_Model_Based_Grid/java/ModelBasedAgent.java` |
| A | A5 — Tower of Hanoi using State Space Search | [A5_Tower_Of_Hanoi.md](A5_Tower_Of_Hanoi.md) | `Part_A/A5_Tower_Of_Hanoi/python/tower_of_hanoi.py` | `Part_A/A5_Tower_Of_Hanoi/java/TowerOfHanoi.java` |
| A | A6 — Sudoku Solver using Backtracking + Constraint Propagation | [A6_Sudoku_Solver.md](A6_Sudoku_Solver.md) | `Part_A/A6_Sudoku/python/sudoku_solver.py` | `Part_A/A6_Sudoku/java/SudokuSolver.java` |
| B | B1 — BFS for 8-Puzzle Planning Problem | [B1_BFS_8_Puzzle.md](B1_BFS_8_Puzzle.md) | `Part_B/B1_BFS_8_Puzzle/python/bfs_8puzzle.py` | `Part_B/B1_BFS_8_Puzzle/java/BFS8Puzzle.java` |
| B | B2 — DFS for Water Jug Problem | [B2_DFS_Water_Jug.md](B2_DFS_Water_Jug.md) | `Part_B/B2_DFS_Water_Jug/python/dfs_water_jug.py` | `Part_B/B2_DFS_Water_Jug/java/DFSWaterJug.java` |
| B | B3 — 8-Queens using Backtracking | [B3_8_Queens.md](B3_8_Queens.md) | `Part_B/B3_8_Queens/python/eight_queens.py` | `Part_B/B3_8_Queens/java/EightQueens.java` |
| B | B4 — Rule-Based Chatbot using Pattern Matching | [B4_Chatbot.md](B4_Chatbot.md) | `Part_B/B4_Chatbot/python/chatbot.py` | `Part_B/B4_Chatbot/java/Chatbot.java` |

---

## Folder Structure

```text
AI-Practicals/
│
├── README.md
├── A1_Simple_Reflex_Agent.md
├── A2_Model_Based_Agent.md
├── A5_Tower_Of_Hanoi.md
├── A6_Sudoku_Solver.md
├── B1_BFS_8_Puzzle.md
├── B2_DFS_Water_Jug.md
├── B3_8_Queens.md
├── B4_Chatbot.md
│
├── Part_A/
│   ├── A1_Vacuum_Reflex/
│   │   ├── python/vacuum_reflex.py
│   │   └── java/VacuumReflex.java
│   ├── A2_Model_Based_Grid/
│   │   ├── python/model_based_agent.py
│   │   └── java/ModelBasedAgent.java
│   ├── A5_Tower_Of_Hanoi/
│   │   ├── python/tower_of_hanoi.py
│   │   └── java/TowerOfHanoi.java
│   └── A6_Sudoku/
│       ├── python/sudoku_solver.py
│       └── java/SudokuSolver.java
│
└── Part_B/
    ├── B1_BFS_8_Puzzle/
    │   ├── python/bfs_8puzzle.py
    │   └── java/BFS8Puzzle.java
    ├── B2_DFS_Water_Jug/
    │   ├── python/dfs_water_jug.py
    │   └── java/DFSWaterJug.java
    ├── B3_8_Queens/
    │   ├── python/eight_queens.py
    │   └── java/EightQueens.java
    └── B4_Chatbot/
        ├── python/chatbot.py
        └── java/Chatbot.java
```

---

## How to Run

### Python

Open PowerShell in the practical's `python` folder and run:

```bash
python file_name.py
```

Example:

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_A\A1_Vacuum_Reflex\python"
python vacuum_reflex.py
```

### Java

Open PowerShell in the practical's `java` folder. The file name and public class name are the same.

```bash
javac FileName.java
java FileName
```

Example:

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_A\A1_Vacuum_Reflex\java"
javac VacuumReflex.java
java VacuumReflex
```

---

## Quick Explanation for the Examiner

Every `.md` file has a section **"Line-by-Line Explanation for Examiner"**. The easiest way to present is:

1. Read the **Aim** and **Theory** in 1–2 minutes.
2. Show the **Algorithm** steps.
3. Open the Python code and explain the block names from the table.
4. Run the program and show the **Sample Output**.
5. Mention the **time/space complexity** and one **limitation**.

### One-line summary of each practical

| Practical | One-line explanation |
|---|---|
| A1 | A simple reflex agent uses only the current percept and a table of IF–THEN rules. |
| A2 | A model-based agent remembers sensed cells in an internal map and plans on that map. |
| A5 | Tower of Hanoi is solved by recursion: move `n-1` disks aside, move the largest, move `n-1` back. |
| A6 | Sudoku is a CSP; backtracking tries values, and the candidate list removes illegal choices before recursion. |
| B1 | BFS uses a FIFO queue and finds the shortest 8-puzzle solution because every move costs 1. |
| B2 | DFS uses a LIFO stack, goes deep first, and backtracks at dead ends. |
| B3 | 8-Queens places one queen per column and backtracks when row/diagonal conflicts occur. |
| B4 | A rule-based chatbot matches regex patterns to responses and uses a fallback for unknown input. |
