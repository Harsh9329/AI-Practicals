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
