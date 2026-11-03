public class TowerOfHanoi {
    static int moves = 0;

    static void hanoi(int n, char source, char helper, char target) {
        if (n == 1) {
            moves++;
            System.out.println("Move " + moves + ": disk 1 from " + source + " to " + target);
            return;
        }
        hanoi(n - 1, source, target, helper);
        moves++;
        System.out.println("Move " + moves + ": disk " + n + " from " + source + " to " + target);
        hanoi(n - 1, helper, source, target);
    }

    public static void main(String[] args) {
        int n = 3;
        System.out.println("Tower of Hanoi with " + n + " disks");
        hanoi(n, 'A', 'B', 'C');
        System.out.println("Total moves: " + moves
                + " | Formula 2^n-1 = " + ((int) Math.pow(2, n) - 1));
    }
}
