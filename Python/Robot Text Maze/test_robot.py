import unittest
from unittest.mock import patch
from io import StringIO
import sys
from robot import *

class TestRobot(unittest.TestCase):

    @patch('sys.stdin',StringIO('hal\nforward 1\nforward 4\nreplay\noff\n'))
    def test_15_replay(self):
        obstacles.random.randint = lambda a, b: 0


        sys.stdout = StringIO()
        robot_start()
        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal moved forward by 1 steps.
 > hal now at position (0,1).
hal: What must I do next?  > hal moved forward by 4 steps.
 > hal now at position (0,5).
hal: What must I do next?  > hal moved forward by 1 steps.
 > hal now at position (0,6).
 > hal moved forward by 4 steps.
 > hal now at position (0,10).
 > hal replayed 2 commands.
 > hal now at position (0,10).
hal: What must I do next? hal: Shutting down..
''', sys.stdout.getvalue())


    @patch('sys.stdin',StringIO('hal\nforward 1\nforward 4\nreplay silent\noff\n'))
    def test_16_replay_silent(self):
        obstacles.random.randint = lambda a, b: 0


        sys.stdout = StringIO()
        robot_start()
        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal moved forward by 1 steps.
 > hal now at position (0,1).
hal: What must I do next?  > hal moved forward by 4 steps.
 > hal now at position (0,5).
hal: What must I do next?  > hal replayed 2 commands silently.
 > hal now at position (0,10).
hal: What must I do next? hal: Shutting down..
''', sys.stdout.getvalue())


    @patch('sys.stdin',StringIO('hal\nforward 1\nright\nback 1\nreplay reversed\noff\n'))
    def test_17_replay_in_reverse(self):
        obstacles.random.randint = lambda a, b: 0

        sys.stdout = StringIO()
        robot_start()
        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal moved forward by 1 steps.
 > hal now at position (0,1).
hal: What must I do next?  > hal turned right.
 > hal now at position (0,1).
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (-1,1).
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (-2,1).
 > hal turned right.
 > hal now at position (-2,1).
 > hal moved forward by 1 steps.
 > hal now at position (-2,0).
 > hal replayed 3 commands in reverse.
 > hal now at position (-2,0).
hal: What must I do next? hal: Shutting down..
''', sys.stdout.getvalue())


    @patch('sys.stdin', StringIO('hal\nback 1\nleft\nright\nreplay reversed silent\noff\n'))
    def test_18_replay_reversed_silent(self):
        obstacles.random.randint = lambda a, b: 0

        sys.stdout = StringIO()
        robot_start()
        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (0,-1).
hal: What must I do next?  > hal turned left.
 > hal now at position (0,-1).
hal: What must I do next?  > hal turned right.
 > hal now at position (0,-1).
hal: What must I do next?  > hal replayed 3 commands in reverse silently.
 > hal now at position (0,-2).
hal: What must I do next? hal: Shutting down..
''', sys.stdout.getvalue())


    @patch('sys.stdin', StringIO('hal\nright\nleft\nforward 1\nback 1\nreplay 3\noff\n'))
    def test_19_including_nth_replay_command(self):
        obstacles.random.randint = lambda a, b: 0

        sys.stdout = StringIO()
        robot_start()

        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal turned right.
 > hal now at position (0,0).
hal: What must I do next?  > hal turned left.
 > hal now at position (0,0).
hal: What must I do next?  > hal moved forward by 1 steps.
 > hal now at position (0,1).
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (0,0).
hal: What must I do next?  > hal turned left.
 > hal now at position (0,0).
 > hal moved forward by 1 steps.
 > hal now at position (-1,0).
 > hal moved back by 1 steps.
 > hal now at position (0,0).
 > hal replayed 3 commands.
 > hal now at position (0,0).
hal: What must I do next? hal: Shutting down..
''',sys.stdout.getvalue())


    @patch('sys.stdin', StringIO('hal\nright\nleft\nback 1\nreplay 3-1\noff\n'))
    def test_19_including_nth_replay_command_with_dash(self):
        obstacles.random.randint = lambda a, b: 0

        sys.stdout = StringIO()
        robot_start()

        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal turned right.
 > hal now at position (0,0).
hal: What must I do next?  > hal turned left.
 > hal now at position (0,0).
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (0,-1).
hal: What must I do next?  > hal turned right.
 > hal now at position (0,-1).
 > hal turned left.
 > hal now at position (0,-1).
 > hal replayed 2 commands.
 > hal now at position (0,-1).
hal: What must I do next? hal: Shutting down..
''', sys.stdout.getvalue())


    @patch('sys.stdin',StringIO('hal\nright\nleft\nforward 1\nback 1\nreplay silent 3\noff\n'))
    def test_20_including_nth_replay_silent_command(self):
        obstacles.random.randint = lambda a, b: 0

        sys.stdout = StringIO()
        robot_start()

        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal turned right.
 > hal now at position (0,0).
hal: What must I do next?  > hal turned left.
 > hal now at position (0,0).
hal: What must I do next?  > hal moved forward by 1 steps.
 > hal now at position (0,1).
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (0,0).
hal: What must I do next?  > hal replayed 3 commands silently.
 > hal now at position (0,0).
hal: What must I do next? hal: Shutting down..
''', sys.stdout.getvalue())


    @patch('sys.stdin', StringIO('hal\nright\nleft\nback 1\nreplay silent 3-1\noff\n'))
    def test_20_including_nth_replay_silent_command__wth_dash(self):
        obstacles.random.randint = lambda a, b: 0

        sys.stdout = StringIO()
        robot_start()

        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal turned right.
 > hal now at position (0,0).
hal: What must I do next?  > hal turned left.
 > hal now at position (0,0).
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (0,-1).
hal: What must I do next?  > hal replayed 2 commands silently.
 > hal now at position (0,-1).
hal: What must I do next? hal: Shutting down..
''',sys.stdout.getvalue())


    @patch('sys.stdin',StringIO('hal\nright\nleft\nforward 1\nback 1\nreplay reversed 3\noff\n'))
    def test_21_including_nth_replay_reversed_command(self):
        obstacles.random.randint = lambda a, b: 0

        sys.stdout = StringIO()
        robot_start()

        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal turned right.
 > hal now at position (0,0).
hal: What must I do next?  > hal turned left.
 > hal now at position (0,0).
hal: What must I do next?  > hal moved forward by 1 steps.
 > hal now at position (0,1).
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (0,0).
hal: What must I do next?  > hal moved forward by 1 steps.
 > hal now at position (0,1).
 > hal turned left.
 > hal now at position (0,1).
 > hal turned right.
 > hal now at position (0,1).
 > hal replayed 3 commands in reverse.
 > hal now at position (0,1).
hal: What must I do next? hal: Shutting down..
''',sys.stdout.getvalue())


    @patch('sys.stdin', StringIO('hal\nright\nleft\nback 1\nreplay reversed 3-1\noff\n'))
    def test_21_including_nth_replay_reversed_command_with_dash(self):
        obstacles.random.randint = lambda a, b: 0

        sys.stdout = StringIO()
        robot_start()

        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal turned right.
 > hal now at position (0,0).
hal: What must I do next?  > hal turned left.
 > hal now at position (0,0).
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (0,-1).
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (0,-2).
 > hal turned left.
 > hal now at position (0,-2).
 > hal replayed 2 commands in reverse.
 > hal now at position (0,-2).
hal: What must I do next? hal: Shutting down..
''',sys.stdout.getvalue())


    @patch('sys.stdin',StringIO('hal\nright\nleft\nforward 1\nback 1\nreplay reversed silent 3\noff\n'))
    def test_22_including_nth_replay_reversed_silent_command(self):
        obstacles.random.randint = lambda a, b: 0

        sys.stdout = StringIO()
        robot_start()

        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal turned right.
 > hal now at position (0,0).
hal: What must I do next?  > hal turned left.
 > hal now at position (0,0).
hal: What must I do next?  > hal moved forward by 1 steps.
 > hal now at position (0,1).
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (0,0).
hal: What must I do next?  > hal replayed 3 commands in reverse silently.
 > hal now at position (0,1).
hal: What must I do next? hal: Shutting down..
''', sys.stdout.getvalue())


    @patch('sys.stdin', StringIO('hal\nright\nleft\nback 1\nreplay reversed silent 3-1\noff\n'))
    def test_22_including_nth_replay_reversed_silent_command_with_dash(self):
        obstacles.random.randint = lambda a, b: 0

        sys.stdout = StringIO()
        robot_start()

        self.assertEqual('''What do you want to name your robot? hal: Hello kiddo!
hal: What must I do next?  > hal turned right.
 > hal now at position (0,0).
hal: What must I do next?  > hal turned left.
 > hal now at position (0,0).
hal: What must I do next?  > hal moved back by 1 steps.
 > hal now at position (0,-1).
hal: What must I do next?  > hal replayed 2 commands in reverse silently.
 > hal now at position (0,-2).
hal: What must I do next? hal: Shutting down..
''', sys.stdout.getvalue())



if __name__ == "__main__":
    unittest.main()
