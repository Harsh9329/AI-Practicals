import java.util.*;
import java.util.regex.*;

public class Chatbot {
    static class Rule {
        Pattern pattern;
        String answer;

        Rule(String regex, String answer) {
            this.pattern = Pattern.compile(regex);
            this.answer = answer;
        }
    }

    static Rule[] rules = {
        new Rule("\\b(hi|hello|hey)\\b", "Hello! How can I help you?"),
        new Rule("my name is (\\w+)", "Nice to meet you, {0}!"),
        new Rule("\\b(bye|exit|quit)\\b", "Goodbye! Have a great day."),
        new Rule("what is ai", "AI is the simulation of human intelligence by machines.")
    };

    static String reply(String text) {
        text = text.toLowerCase().trim();
        for (Rule rule : rules) {
            Matcher m = rule.pattern.matcher(text);
            if (m.find()) {
                if (m.groupCount() > 0)
                    return rule.answer.replace("{0}", m.group(1));
                return rule.answer;
            }
        }
        return "Sorry, I don't understand.";
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--demo")) {
            String[] demo = {"Hello", "My name is Harsh", "What is AI?",
                             "Tell me a joke", "bye"};
            for (String line : demo) {
                System.out.println("You: " + line);
                System.out.println("Bot: " + reply(line));
            }
            return;
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("You: ");
            String line = sc.nextLine();
            System.out.println("Bot: " + reply(line));
            if (line.toLowerCase().matches(".*\\b(bye|exit|quit)\\b.*")) break;
        }
    }
}
