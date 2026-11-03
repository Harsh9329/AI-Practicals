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
