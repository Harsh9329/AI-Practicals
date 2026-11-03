# A5 - Tower of Hanoi using recursion (state space search)

moves = 0


def hanoi(n, source, helper, target):
    global moves
    if n == 1:                              # base case
        moves += 1
        print("Move", moves, ": disk 1 from", source, "to", target)
        return

    hanoi(n - 1, source, target, helper)    # move n-1 disks aside
    moves += 1
    print("Move", moves, ": disk", n, "from", source, "to", target)
    hanoi(n - 1, helper, source, target)    # move them back on top


n = 3
print("Tower of Hanoi with", n, "disks")
hanoi(n, "A", "B", "C")
print("Total moves:", moves, "| Formula 2^n-1 =", 2 ** n - 1)
