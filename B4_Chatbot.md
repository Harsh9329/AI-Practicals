# Assignment B4 — Simple Rule-Based Chatbot using Pattern Matching

> **Selected practical:** This is one of the bold / highlighted practicals in **Part B** of the manual. Mini-project is not included.

## 1. Aim

To develop a simple chatbot using predefined rules and pattern-matching techniques.

## 2. Prerequisite

Basics of string processing/regular expressions, dictionaries/hash maps, elementary NLP concepts.

## 3. Objective

- To understand the architecture of a rule-based conversational agent (chatbot).
- To study pattern matching techniques used to map user input to appropriate responses.
- To design and implement a simple chatbot using a predefined set of pattern-response rules.
- To analyse the limitations of rule-based chatbots compared to modern AI-based conversational systems.

## 4. Outcome

Students will be able to design a rule base of patterns and responses and implement a chatbot that processes natural-language user input, matches it against these rules, and produces a coherent reply, including a fallback for unmatched input.

## 5. Software and Hardware Requirements

Python 3.x with `re` (regular expressions) module / Java / C++ compiler, Windows/Linux OS, 4 GB RAM minimum.

## 6. Theory

### What is a Chatbot?

A chatbot is a software application designed to simulate human conversation through text (or voice). Chatbots range from simple rule-based systems that match patterns in user input to sophisticated AI-based systems using large language models. This assignment focuses on the simplest category: a **rule-based, pattern-matching chatbot**, historically exemplified by Joseph Weizenbaum's **ELIZA** (1966).

### Rule-Based Pattern Matching

A rule-based chatbot maintains a knowledge base of **pattern-response pairs**. Each pattern is typically expressed using keywords or regular expressions that can match a family of related user inputs. For example, a pattern for `I am *` can capture `I am tired`, `I am happy`, etc., and reflect the captured phrase back in the response, e.g., `Why are you *?`.

### Processing Pipeline

1. **Pre-processing:** convert the user input to lowercase and remove leading/trailing spaces with `strip()`.
2. **Pattern matching:** compare the input against the rule base using `re.search()`, trying the rules in a fixed priority order (the first match wins).
3. **Response generation:** when a rule matches, return its fixed response string.
4. **Fallback:** if no rule matches, return "Sorry, I don't understand that." so the conversation does not break down.

### Rule Base Used in the Program

| Pattern (regex) | Response |
|---|---|
| `\b(hi\|hello\|hey)\b` | Hello! How are you? |
| `how are you` | I'm fine, thank you! |
| `(your name\|who are you)` | I am a simple Rule-Based Chatbot. |
| `what can you do` | I can answer basic questions using predefined rules. |
| `(thank you\|thanks)` | You're welcome! |
| `help` | Try asking me about my name, greetings, or how I am. |
| exact input `bye` | Goodbye! Have a great day. |
| (default / no match) | Sorry, I don't understand that. |

### Limitations of Rule-Based Chatbots

Rule-based chatbots can only respond well to inputs anticipated by their designer; they do not truly "understand" language, cannot handle context across multiple turns beyond simple slot-filling, and their quality depends entirely on the comprehensiveness of the hand-written rule base. Modern chatbots instead use statistical/neural NLP models (e.g., transformer-based large language models) trained on large corpora, allowing them to generalise to unseen inputs far better than fixed pattern-matching.

## 7. Algorithm Steps

1. Load the predefined pattern-response rule base (or use the built-in `DEMO` list in demo mode).
2. Loop: read user input.
3. Pre-process the input: convert to lowercase and trim spaces.
4. Try to match the input against each rule's pattern in priority order.
5. If matched, display that rule's response.
6. If not matched, display the fallback response.
7. Exit the loop when the user enters `bye`.

## 8. Diagram

The manual shows the chatbot processing pipeline. In simple words:

```text
User input
   |
   v
Pre-processing (lowercase, trim spaces)
   |
   v
Pattern matching against rule base
   |
   +---- match found ----> Return fixed response ----> Reply
   |
   +---- no match -------> Fallback reply
```

The first matching rule wins, so more specific rules should be placed before general ones.

## 9. Conclusion

A rule-based chatbot was implemented using a predefined set of pattern-response rules and regular-expression matching. The chatbot correctly handled greetings, simple statements, and farewells, and gracefully used a fallback response for unrecognised input, illustrating both the utility and the limitations of pure pattern-matching conversational agents.

## 10. Viva Voce Questions

1. What is the fundamental technique used by a rule-based chatbot to generate responses?
2. How does ELIZA's "reflection" technique work?
3. What are the main limitations of rule-based chatbots compared to AI/ML-based chatbots?
4. Why is a fallback response important in a chatbot's design?
5. How would you use regular expressions to capture and reuse part of the user's input?
6. What is the difference between rule-based and retrieval-based versus generative chatbots?
7. How could you extend this chatbot to maintain context across multiple turns?
8. What NLP pre-processing steps improve pattern-matching accuracy?

## 11. How to Run

### Python

```bash
cd Part_B/B4_Chatbot/python
python chatbot.py --demo
```

For interactive mode:

```bash
python chatbot.py
```

### Java

```bash
cd Part_B/B4_Chatbot/java
javac Chatbot.java
java Chatbot --demo
```

For interactive mode:

```bash
java Chatbot
```

## 12. Python Code

```python
# Simple chatbot using predefined rules and pattern matching

import re
import sys


def respond(user_input):
    user_input = user_input.lower().strip()

    if re.search(r"\b(hi|hello|hey)\b", user_input):
        return "Hello! How are you?"

    elif re.search(r"how are you", user_input):
        return "I'm fine, thank you!"

    elif re.search(r"(your name|who are you)", user_input):
        return "I am a simple Rule-Based Chatbot."

    elif re.search(r"what can you do", user_input):
        return "I can answer basic questions using predefined rules."

    elif re.search(r"(thank you|thanks)", user_input):
        return "You're welcome!"

    elif re.search(r"help", user_input):
        return "Try asking me about my name, greetings, or how I am."

    elif user_input == "bye":
        return "Goodbye! Have a great day."

    else:
        return "Sorry, I don't understand that."


DEMO = ["hello", "how are you", "who are you",
        "what can you do", "thanks", "help", "bye"]

if "--demo" in sys.argv:
    for line in DEMO:
        print("You:", line)
        print("Bot:", respond(line))
else:
    print("=== Simple Chatbot ===")
    print("Type 'bye' to exit.\n")

    while True:
        user_input = input("You: ")
        print("Bot:", respond(user_input))

        if user_input.lower().strip() == "bye":
            break
```

## 13. Java Code

```java
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
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `import re` | Regular expressions for pattern matching. |
| `import sys` | Used to read the `--demo` command-line argument. |
| `def respond(user_input):` | One function holds the complete rule base. |
| `user_input = user_input.lower().strip()` | Normalises the input: lowercase and remove extra spaces. |
| `if re.search(r"\b(hi\|hello\|hey)\b", user_input):` | Greeting rule; `\b` matches whole words, so "this" does not match "hi". |
| `return "Hello! How are you?"` | Reply for a greeting. |
| `elif re.search(r"how are you", ...)` | How-are-you rule. |
| `elif re.search(r"(your name\|who are you)", ...)` | Name rule. |
| `elif re.search(r"what can you do", ...)` | Capability rule. |
| `elif re.search(r"(thank you\|thanks)", ...)` | Thanks rule. |
| `elif re.search(r"help", ...)` | Help rule. |
| `elif user_input == "bye":` | Exact exit phrase. |
| `else: return "Sorry, I don't understand that."` | **Fallback** when no rule matches. |
| `DEMO = [...]` | Fixed inputs used for a repeatable demonstration. |
| `if "--demo" in sys.argv:` | Runs the demo without typing. |
| `for line in DEMO: print(...)` | Shows the question and the bot's reply. |
| `else:` | Interactive mode. |
| `while True:` / `input(...)` / `print(...)` | Chat loop. |
| `if user_input.lower().strip() == "bye": break` | Exits after the farewell. |

### Java code

| Code block | Explanation |
|---|---|
| `static String respond(String userInput)` | One method holds the complete rule base. |
| `userInput = userInput.toLowerCase().trim();` | Normalises the input. |
| `Pattern.compile("\\b(hi\|hello\|hey)\\b").matcher(userInput).find()` | Greeting rule; `\b` matches whole words only. |
| `return "Hello! How are you?";` | Reply for a greeting. |
| `Pattern.compile("how are you")...` | How-are-you rule. |
| `Pattern.compile("(your name\|who are you)")...` | Name rule. |
| `Pattern.compile("what can you do")...` | Capability rule. |
| `Pattern.compile("(thank you\|thanks)")...` | Thanks rule. |
| `Pattern.compile("help")...` | Help rule. |
| `else if (userInput.equals("bye"))` | Exact exit phrase. |
| `else { return "Sorry, I don't understand that."; }` | **Fallback**. |
| `String[] demo = {...};` | Fixed inputs for demonstration. |
| `for (String arg : args)` | Looks for the `--demo` argument. |
| `if (demoMode)` | Runs the fixed conversation. |
| `Scanner sc = new Scanner(System.in);` | Interactive mode. |
| `while (true)` / `sc.nextLine()` / `respond(...)` | Chat loop. |
| `if (userInput.toLowerCase().trim().equals("bye")) break;` | Exits after the farewell. |

## 15. Sample Output

### Python

```text
You: hello
Bot: Hello! How are you?
You: how are you
Bot: I'm fine, thank you!
You: who are you
Bot: I am a simple Rule-Based Chatbot.
You: what can you do
Bot: I can answer basic questions using predefined rules.
You: thanks
Bot: You're welcome!
You: help
Bot: Try asking me about my name, greetings, or how I am.
You: bye
Bot: Goodbye! Have a great day.
```

### Java

```text
You: hello
Bot: Hello! How are you?
You: how are you
Bot: I'm fine, thank you!
You: who are you
Bot: I am a simple Rule-Based Chatbot.
You: what can you do
Bot: I can answer basic questions using predefined rules.
You: thanks
Bot: You're welcome!
You: help
Bot: Try asking me about my name, greetings, or how I am.
You: bye
Bot: Goodbye! Have a great day.
```
