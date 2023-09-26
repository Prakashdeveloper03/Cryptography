import java.util.Scanner;

public class HillCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] keyMatrix;

        System.out.println("Enter a 2x2 or 3x3 key matrix (space-separated values):");
        keyMatrix = readKeyMatrix(scanner);

        if (isValidKeyMatrix(keyMatrix)) {
            System.out.println("Select an option:");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("Enter the choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter the plain text (in uppercase): ");
                    String plaintext = scanner.nextLine();
                    String ciphertext = encrypt(plaintext, keyMatrix);
                    System.out.println("Cipher Text: " + ciphertext);
                    break;
                case 2:
                    System.out.print("Enter the cipher text (in uppercase): ");
                    String ciphertextInput = scanner.nextLine();
                    String decryptedText = decrypt(ciphertextInput, keyMatrix);
                    System.out.println("Plain Text: " + decryptedText);
                    break;
                default:
                    System.out.print("Invalid choice!");
            }
        } else {
            System.out.println("Invalid key matrix! The matrix should be square and invertible.");
        }
        scanner.close();
    }

    public static int[][] readKeyMatrix(Scanner scanner) {
        int size = scanner.nextInt();
        int[][] keyMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                keyMatrix[i][j] = scanner.nextInt();
            }
        }

        return keyMatrix;
    }

    public static boolean isValidKeyMatrix(int[][] keyMatrix) {
        return keyMatrix.length == keyMatrix[0].length && determinant(keyMatrix) != 0;
    }

    public static int determinant(int[][] matrix) {
        int n = matrix.length;
        if (n == 2) {
            return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
        }

        int det = 0;
        for (int i = 0; i < n; i++) {
            det += (matrix[0][i] * cofactor(matrix, 0, i));
        }
        return det;
    }

    public static int cofactor(int[][] matrix, int row, int col) {
        return (int) Math.pow(-1, row + col) * determinant(minor(matrix, row, col));
    }

    public static int[][] minor(int[][] matrix, int row, int col) {
        int n = matrix.length;
        int[][] minorMatrix = new int[n - 1][n - 1];
        int x = 0, y = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) continue;

            for (int j = 0; j < n; j++) {
                if (j == col) continue;
                minorMatrix[x][y] = matrix[i][j];
                y++;
            }
            x++;
            y = 0;
        }

        return minorMatrix;
    }

    public static int[][] adjugate(int[][] matrix) {
        int n = matrix.length;
        int[][] adjugateMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjugateMatrix[i][j] = cofactor(matrix, j, i);
            }
        }
        return adjugateMatrix;
    }

    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }

    public static int[][] inverse(int[][] matrix, int mod) {
        int det = determinant(matrix);
        int modInv = modInverse(det, mod);
        int[][] adjugateMatrix = adjugate(matrix);
        int n = matrix.length;
        int[][] inverseMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverseMatrix[i][j] = (adjugateMatrix[i][j] * modInv) % mod;
                if (inverseMatrix[i][j] < 0) {
                    inverseMatrix[i][j] += mod;
                }
            }
        }
        return inverseMatrix;
    }

    public static String encrypt(String plaintext, int[][] keyMatrix) {
        int n = keyMatrix.length;
        int mod = 26;

        while (plaintext.length() % n != 0) {
            plaintext += "X";
        }

        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += n) {
            String block = plaintext.substring(i, i + n);
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += (keyMatrix[j][k] * (block.charAt(k) - 'A'));
                }
                int encryptedChar = (sum % mod + mod) % mod;
                ciphertext.append((char) (encryptedChar + 'A'));
            }
        }

        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, int[][] keyMatrix) {
        int n = keyMatrix.length;
        int mod = 26;
        int modInv = modInverse(determinant(keyMatrix), mod);

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += n) {
            String block = ciphertext.substring(i, i + n);
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += (keyMatrix[k][j] * (block.charAt(k) - 'A'));
                }
                int decryptedChar = (sum * modInv) % mod;
                if (decryptedChar < 0) {
                    decryptedChar += mod;
                }
                plaintext.append((char) (decryptedChar + 'A'));
            }
        }

        return plaintext.toString();
    }
}