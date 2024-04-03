import unittest
import encrypter
import decrypter

class Test_encrypt_and_decrypt(unittest.TestCase):

    def test_encypt_username(self):
        self.assertEqual(encrypter.encrypt_username("daisy"), "gdlv|")
        self.assertEqual(encrypter.encrypt_username("kdennis"), "nghqqlv")


    def test_encrypt_password(self):
        self.assertEqual(encrypter.encrypt_password("Dennis1234"), "Ghqqlv4567")
        self.assertEqual(encrypter.encrypt_password("Kaylan321"), "Nd|odq654")


    def test_decrypt_username(self):
        self.assertEqual(decrypter.decrypt_username("gdlv|"),"daisy")
        self.assertEqual(decrypter.decrypt_username("nghqqlv"),"kdennis")

    def test_decrypt_password(self):
        self.assertEqual(decrypter.decrypt_password("Ghqqlv4567"),"Dennis1234")
        self.assertEqual(decrypter.decrypt_password("Nd|odq654"),"Kaylan321")


if __name__ == "__main__":
   unittest.main()



