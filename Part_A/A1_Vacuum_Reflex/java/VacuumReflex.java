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
