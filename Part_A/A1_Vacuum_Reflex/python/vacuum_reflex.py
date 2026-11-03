# A1 - Simple Reflex Agent: Vacuum Cleaner World

def agent(location, status):
    if status == "Dirty":
        return "Suck"
    if location == "A":
        return "Right"
    return "Left"


rooms = {"A": "Dirty", "B": "Dirty"}   # environment
location = "A"                          # start room
score = 0

for step in range(1, 7):                # at most 6 steps
    status = rooms[location]            # SENSE
    action = agent(location, status)    # DECIDE
    print("Step", step, "|", location, status, "->", action)

    if action == "Suck":                # ACT
        rooms[location] = "Clean"
        score += 10
    else:
        location = "B" if action == "Right" else "A"
        score -= 1

    if rooms["A"] == rooms["B"] == "Clean":
        break

print("Final rooms:", rooms)
print("Score:", score)
