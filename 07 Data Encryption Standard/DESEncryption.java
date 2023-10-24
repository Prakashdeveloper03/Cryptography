import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/** This class demonstrates DES encryption and decryption using a provided shift value. */
public class DESEncryption {
  /**
   * Generates a DES SecretKey using the provided shift value.
   *
   * @param shift The shift value to use for generating the SecretKey.
   * @return A DES SecretKey based on the provided shift value.
   * @throws Exception If there is an issue with generating the SecretKey.
   */
  private static SecretKey generateSecretKey(int shift) throws Exception {
    byte[] keyBytes = new byte[8];
    for (int i = 0; i < 8; i++) {
      keyBytes[i] = (byte) shift;
    }
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
    return keyFactory.generateSecret(desKeySpec);
  }

  /**
   * Encrypts the provided plaintext using the DES encryption algorithm.
   *
   * @param plaintext The plaintext to be encrypted.
   * @param secretKey The SecretKey used for encryption.
   * @return The encrypted ciphertext in bytes.
   * @throws Exception If there is an issue with the encryption process.
   */
  public static byte[] encrypt(String plaintext, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    return cipher.doFinal(plaintext.getBytes());
  }

  /**
   * Decrypts the provided ciphertext using the DES decryption algorithm.
   *
   * @param ciphertext The ciphertext to be decrypted in bytes.
   * @param secretKey The SecretKey used for decryption.
   * @return The decrypted plaintext.
   * @throws Exception If there is an issue with the decryption process.
   */
  public static String decrypt(byte[] ciphertext, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decryptedBytes = cipher.doFinal(ciphertext);
    return new String(decryptedBytes);
  }

  public static void main(String[] args) throws Exception {
    Scanner scanner = new Scanner(System.in);
    int choice, shift;
    String plaintext;
    System.out.println("Select an option:");
    System.out.println("1. Encrypt");
    System.out.println("2. Decrypt");
    System.out.print("Enter the choice: ");
    choice = scanner.nextInt();
    System.out.print("Enter the shift value (0-255): ");
    shift = scanner.nextInt();
    if (shift < 0 || shift > 255) {
      System.out.println("Shift value must be between 0 and 255.");
      scanner.close();
      return;
    }
    SecretKey secretKey = generateSecretKey(shift);
    switch (choice) {
      case 1:
        System.out.print("Enter the plain text: ");
        scanner.nextLine();
        plaintext = scanner.nextLine();
        byte[] encryptedBytes = encrypt(plaintext, secretKey);
        String encodedCipherText = Base64.getEncoder().encodeToString(encryptedBytes);
        System.out.println("Cipher Text: " + encodedCipherText);
        break;
      case 2:
        System.out.print("Enter the cipher text (Base64 encoded): ");
        scanner.nextLine();
        encodedCipherText = scanner.nextLine();
        byte[] decodedCipherText = Base64.getDecoder().decode(encodedCipherText);
        String decryptedText = decrypt(decodedCipherText, secretKey);
        System.out.println("Plain Text: " + decryptedText);
        break;
      default:
        System.out.print("Invalid choice!");
    }
    scanner.close();
  }
}
