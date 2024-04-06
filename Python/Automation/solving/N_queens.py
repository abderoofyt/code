def solve_n_queens_input():
    n = int(input("Enter the value of N for N-Queens problem: "))
    if n <= 0:
        print("N should be a positive integer.")
        return
    
    # Initialize an empty chessboard
    chessboard = [[0] * n for _ in range(n)]

    # Function to check if placing a queen at (row, col) is safe
    def is_safe(row, col):
        # Check if there's a queen in the same column
        for i in range(row):
            if chessboard[i][col] == 1:
                return False
        
        # Check upper left diagonal
        for i, j in zip(range(row, -1, -1), range(col, -1, -1)):
            if chessboard[i][j] == 1:
                return False
        
        # Check upper right diagonal
        for i, j in zip(range(row, -1, -1), range(col, n)):
            if chessboard[i][j] == 1:
                return False
        
        return True

    # Recursive function to solve N-Queens problem
    def place_queens(row):
        if row >= n:
            return True
        
        for col in range(n):
            if is_safe(row, col):
                chessboard[row][col] = 1
                if place_queens(row + 1):
                    return True
                chessboard[row][col] = 0
        
        return False

    # Call the recursive function to solve the problem
    if place_queens(0):
        print("Solution found:")
        for row in chessboard:
            print(row)
    else:
        print("No solution exists.")

# Call the function to solve N-Queens problem
solve_n_queens_input()
