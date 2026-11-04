import java.util.Scanner;
import java.util.regex.Pattern;

public class Chatbot {

    static String respond(String userInput) {
        userInput = userInput.toLowerCase().trim();

        if (Pattern.compile("\\b(hi|hello|hey)\\b").matcher(userInput).find()) {
            return "Hello! How are you?";

        } else if (Pattern.compile("how are you").matcher(userInput).find()) {
            return "I'm fine, thank you!";

        } else if (Pattern.compile("(your name|who are you)").matcher(userInput).find()) {
            return "I am a simple Rule-Based Chatbot.";

        } else if (Pattern.compile("what can you do").matcher(userInput).find()) {
            return "I can answer basic questions using predefined rules.";

        } else if (Pattern.compile("(thank you|thanks)").matcher(userInput).find()) {
            return "You're welcome!";

        } else if (Pattern.compile("help").matcher(userInput).find()) {
            return "Try asking me about my name, greetings, or how I am.";

        } else if (userInput.equals("bye")) {
            return "Goodbye! Have a great day.";

        } else {
            return "Sorry, I don't understand that.";
        }
    }

    public static void main(String[] args) {
        String[] demo = {
            "hello", "how are you", "who are you",
            "what can you do", "thanks", "help", "bye"
        };

        boolean demoMode = false;
        for (String arg : args) {
            if (arg.equals("--demo")) {
                demoMode = true;
            }
        }

        if (demoMode) {
            for (String line : demo) {
                System.out.println("You: " + line);
                System.out.println("Bot: " + respond(line));
            }

        } else {
            System.out.println("=== Simple Chatbot ===");
            System.out.println("Type 'bye' to exit.\n");

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.print("You: ");
                String userInput = sc.nextLine();
                System.out.println("Bot: " + respond(userInput));

                if (userInput.toLowerCase().trim().equals("bye")) {
                    break;
                }
            }
        }
    }
}
