import java.util.Scanner;

/** This class implements the Hill Cipher for encryption and decryption of text. */
public class HillCipher {
  /**
   * Reads a key matrix of a specified size from the user input.
   *
   * @param scanner The scanner to read user input.
   * @return The key matrix read from the input.
   */
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

  /**
   * Checks if the provided key matrix is valid (square and invertible).
   *
   * @param keyMatrix The key matrix to be validated.
   * @return True if the key matrix is valid, false otherwise.
   */
  public static boolean isValidKeyMatrix(int[][] keyMatrix) {
    return keyMatrix.length == keyMatrix[0].length && determinant(keyMatrix) != 0;
  }

  /**
   * Calculates the determinant of a matrix.
   *
   * @param matrix The input matrix.
   * @return The determinant of the matrix.
   */
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

  /**
   * Calculates the cofactor of an element in the matrix.
   *
   * @param matrix The input matrix.
   * @param row The row index of the element.
   * @param col The column index of the element.
   * @return The cofactor of the element.
   */
  public static int cofactor(int[][] matrix, int row, int col) {
    return (int) Math.pow(-1, row + col) * determinant(minor(matrix, row, col));
  }

  /**
   * Computes the minor of a matrix by removing a specific row and column.
   *
   * @param matrix The input matrix.
   * @param row The row to be removed.
   * @param col The column to be removed.
   * @return The minor matrix.
   */
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

  /**
   * Computes the adjugate matrix of a given matrix.
   *
   * @param matrix The input matrix.
   * @return The adjugate matrix.
   */
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

  /**
   * Calculates the modular inverse of a number.
   *
   * @param a The number for which to find the modular inverse.
   * @param m The modulus.
   * @return The modular inverse of 'a' modulo 'm'.
   */
  public static int modInverse(int a, int m) {
    a = a % m;
    for (int x = 1; x < m; x++) {
      if ((a * x) % m == 1) {
        return x;
      }
    }
    return 1;
  }

  /**
   * Computes the inverse of a key matrix.
   *
   * @param matrix The key matrix to be inverted.
   * @param mod The modulus.
   * @return The inverse matrix if it exists; otherwise, null.
   */
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

  /**
   * Encrypts the given plaintext using the Hill Cipher.
   *
   * @param plaintext The plaintext to be encrypted (in uppercase).
   * @param keyMatrix The key matrix for encryption.
   * @return The encrypted ciphertext.
   */
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

  /**
   * Decrypts the given ciphertext using the Hill Cipher.
   *
   * @param ciphertext The ciphertext to be decrypted (in uppercase).
   * @param keyMatrix The key matrix for decryption.
   * @return The decrypted plaintext.
   */
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

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int[][] keyMatrix;

    System.out.println("Enter a 2x2 or 3x3 key matrix (space-separated values):");
    keyMatrix = readKeyMatrix(scanner);

    if (isValidKeyMatrix(keyMatrix)) {
      System.out.println("Select an option:");
      System.out.println("1. Encrypt");
      System.out.println("2. Decrypt");
      System.out.print("Enter the choice: ");
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
}
