import unittest
from world.text import world

class TestWorld(unittest.TestCase):

    def test_1_check_area_limit_TRUE(self):
        
        result = world.is_position_allowed(0, 0)
        self.assertTrue(result)

    def test_2_check_area_limit_False(self):
        result = world.is_position_allowed(201, 101)
        self.assertFalse(result)









if __name__ == '__main__':
    unittest.main()