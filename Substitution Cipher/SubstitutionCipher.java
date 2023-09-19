import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class SubstitutionCipher {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Encrypts a given text using a substitution cipher.
    public static String substitutionCipher(String text, Map<Character, Character> substitutionMap) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            char encrypted = character;
            if (substitutionMap.containsKey(character)) {
                encrypted = substitutionMap.get(character);
            }
            result.append(encrypted);
        }
        return result.toString();
    }

    // Creates a substitution map based on the provided key.
    public static Map<Character, Character> createSubstitutionMap(String key) {
        Map<Character, Character> substitutionMap = new HashMap<>();

        for (int i = 0; i < ALPHABET.length(); i++) {
            char originalChar = ALPHABET.charAt(i);
            char substituteChar = key.charAt(i % key.length());
            substitutionMap.put(originalChar, substituteChar);
            substitutionMap.put(Character.toLowerCase(originalChar), Character.toLowerCase(substituteChar));
        }
        return substitutionMap;
    }

    // Decrypts a given ciphertext using a substitution cipher.
    public static String reverseSubstitutionCipher(String ciphertext, Map<Character, Character> substitutionMap) {
        StringBuilder result = new StringBuilder();

        for (char character : ciphertext.toCharArray()) {
            char decrypted = character;
            for (Map.Entry<Character, Character> entry : substitutionMap.entrySet()) {
                if (entry.getValue() == character) {
                    decrypted = entry.getKey();
                    break;
                }
            }
            result.append(decrypted);
        }
        return result.toString();
    }

    // Validates a key for the substitution cipher.
    public static boolean isValidKey(String key) {
        // Check if the key has exactly 26 unique characters
        if (key.length() != 26) {
            return false;
        }
        for (char c : key.toCharArray()) {
            if (!Character.isLetter(c) || key.chars().filter(ch -> ch == c).count() > 1) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String key, plaintext, ciphertext;
        Map<Character, Character> substitutionMap;

        System.out.print("Enter the substitution key (26 unique characters): ");
        key = scanner.nextLine();

        // Validate the key
        if (isValidKey(key)) {
            substitutionMap = createSubstitutionMap(key);
            System.out.println("Select an option:");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("Enter the choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter the plain text: ");
                    scanner.nextLine();
                    plaintext = scanner.nextLine();
                    ciphertext = substitutionCipher(plaintext, substitutionMap);
                    System.out.println("Cipher Text: " + ciphertext);
                    break;
                case 2:
                    System.out.print("Enter the cipher text: ");
                    scanner.nextLine();
                    ciphertext = scanner.nextLine();
                    plaintext = reverseSubstitutionCipher(ciphertext, substitutionMap);
                    System.out.println("Plain Text: " + plaintext);
                    break;
                default:
                    System.out.print("Invalid choice!");
            }
        } else {
            System.out.println("Invalid key! The key must have 26 unique characters.");
        }
        scanner.close();
    }
}