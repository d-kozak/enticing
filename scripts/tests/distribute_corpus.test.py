import unittest

from distribute_corpus import handle_args, split_files


class HandleArgumentsTest(unittest.TestCase):
    def test_incorrect_arguments(self):
        self.assertRaises(ValueError, lambda: handle_args([]))


class SplitFilesTest(unittest.TestCase):
    def test_just_one_server(self):
        res = split_files(['one', 'two', 'three'], ['main'])
        self.assertDictEqual(res, {
            'main': ['one', 'two', 'three']
        })

    def test_two_servers(self):
        res = split_files(['one', 'two', 'three'], ['main', 'sec'])
        self.assertDictEqual(res, {
            'main': ['one', 'two'],
            'sec': ['three']
        })

    def test_three_servers(self):
        res = split_files(['one', 'two', 'three'], ['main', 'sec', 'foo'])
        self.assertDictEqual(res, {
            'main': ['one'],
            'sec': ['two'],
            'foo': ['three']
        })


if __name__ == '__main__':
    unittest.main()
