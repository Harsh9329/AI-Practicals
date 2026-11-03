def simple_reflex_agent(location, status):
    """Decide one action using condition-action rules."""
    if status == "Dirty":          # [A,Dirty] -> Suck, [B,Dirty] -> Suck
        return "Suck"
    if location == "A":            # [A,Clean] -> Move Right
        return "Move Right"
    return "Move Left"             # [B,Clean] -> Move Left


def run_vacuum_world():
    # Environment state
    rooms = {"A": "Dirty", "B": "Dirty"}
    location = "A"                 # Agent starts in room A
    score = 0                      # Performance measure

    print("Initial rooms:", rooms)

    for step in range(1, 11):      # maximum 10 steps
        status = rooms[location]                         # SENSE
        action = simple_reflex_agent(location, status)   # DECIDE
        print("Step", step, "| location =", location,
              "| status =", status, "| action =", action)

        if action == "Suck":                             # ACT
            rooms[location] = "Clean"
            score += 10            # reward for cleaning a room
        else:
            if action == "Move Right":
                location = "B"
            else:
                location = "A"
            score -= 1             # small cost for moving

        # Performance measure: +1 for each clean room after every step
        if rooms["A"] == "Clean":
            score += 1
        if rooms["B"] == "Clean":
            score += 1

        print("   rooms =", rooms, "| score =", score)

        if rooms["A"] == "Clean" and rooms["B"] == "Clean":
            print("Both rooms are clean. Stopping.")
            break

    print("Final score:", score)


if __name__ == "__main__":
    run_vacuum_world()
