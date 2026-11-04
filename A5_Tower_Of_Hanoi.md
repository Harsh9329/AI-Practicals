# Assignment A5 — Tower of Hanoi using State Space Search

> **Selected practical:** This is one of the bold / highlighted practicals in **Part A** of the manual. Mini-project is not included.

## 1. Aim

To solve the Tower of Hanoi problem using the State Space Search technique.

## 2. Prerequisite

Concepts of recursion, stacks, state-space representation of problems.

## 3. Objective

- To understand state-space representation of a classic combinatorial problem.
- To study recursive problem decomposition for the Tower of Hanoi puzzle.
- To implement a recursive state-space search solution for Tower of Hanoi with N disks.
- To analyse the time complexity and minimum number of moves required.

## 4. Outcome

Students will be able to represent the Tower of Hanoi problem as a state-space search problem, implement its recursive solution, and derive the minimum number of moves required for N disks.

## 5. Software and Hardware Requirements

Python 3.x / Java / C++ compiler, Windows/Linux OS, 4 GB RAM minimum.

## 6. Theory

### Problem Description

The Tower of Hanoi consists of **three pegs** (Source, Auxiliary, Destination) and **N disks** of different sizes stacked on the Source peg in decreasing order of size (largest at the bottom). The objective is to move the entire stack to the Destination peg, obeying the rules:

1. Only one disk may be moved at a time.
2. A disk can only be placed on top of a larger disk or on an empty peg.
3. Only the topmost disk of a peg can be moved.

### State-Space Formulation

- **State:** the distribution of all N disks across the three pegs (which peg each disk is currently on).
- **Initial State:** all N disks on the Source peg.
- **Goal State:** all N disks on the Destination peg, in the same relative order.
- **Actions:** move the topmost disk of one peg to the top of another peg, provided the move rules are respected.

The state space forms a graph that, for N disks, is isomorphic to the Sierpinski triangle, with exactly `3^N` states and a shortest solution path of length `2^N - 1`.

### Recursive Solution

The classic recursive strategy decomposes the problem: to move N disks from Source to Destination using Auxiliary:

1. Recursively move the top `N-1` disks from Source to Auxiliary, using Destination as the helper peg.
2. Move the remaining largest disk (disk N) directly from Source to Destination.
3. Recursively move the `N-1` disks from Auxiliary to Destination, using Source as the helper peg.

The base case is `N = 1`: simply move the single disk directly from Source to Destination.

### Minimum Number of Moves

Let `T(N)` be the minimum number of moves required for N disks. The recurrence is:

```text
T(N) = 2 * T(N-1) + 1, with T(1) = 1
```

Solving this recurrence gives:

```text
T(N) = 2^N - 1
```

For example, `T(3) = 7` and `T(10) = 1023`. This shows the problem has exponential time complexity in the number of disks.

### State-Space Search View

Although Tower of Hanoi is usually solved by direct recursion, it can equally be framed and solved as a state-space search problem: each state is a configuration of disks on pegs, actions are legal single-disk moves, and search (e.g., BFS or IDDFS over this state graph) will also discover the same optimal `2^N - 1` length path. However, direct recursion is far more efficient because it exploits the problem's self-similar structure instead of blind search.

## 7. Algorithm (Recursive Solution)

1. If `N == 1`, move disk 1 from Source to Destination and stop.
2. Otherwise, recursively move the top `N-1` disks from Source to Auxiliary, using Destination as helper.
3. Move disk N from Source to Destination.
4. Recursively move the `N-1` disks from Auxiliary to Destination, using Source as helper.

## 8. Diagram

The manual shows three pegs with disks of decreasing size. In simple words:

```text
Initial:                 Final:
  |                        |
  |                        |
 [1]                      [1]
[2]                      [2]
[3]                      [3]
 A     B     C            A     B     C
```

The smallest disk is on top. The largest disk is at the bottom and can never be placed on a smaller disk.

## 9. Conclusion

The Tower of Hanoi problem was solved using recursion and state-space decomposition. The program correctly generated the sequence of `2^N - 1` moves for N disks, showing that the recursive solution is optimal for the standard three-peg problem.

## 10. Viva Voce Questions

1. State the three rules of the Tower of Hanoi puzzle.
2. Derive the recurrence relation for the minimum number of moves.
3. Why is the Tower of Hanoi problem said to have exponential time complexity?
4. How can Tower of Hanoi be modelled as a state-space search problem?
5. What is the size of the complete state space for N disks?
6. Can Tower of Hanoi be solved iteratively? Briefly describe one iterative approach.
7. What real-world problems have a similar recursive structure to Tower of Hanoi?
8. What would change in the algorithm if 4 pegs were allowed instead of 3?

## 11. How to Run

### Python

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_A\A5_Tower_Of_Hanoi\python"
python tower_of_hanoi.py
```

When prompted, enter the number of disks (for example `3`).

### Java

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_A\A5_Tower_Of_Hanoi\java"
javac TowerOfHanoi.java
java TowerOfHanoi
```

## 12. Python Code

```python
# Tower of Hanoi using State Space Search

def tower_of_hanoi(n, source, auxiliary, destination):
    if n == 1:
        print(f"Move Disk 1 from {source} -> {destination}")
        return

    # Move n-1 disks from source to auxiliary
    tower_of_hanoi(n - 1, source, destination, auxiliary)

    # Move largest disk to destination
    print(f"Move Disk {n} from {source} -> {destination}")

    # Move n-1 disks from auxiliary to destination
    tower_of_hanoi(n - 1, auxiliary, source, destination)


# Main Program
n = int(input("Enter number of disks: "))

print("\nSteps to solve Tower of Hanoi:\n")
tower_of_hanoi(n, 'A', 'B', 'C')
```

## 13. Java Code

```java
import java.util.Scanner;

public class TowerOfHanoi {

    static void towerOfHanoi(int n, char source, char auxiliary, char destination) {
        if (n == 1) {
            System.out.println("Move Disk 1 from " + source + " -> " + destination);
            return;
        }

        // Move n-1 disks from source to auxiliary
        towerOfHanoi(n - 1, source, destination, auxiliary);

        // Move largest disk to destination
        System.out.println("Move Disk " + n + " from " + source + " -> " + destination);

        // Move n-1 disks from auxiliary to destination
        towerOfHanoi(n - 1, auxiliary, source, destination);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of disks: ");
        int n = sc.nextInt();

        System.out.println("\nSteps to solve Tower of Hanoi:\n");
        towerOfHanoi(n, 'A', 'B', 'C');
    }
}
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `def tower_of_hanoi(n, source, auxiliary, destination):` | Recursive function: move `n` disks from `source` to `destination` using `auxiliary`. |
| `if n == 1:` | **Base case**: only one disk is left. |
| `print(f"Move Disk 1 from {source} -> {destination}")` | A single disk can always be moved directly, so print the move. |
| `return` | Stop this branch of the recursion. |
| `tower_of_hanoi(n - 1, source, destination, auxiliary)` | **Step 1**: move the top `n-1` disks out of the way, onto the auxiliary peg. |
| `print(f"Move Disk {n} from {source} -> {destination}")` | **Step 2**: move the largest remaining disk to its final peg. |
| `tower_of_hanoi(n - 1, auxiliary, source, destination)` | **Step 3**: move the `n-1` disks from the auxiliary peg onto the largest disk. |
| `n = int(input("Enter number of disks: "))` | Reads the number of disks from the user (3 in the sample run). |
| `print("\nSteps to solve Tower of Hanoi:\n")` | Prints the heading. |
| `tower_of_hanoi(n, 'A', 'B', 'C')` | Starts the recursion: all disks on A, destination C, B is auxiliary. |

### Java code

| Code block | Explanation |
|---|---|
| `static void towerOfHanoi(int n, char source, char auxiliary, char destination)` | Recursive method: move `n` disks from source to destination using auxiliary. |
| `if (n == 1)` | **Base case**: one disk left. |
| `System.out.println("Move Disk 1 from ...")` | A single disk is moved directly, so print the move. |
| `return;` | Stop this branch of the recursion. |
| `towerOfHanoi(n - 1, source, destination, auxiliary);` | **Step 1**: move the top `n-1` disks onto the auxiliary peg. |
| `System.out.println("Move Disk " + n + " ...");` | **Step 2**: move the largest remaining disk to its final peg. |
| `towerOfHanoi(n - 1, auxiliary, source, destination);` | **Step 3**: move the `n-1` disks from auxiliary onto the largest disk. |
| `Scanner sc = new Scanner(System.in);` | Reads input from the keyboard. |
| `int n = sc.nextInt();` | Number of disks entered by the user (3 in the sample run). |
| `System.out.println("\nSteps to solve Tower of Hanoi:\n");` | Prints the heading. |
| `towerOfHanoi(n, 'A', 'B', 'C');` | Starts the recursion with all disks on A. |

## 15. Sample Output

### Python

```text
Enter number of disks: 3
Steps to solve Tower of Hanoi:

Move Disk 1 from A -> C
Move Disk 2 from A -> B
Move Disk 1 from C -> B
Move Disk 3 from A -> C
Move Disk 1 from B -> A
Move Disk 2 from B -> C
Move Disk 1 from A -> C
```

### Java

```text
Enter number of disks: 3
Steps to solve Tower of Hanoi:

Move Disk 1 from A -> C
Move Disk 2 from A -> B
Move Disk 1 from C -> B
Move Disk 3 from A -> C
Move Disk 1 from B -> A
Move Disk 2 from B -> C
Move Disk 1 from A -> C
```
