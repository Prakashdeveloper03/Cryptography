import java.util.Scanner;

public class RailFenceCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Rail Fence Cipher");
        System.out.println("Select an option:");
        System.out.println("1. Encrypt");
        System.out.println("2. Decrypt");
        System.out.print("Enter the choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter the number of rails: ");
                int rails = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter the text to encrypt: ");
                String plaintext = scanner.nextLine();

                String encryptedText = encrypt(plaintext, rails);
                System.out.println("Encrypted Text: " + encryptedText);
                break;

            case 2:
                System.out.print("Enter the number of rails: ");
                int railsToDecrypt = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter the text to decrypt: ");
                String ciphertext = scanner.nextLine();

                String decryptedText = decrypt(ciphertext, railsToDecrypt);
                System.out.println("Decrypted Text: " + decryptedText);
                break;

            default:
                System.out.println("Invalid choice. Please enter '1' to encrypt or '2' to decrypt.");
        }
        scanner.close();
    }

    public static String encrypt(String plaintext, int rails) {
        StringBuilder[] railsArray = new StringBuilder[rails];

        for (int i = 0; i < rails; i++) {
            railsArray[i] = new StringBuilder();
        }

        int currentRail = 0;
        boolean goingDown = true;

        for (char c : plaintext.toCharArray()) {
            railsArray[currentRail].append(c);
            if (currentRail == 0) {
                goingDown = true;
            } else if (currentRail == rails - 1) {
                goingDown = false;
            }

            if (goingDown) {
                currentRail++;
            } else {
                currentRail--;
            }
        }

        StringBuilder ciphertext = new StringBuilder();
        for (StringBuilder rail : railsArray) {
            ciphertext.append(rail);
        }

        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, int rails) {
        StringBuilder[] railsArray = new StringBuilder[rails];

        for (int i = 0; i < rails; i++) {
            railsArray[i] = new StringBuilder();
        }

        int[] railLengths = new int[rails];
        int index = 0;
        boolean goingDown = true;

        for (char c : ciphertext.toCharArray()) {
            railsArray[index].append(c);
            railLengths[index]++;
            if (index == 0) {
                goingDown = true;
            } else if (index == rails - 1) {
                goingDown = false;
            }
            if (goingDown) {
                index++;
            } else {
                index--;
            }
        }

        char[] plaintextChars = new char[ciphertext.length()];
        int pos = 0;
        for (int i = 0; i < rails; i++) {
            char[] railChars = railsArray[i].toString().toCharArray();
            for (int j = 0; j < railLengths[i]; j++) {
                plaintextChars[pos++] = railChars[j];
            }
        }
        return new String(plaintextChars);
    }
}