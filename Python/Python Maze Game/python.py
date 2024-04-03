import tkinter as tk
from tkinter import messagebox
import random

# Constants
ROWS = 15
COLS = 20
CELL_SIZE = 40

class MazeGame:
    def __init__(self):
        self.root = tk.Tk()
        self.root.title("Maze Game")
        self.canvas = tk.Canvas(self.root, width=COLS * CELL_SIZE, height=ROWS * CELL_SIZE, bg="white")
        self.canvas.pack()
        self.generate_maze()
        self.draw_maze()
        self.player = {'x': 0, 'y': 0, 'size': CELL_SIZE // 2, 'color': 'blue'}
        self.exit = {'x': COLS - 1, 'y': ROWS - 1, 'size': CELL_SIZE, 'color': 'green'}
        self.draw_player()
        self.draw_exit()
        self.root.bind("<KeyPress>", self.key_pressed)
        self.root.mainloop()

    def generate_maze(self):
        self.maze = [[1] * COLS for _ in range(ROWS)]
        self.maze[0][0] = 0
        self.carve_passages_from(0, 0)
        self.maze[ROWS - 1][COLS - 1] = 0

    def carve_passages_from(self, x, y):
        directions = [[1, 0], [-1, 0], [0, 1], [0, -1]]
        random.shuffle(directions)

        for dx, dy in directions:
            nx, ny = x + dx * 2, y + dy * 2
            if 0 <= nx < COLS and 0 <= ny < ROWS and self.maze[ny][nx] == 1:
                self.maze[y + dy][x + dx] = 0
                self.maze[ny][nx] = 0
                self.carve_passages_from(nx, ny)

    def draw_maze(self):
        for y in range(ROWS):
            for x in range(COLS):
                color = "black" if self.maze[y][x] == 1 else "white"
                self.canvas.create_rectangle(x * CELL_SIZE, y * CELL_SIZE,
                                             (x + 1) * CELL_SIZE, (y + 1) * CELL_SIZE,
                                             fill=color)

    def draw_player(self):
        self.player_id = self.canvas.create_rectangle(self.player['x'] * CELL_SIZE + (CELL_SIZE - self.player['size']) / 2,
                                                      self.player['y'] * CELL_SIZE + (CELL_SIZE - self.player['size']) / 2,
                                                      self.player['x'] * CELL_SIZE + (CELL_SIZE - self.player['size']) / 2 + self.player['size'],
                                                      self.player['y'] * CELL_SIZE + (CELL_SIZE - self.player['size']) / 2 + self.player['size'],
                                                      fill=self.player['color'])

    def draw_exit(self):
        self.exit_id = self.canvas.create_rectangle(self.exit['x'] * CELL_SIZE,
                                                    self.exit['y'] * CELL_SIZE,
                                                    (self.exit['x'] + 1) * CELL_SIZE,
                                                    (self.exit['y'] + 1) * CELL_SIZE,
                                                    fill=self.exit['color'])

    def key_pressed(self, event):
        dx, dy = 0, 0
        if event.keysym == 'Up':
            dy = -1
        elif event.keysym == 'Down':
            dy = 1
        elif event.keysym == 'Left':
            dx = -1
        elif event.keysym == 'Right':
            dx = 1
        self.move_player(dx, dy)
        self.check_win()

    def move_player(self, dx, dy):
        new_x, new_y = self.player['x'] + dx, self.player['y'] + dy
        if 0 <= new_x < COLS and 0 <= new_y < ROWS and self.maze[new_y][new_x] == 0:
            self.canvas.move(self.player_id, dx * CELL_SIZE, dy * CELL_SIZE)
            self.player['x'], self.player['y'] = new_x, new_y

    def check_win(self):
        if self.player['x'] == self.exit['x'] and self.player['y'] == self.exit['y']:
            messagebox.showinfo("Congratulations", "You've escaped the maze!")
            self.root.destroy()

if __name__ == "__main__":
    MazeGame()
