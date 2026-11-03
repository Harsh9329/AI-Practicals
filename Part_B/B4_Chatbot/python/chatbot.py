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
