import random

# Function to generate match-ups for a given list of names
def generate_matchups(names):
    matchups = []
    num_players = len(names)
    for day in range(num_players - 1):
        matchups.append([f"Day {day + 1}"])
        for i in range(num_players // 2):
            player1 = names[i]
            player2 = names[num_players - 1 - i]
            matchups[day].append(f"{player1} vs {player2}")
        # Rotate the names for the next day
        names.insert(1, names.pop())
    return matchups

# Input player names and randomize order
num_players = 4
names = []
for i in range(num_players):
    player_name = input(f"Enter the name of Player {i + 1}: ")
    names.append(player_name)

# Randomize the order of players
random.shuffle(names)

# Generate match-ups
matchups = generate_matchups(names)

# Print the match-ups
for day_matchups in matchups:
    print("\n".join(day_matchups))
