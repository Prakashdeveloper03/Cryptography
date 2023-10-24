import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;

/** This class demonstrates RSA encryption and decryption. */
public class RSAEncryption {
  /**
   * Generates an RSA key pair with a 2048-bit key size.
   *
   * @return A KeyPair object containing the generated public and private keys.
   * @throws NoSuchAlgorithmException If the RSA algorithm is not available.
   */
  public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);
    return keyPairGenerator.generateKeyPair();
  }

  /**
   * Encrypts the given plaintext using the provided public key.
   *
   * @param plaintext The plaintext to be encrypted.
   * @param publicKey The public key used for encryption.
   * @return An array of bytes representing the encrypted ciphertext.
   * @throws Exception If an error occurs during encryption.
   */
  public static byte[] encrypt(String plaintext, PublicKey publicKey) throws Exception {
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    return cipher.doFinal(plaintext.getBytes());
  }

  /**
   * Decrypts the given ciphertext using the provided private key.
   *
   * @param ciphertext The ciphertext to be decrypted.
   * @param privateKey The private key used for decryption.
   * @return The decrypted plaintext as a string.
   * @throws Exception If an error occurs during decryption.
   */
  public static String decrypt(byte[] ciphertext, PrivateKey privateKey) throws Exception {
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] decryptedBytes = cipher.doFinal(ciphertext);
    return new String(decryptedBytes);
  }

  public static void main(String[] args) throws Exception {
    Scanner scanner = new Scanner(System.in);
    String plaintext;
    KeyPair keyPair = generateRSAKeyPair();
    System.out.print("Enter the plain text: ");
    plaintext = scanner.nextLine();

    // Encrypt with the public key
    byte[] encryptedBytes = encrypt(plaintext, keyPair.getPublic());
    String encodedCipherText = Base64.getEncoder().encodeToString(encryptedBytes);
    System.out.println("\nCipher Text: " + encodedCipherText);

    // Decrypt with the private key
    byte[] decodedCipherText = Base64.getDecoder().decode(encodedCipherText);
    String decryptedText = decrypt(decodedCipherText, keyPair.getPrivate());
    System.out.println("\nPlain Text: " + decryptedText);

    scanner.close();
  }
}
