import java.util.Scanner;

public class PlayfairCipher {
    // Function to create the Playfair matrix from the key
    public static char[][] createPlayfairMatrix(String key) {
        char[][] matrix = new char[5][5];
        String keyWithoutDuplicates = removeDuplicateCharacters(key + "ABCDEFGHIKLMNOPQRSTUVWXYZ");
        int keyLength = keyWithoutDuplicates.length();
        int row = 0, col = 0;

        for (int i = 0; i < keyLength; i++) {
            matrix[row][col] = keyWithoutDuplicates.charAt(i);
            col++;
            if (col == 5) {
                col = 0;
                row++;
            }
        }

        char currentChar = 'A';
        for (int i = row; i < 5; i++) {
            for (int j = col; j < 5; j++) {
                while (keyWithoutDuplicates.indexOf(currentChar) != -1) {
                    currentChar++;
                }
                matrix[i][j] = currentChar;
                currentChar++;
            }
            col = 0;
        }

        return matrix;
    }

    // Function to remove duplicate characters from a string
    public static String removeDuplicateCharacters(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (result.indexOf(String.valueOf(currentChar)) == -1) {
                result.append(currentChar);
            }
        }
        return result.toString();
    }

    // Function to preprocess the plaintext (remove spaces and handle double letters)
    public static String preprocessText(String plaintext) {
        StringBuilder result = new StringBuilder();
        plaintext = plaintext.replaceAll(" ", "").toUpperCase();
        for (int i = 0; i < plaintext.length(); i += 2) {
            char firstChar = plaintext.charAt(i);
            char secondChar = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';
            if (firstChar == secondChar) {
                secondChar = 'X';
                i--;
            }
            result.append(firstChar).append(secondChar);
        }
        return result.toString();
    }

    // Function to find the position of a character in the matrix
    public static int[] findCharacterPosition(char[][] matrix, char target) {
        int[] position = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == target) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    // Function to perform Playfair encryption
    public static String playfairCipher(String plaintext, String key) {
        char[][] matrix = createPlayfairMatrix(key);
        String processedText = preprocessText(plaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < processedText.length(); i += 2) {
            char firstChar = processedText.charAt(i);
            char secondChar = processedText.charAt(i + 1);
            int[] firstCharPosition = findCharacterPosition(matrix, firstChar);
            int[] secondCharPosition = findCharacterPosition(matrix, secondChar);

            int row1 = firstCharPosition[0];
            int col1 = firstCharPosition[1];
            int row2 = secondCharPosition[0];
            int col2 = secondCharPosition[1];

            if (row1 == row2) {
                ciphertext.append(matrix[row1][(col1 + 1) % 5]);
                ciphertext.append(matrix[row2][(col2 + 1) % 5]);
            } else if (col1 == col2) {
                ciphertext.append(matrix[(row1 + 1) % 5][col1]);
                ciphertext.append(matrix[(row2 + 1) % 5][col2]);
            } else {
                ciphertext.append(matrix[row1][col2]);
                ciphertext.append(matrix[row2][col1]);
            }
        }

        return ciphertext.toString();
    }

    // Function to perform Playfair decryption
    public static String playfairDecipher(String ciphertext, String key) {
        char[][] matrix = createPlayfairMatrix(key);
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            char firstChar = ciphertext.charAt(i);
            char secondChar = ciphertext.charAt(i + 1);
            int[] firstCharPosition = findCharacterPosition(matrix, firstChar);
            int[] secondCharPosition = findCharacterPosition(matrix, secondChar);

            int row1 = firstCharPosition[0];
            int col1 = firstCharPosition[1];
            int row2 = secondCharPosition[0];
            int col2 = secondCharPosition[1];

            if (row1 == row2) {
                plaintext.append(matrix[row1][(col1 - 1 + 5) % 5]);
                plaintext.append(matrix[row2][(col2 - 1 + 5) % 5]);
            } else if (col1 == col2) {
                plaintext.append(matrix[(row1 - 1 + 5) % 5][col1]);
                plaintext.append(matrix[(row2 - 1 + 5) % 5][col2]);
            } else {
                plaintext.append(matrix[row1][col2]);
                plaintext.append(matrix[row2][col1]);
            }
        }

        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputText, key, result;
        int choice;

        System.out.println("Playfair Cipher");
        System.out.print("Enter the key (without spaces): ");
        key = scanner.nextLine().toUpperCase();

        System.out.println("Select an option:");
        System.out.println("1. Encrypt");
        System.out.println("2. Decrypt");
        System.out.print("Enter the choice: ");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.print("Enter the plain text: ");
                inputText = scanner.nextLine().toUpperCase();
                result = playfairCipher(inputText, key);
                System.out.println("Cipher Text: " + result);
                break;
            case 2:
                System.out.print("Enter the cipher text: ");
                inputText = scanner.nextLine().toUpperCase();
                result = playfairDecipher(inputText, key);
                System.out.println("Decrypted Text: " + result);
                break;
            default:
                System.out.println("Invalid choice!");
        }
        scanner.close();
    }
}