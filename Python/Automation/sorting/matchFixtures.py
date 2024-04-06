import random

def generate_matchups(names):
    matchups = []
    num_players = len(names)
    for day in range(num_players - 1):
        daily_matchups = []
        for i in range(num_players // 2):
            player1 = names[i]
            player2 = names[num_players - 1 - i]
            daily_matchups.append(f"{player1} vs {player2}")
        matchups.append(daily_matchups)
        names.insert(1, names.pop())
    return matchups

def get_positive_integer(prompt):
    while True:
        try:
            value = int(input(prompt))
            if value <= 0:
                print("Please enter a positive integer.")
            else:
                return value
        except ValueError:
            print("Please enter a valid integer.")

def get_player_names(num_players):
    names = []
    for i in range(num_players):
        player_name = input(f"Enter the name of Player {i + 1}: ")
        names.append(player_name)
    return names

def solve_matchups():
    num_players = get_positive_integer("Enter the number of players: ")
    num_days = get_positive_integer("Enter the number of days: ")

    names = get_player_names(num_players)
    random.shuffle(names)

    matchups = generate_matchups(names)

    for day, day_matchups in enumerate(matchups[:num_days], start=1):
        print(f"\nDay {day} Match-ups:")
        print("\n".join(day_matchups))

# Call the function to solve match-ups
solve_matchups()
