import turtle
import robot

from world import obstacles

# initialize environment for turtle
turtle_world = turtle.Screen()
turtle.setup(800, 600)

# turtle custimization
turtle.pen(speed = 7)


# area limt border in turtle world
turtle.pu()
turtle.goto(-100, 200)
turtle.pencolor('red')
turtle.pd()
turtle.fd(200)
turtle.rt(90)
turtle.fd(400)
turtle.rt(90)
turtle.fd(200)
turtle.rt(90)
turtle.fd(400)
turtle.pu()
turtle.home()
turtle.pen(pencolor= 'black', fillcolor= 'red')

# Draw obstacles
obs_list = obstacles.get_obstacles()
# print(obs_list)

for i in (obs_list):

    turtle.pu()
    turtle.goto(i[0], i[1])
    # turtle.pen(pencolor= 'red', fillcolor= 'red')
    turtle.begin_fill()
    turtle.pd()
    turtle.goto((i[0] +4), i[1])
    turtle.goto((i[0] +4), (i[1] +4))
    turtle.goto(i[0], (i[1] +4))
    turtle.goto(i[0], i[1])
    turtle.end_fill()
    turtle.pu()
    # turtle.pen(pencolor= 'black', fillcolor= 'red')
    turtle.home()
turtle.lt(90)
turtle.pen(speed = 2)

# variables tracking position and direction
position_x = 0
position_y = 0
directions = ['forward', 'right', 'back', 'left']
current_direction_index = 0


# area limit vars
min_y, max_y = -200, 200
min_x, max_x = -100, 100


def do_left_turn(robot_name):
    """
    Do a 90 degree turn to the left
    :param robot_name:
    :return: (True, left turn output text)
    """
    global current_direction_index

    current_direction_index -= 1
    if current_direction_index < 0:
        current_direction_index = 3

    turtle.lt(90)

    return True, ' > '+robot_name+' turned left.'


def do_right_turn(robot_name):
    """
    Do a 90 degree turn to the right
    :param robot_name:
    :return: (True, right turn output text)
    """
    global current_direction_index

    current_direction_index += 1
    if current_direction_index > 3:
        current_direction_index = 0

    turtle.rt(90)

    return True, ' > '+robot_name+' turned right.'


def show_position(robot_name):

    turtle.goto(position_x, position_y)
    print(' > '+robot_name+' now at position ('+str(position_x)+','+str(position_y)+').')


def is_position_allowed(new_x, new_y):
    """
    Checks if the new position will still fall within the max area limit
    :param new_x: the new/proposed x position
    :param new_y: the new/proposed y position
    :return: True if allowed, i.e. it falls in the allowed area, else False
    """

    return min_x <= new_x <= max_x and min_y <= new_y <= max_y


def update_position(steps, robot_name):
    """
    Update the current x and y positions given the current direction, and specific nr of steps
    :param steps:
    :return: True if the position was updated, else False
    """

    global position_x, position_y
    new_x = position_x
    new_y = position_y

    if directions[current_direction_index] == 'forward':
        new_y = new_y + steps
    elif directions[current_direction_index] == 'right':
        new_x = new_x + steps
    elif directions[current_direction_index] == 'back':
        new_y = new_y - steps
    elif directions[current_direction_index] == 'left':
        new_x = new_x - steps

    if obstacles.is_path_blocked(position_x, position_y, new_x, new_y):
        print(f'{robot_name}: Sorry, there is an obstancle in the way.')
        return True

    if is_position_allowed(new_x, new_y):
        position_x = new_x
        position_y = new_y
        return True

    else:
        return False

