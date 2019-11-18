import configparser
import unittest

from utils.config_validation import verify_config


class ConfigValidationTest(unittest.TestCase):

    def test_correct_file(self):
        with open("./config.test.ini") as f:
            config = configparser.ConfigParser(interpolation=configparser.ExtendedInterpolation())
            config.read_string(f.read())
            self.assertTrue(len(verify_config(config)) == 0)
            pass


if __name__ == '__main__':
    unittest.main()
