import random
import turtle
from world.text import world

# Create an empty list to store created 
obstacle_list = []


def get_obstacles():
    '''
    Generates a random obstacle list which randomizes after every intitial start.
    '''
    global obstacle_list
    
    for i in range(random.randint(0, 10)):

        x = random.randint(-100, 100)
        y = random.randint(-200, 200)

        obstacle = (x, y)
        obstacle_list.append(obstacle)

    return obstacle_list


def is_position_blocked(x, y):
    '''
    Checks if any obstacles are on the coordinates of the robot's coordinates.
    '''

    global obstacle_list

    for i in obstacle_list:
        if i[0] <= x <= i[0] + 4 and i[1] <= y <= i[1] + 4:
            return True  
    return False


def is_path_blocked(x1, y1, x2, y2):
    '''
    Checks if any obstacles are in the way of the robots path.
    * Using 'is_position_blocked', it prechecks if any obstacles in the driection the robot is moving towards.
    '''

    global obstacle_list

    if x1 > x2:
        x1, x2 = x2, x1
    if y1 > y2:
        y1, y2 = y2, y1

    if x1 == x2:
        for y in range(y1, y2 +1):
            if is_position_blocked(x1, y):
                return True

    if y1 == y2:            
        for x in range(x1, x2 +1):
            if is_position_blocked(x, y1):
                return True

    return False

