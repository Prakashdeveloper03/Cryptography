import java.util.Scanner;

public class VigenereCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vigenère Cipher");
        System.out.print("Enter the encryption keyword: ");
        String keyword = scanner.nextLine().toUpperCase();
        if (isValidKeyword(keyword)) {
            System.out.println("Select an option:");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.print("Enter the choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter the plain text (in uppercase): ");
                    String plaintext = scanner.nextLine().toUpperCase();
                    String ciphertext = encrypt(plaintext, keyword);
                    System.out.println("Cipher Text: " + ciphertext);
                    break;
                case 2:
                    System.out.print("Enter the cipher text (in uppercase): ");
                    String ciphertextInput = scanner.nextLine().toUpperCase();
                    String decryptedText = decrypt(ciphertextInput, keyword);
                    System.out.println("Plain Text: " + decryptedText);
                    break;
                default:
                    System.out.print("Invalid choice!");
            }
        } else {
            System.out.println("Invalid keyword! The keyword should consist of letters only.");
        }
        scanner.close();
    }

    public static boolean isValidKeyword(String keyword) {
        return keyword.matches("[A-Z]+");
    }

    public static String encrypt(String plaintext, String keyword) {
        StringBuilder ciphertext = new StringBuilder();
        int keywordLength = keyword.length();
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keywordChar = keyword.charAt(i % keywordLength);
            if (Character.isLetter(plainChar)) {
                int shift = keywordChar - 'A';
                char encryptedChar = (char) (((plainChar - 'A' + shift) % 26) + 'A');
                ciphertext.append(encryptedChar);
            } else {
                ciphertext.append(plainChar);
            }
        }
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, String keyword) {
        StringBuilder plaintext = new StringBuilder();
        int keywordLength = keyword.length();
        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keywordChar = keyword.charAt(i % keywordLength);
            if (Character.isLetter(cipherChar)) {
                int shift = keywordChar - 'A';
                char decryptedChar = (char) (((cipherChar - 'A' - shift + 26) % 26) + 'A');
                plaintext.append(decryptedChar);
            } else {
                plaintext.append(cipherChar);
            }
        }
        return plaintext.toString();
    }
}