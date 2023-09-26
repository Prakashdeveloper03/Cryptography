import java.util.Scanner;

public class CaesarCipher {
    public static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            if (character >= 'a' && character <= 'z') {
                char encrypted = (char) ('a' + (character - 'a' + shift) % 26);
                result.append(encrypted);
            } else if (character >= 'A' && character <= 'Z') {
                char encrypted = (char) ('A' + (character - 'A' + shift) % 26);
                result.append(encrypted);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice, shift;
        String plaintext, ciphertext;

        System.out.println("Select an option:");
        System.out.println("1. Encrypt");
        System.out.println("2. Decrypt");
        System.out.print("Enter the choice: ");
        choice = scanner.nextInt();
        System.out.print("Enter the number of shifts: ");
        shift = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter the plain text: ");
                scanner.nextLine();
                plaintext = scanner.nextLine();
                ciphertext = caesarCipher(plaintext, shift);
                System.out.println("Cipher Text: " + ciphertext);
                break;
            case 2:
                System.out.print("Enter the cipher text: ");
                scanner.nextLine();
                ciphertext = scanner.nextLine();
                plaintext = caesarCipher(ciphertext, 26 - shift);
                System.out.println("Plain Text: " + plaintext);
                break;
            default:
                System.out.print("Invalid choice!");
        }
        scanner.close();
    }
}