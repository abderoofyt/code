import unittest
from world import obstacles
import sys
from io import StringIO

class TestObstacles(unittest.TestCase):

    def test_1_if_path_is_blocked_by_obstacle_on_x_axis(self):

        obstacles.obstacle_list = [(50,40) , (10,12)]
        self.assertTrue(obstacles.is_path_blocked(0,40 , 60,40))

    def test_1_if_path_is_blocked_by_obstacle_on_y_axis(self):
        
        obstacles.obstacle_list = [(50,40) , (10,12)]
        self.assertTrue(obstacles.is_path_blocked(10,0 , 10,12))

    def test_1_if_path_is_not_blocked(self):

        obstacles.obstacle_list = [(50,40) , (10,12)]
        self.assertFalse(obstacles.is_path_blocked(0,0 , 0,-10))

    def test_2_if_pos_blocked_by_obstacle(self):

        obstacles.obstacle_list = [(2,4)]
        self.assertTrue(obstacles.is_position_blocked(2,4))

    def test_2_if_pos_not_blocked(self):

        obstacles.obstacle_list = [(2, 4)]
        self.assertFalse(obstacles.is_position_blocked(0,0))

if __name__ == '__main__':
    unittest.main()