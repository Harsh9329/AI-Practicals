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
