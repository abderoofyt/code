def solve_sudoku_board():
    user_input = input("Enter the Sudoku board as a text (leave blank to use default board): ").strip()
    if user_input:
        board = [list(map(int, line.strip().split())) for line in user_input.split('\n')]
    else:
        # Example Sudoku board
        board = [
            [5, 3, 0,    0, 7, 0,    0, 0, 0],
            [6, 0, 0,    1, 9, 5,    0, 0, 0],
            [0, 9, 8,    0, 0, 0,    0, 6, 0],
            
            [8, 0, 0,    0, 6, 0,    0, 0, 3],
            [4, 0, 0,    8, 0, 3,    0, 0, 1],
            [7, 0, 0,    0, 2, 0,    0, 0, 6],
            
            [0, 6, 0,    0, 0, 0,    2, 8, 0],
            [0, 0, 0,    4, 1, 9,    0, 0, 5],
            [0, 0, 0,    0, 8, 0,    0, 7, 9]
        ]

    print("Original Sudoku Board:")
    print_board(board)

    if solve_sudoku(board):
        print("\nSolved Sudoku Board:")
        print_board(board)
    else:
        print("\nNo solution exists.")

def is_valid(board, row, col, num):
    # Check if the number is already present in the row
    for x in range(9):
        if board[row][x] == num:
            return False
    
    # Check if the number is already present in the column
    for x in range(9):
        if board[x][col] == num:
            return False
    
    # Check if the number is already present in the 3x3 grid
    start_row, start_col = 3 * (row // 3), 3 * (col // 3)
    for i in range(3):
        for j in range(3):
            if board[i + start_row][j + start_col] == num:
                return False
    
    return True

def solve_sudoku(board):
    empty_cell = find_empty_cell(board)
    if not empty_cell:
        return True
    
    row, col = empty_cell
    
    for num in range(1, 10):
        if is_valid(board, row, col, num):
            board[row][col] = num
            
            if solve_sudoku(board):
                return True
            
            board[row][col] = 0
    
    return False

def find_empty_cell(board):
    for i in range(9):
        for j in range(9):
            if board[i][j] == 0:
                return (i, j)
    return None

def print_board(board):
    for i in range(9):
        if i % 3 == 0 and i != 0:
            print("- - - - - - - - - - - - ")
        for j in range(9):
            if j % 3 == 0 and j != 0:
                print(" | ", end="")
            if j == 8:
                print(board[i][j])
            else:
                print(str(board[i][j]) + " ", end="")

solve_sudoku_board()
