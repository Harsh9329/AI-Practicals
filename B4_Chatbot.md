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

1. **Pre-processing:** convert user input to lowercase, strip punctuation, and tokenise into words.
2. **Pattern matching:** compare the pre-processed input (or its tokens) against the rule base, typically using regular expressions or keyword lookup, often trying rules in a priority order (more specific rules first).
3. **Response generation:** when a rule matches, generate the response, substituting any captured groups from the input (as ELIZA-style reflection) if applicable.
4. **Fallback:** if no rule matches, return a generic response (e.g., "Can you tell me more about that?") so the conversation does not break down.

### Example Rule Base (Illustrative)

| Pattern (keyword / regex) | Response template |
|---|---|
| `hello\|hi\|hey` | Hello! How can I help you today? |
| `my name is (.*)` | Nice to meet you, `\1`! |
| `i am (.*)` | Why do you think you are `\1`? |
| `bye\|quit\|exit` | Goodbye! Have a great day. |
| (default / no match) | I'm not sure I understand — could you rephrase that? |

### Limitations of Rule-Based Chatbots

Rule-based chatbots can only respond well to inputs anticipated by their designer; they do not truly "understand" language, cannot handle context across multiple turns beyond simple slot-filling, and their quality depends entirely on the comprehensiveness of the hand-written rule base. Modern chatbots instead use statistical/neural NLP models (e.g., transformer-based large language models) trained on large corpora, allowing them to generalise to unseen inputs far better than fixed pattern-matching.

## 7. Algorithm Steps

1. Load the predefined pattern-response rule base.
2. Loop: read user input.
3. Pre-process the input (lowercase, strip punctuation).
4. Try to match input against each rule's pattern in priority order.
5. If matched, format and display the response (substituting captured text if needed).
6. If not matched, display the fallback response.
7. Exit the loop when the user enters an exit keyword (e.g., "bye").

## 8. Diagram

The manual shows the chatbot processing pipeline. In simple words:

```text
User input
   |
   v
Pre-processing (lowercase, remove punctuation)
   |
   v
Pattern matching against rule base
   |
   +---- match found ----> Fill response template ----> Reply
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
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_B\B4_Chatbot\python"
python chatbot.py --demo
```

For interactive mode:

```bash
python chatbot.py
```

### Java

```bash
cd "C:\Users\harsh\TE SM\AI-Practicals\Part_B\B4_Chatbot\java"
javac Chatbot.java
java Chatbot --demo
```

For interactive mode:

```bash
java Chatbot
```

## 12. Python Code

```python
# B4 - Rule-based chatbot using pattern matching

import re
import sys

RULES = [
    (r"\b(hi|hello|hey)\b", "Hello! How can I help you?"),
    (r"my name is (\w+)", "Nice to meet you, {0}!"),
    (r"\b(bye|exit|quit)\b", "Goodbye! Have a great day."),
    (r"what is ai", "AI is the simulation of human intelligence by machines."),
]


def reply(text):
    text = text.lower().strip()
    for pattern, answer in RULES:
        match = re.search(pattern, text)
        if match:
            if match.groups():
                return answer.format(match.group(1))
            return answer
    return "Sorry, I don't understand."


DEMO = ["Hello", "My name is Harsh", "What is AI?", "Tell me a joke", "bye"]

if "--demo" in sys.argv:
    for line in DEMO:
        print("You:", line)
        print("Bot:", reply(line))
else:
    while True:
        line = input("You: ")
        print("Bot:", reply(line))
        if re.search(r"\b(bye|exit|quit)\b", line.lower()):
            break
```

## 13. Java Code

```java
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
```

## 14. Line-by-Line Explanation for Examiner

### Python code

| Code block | Explanation |
|---|---|
| `RULES = [...]` | The rule base: each item is a `(regex pattern, response)` pair. |
| `r"\b(hi\|hello\|hey)\b"` | Matches whole words only, so "this" does not match "hi". |
| `r"my name is (\w+)"` | `(\w+)` **captures** the name so it can be reused. |
| `{0}` | Placeholder replaced by the captured name. |
| `def reply(text):` | Main chatbot function. |
| `text = text.lower().strip()` | Pre-processing: lowercase and trim spaces. |
| `for pattern, answer in RULES:` | Tries each rule in priority order. |
| `match = re.search(pattern, text)` | Searches anywhere in the input. |
| `if match.groups():` | True when the pattern captured something. |
| `return answer.format(match.group(1))` | Fills `{0}` with the captured text. |
| `return answer` | No capture → fixed response. |
| `return "Sorry, I don't understand."` | **Fallback** when no rule matches. |
| `if "--demo" in sys.argv:` | Runs a fixed conversation for easy demonstration. |
| `else: while True: ...` | Interactive mode; exits on bye/exit/quit. |

### Java code

| Code block | Explanation |
|---|---|
| `static class Rule` | Stores a compiled `Pattern` and its response. |
| `Pattern.compile(regex)` | Compiles each regex once. |
| `static Rule[] rules = {...}` | Rule base, checked top to bottom. |
| `static String reply(String text)` | Main chatbot function. |
| `text = text.toLowerCase().trim();` | Pre-processing. |
| `Matcher m = rule.pattern.matcher(text);` | Creates a matcher for one rule. |
| `if (m.find())` | Rule matches anywhere in the input. |
| `if (m.groupCount() > 0)` | A capture group exists. |
| `rule.answer.replace("{0}", m.group(1))` | Inserts the captured text. |
| `return "Sorry, I don't understand.";` | Fallback. |
| `if (args[0].equals("--demo"))` | Demo mode with fixed inputs. |
| `Scanner sc = new Scanner(System.in);` | Interactive mode. |
| `line.toLowerCase().matches(...)` | Exit check for bye/exit/quit. |

## 15. Sample Output

```text
You: Hello
Bot: Hello! How can I help you?
You: My name is Harsh
Bot: Nice to meet you, harsh!
You: What is AI?
Bot: AI is the simulation of human intelligence by machines.
You: Tell me a joke
Bot: Sorry, I don't understand.
You: bye
Bot: Goodbye! Have a great day.
```
