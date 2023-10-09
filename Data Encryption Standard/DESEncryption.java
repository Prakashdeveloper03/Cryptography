import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class DESEncryption {
    private static SecretKey generateSecretKey(int shift) throws Exception {
        byte[] keyBytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            keyBytes[i] = (byte) shift;
        }
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
        return keyFactory.generateSecret(desKeySpec);
    }

    public static byte[] encrypt(String plaintext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plaintext.getBytes());
    }

    public static String decrypt(byte[] ciphertext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(ciphertext);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int choice, shift;
        String plaintext, ciphertext;
        System.out.println("Select an option:");
        System.out.println("1. Encrypt");
        System.out.println("2. Decrypt");
        System.out.print("Enter the choice: ");
        choice = scanner.nextInt();
        System.out.print("Enter the shift value (0-255): ");
        shift = scanner.nextInt();
        if (shift < 0 || shift > 255) {
            System.out.println("Shift value must be between 0 and 255.");
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
