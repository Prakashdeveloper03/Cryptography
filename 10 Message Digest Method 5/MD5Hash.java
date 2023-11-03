import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/** This class demonstrates the calculation of an MD5 hash for a user-provided string. */
public class MD5Hash {
  /**
   * Calculates the MD5 hash of the input string.
   *
   * @param input The string for which to calculate the MD5 hash.
   * @return The MD5 hash of the input string as a hexadecimal string.
   * @throws NoSuchAlgorithmException If the MD5 algorithm is not available.
   */
  public static String calculateMD5(String input) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] hashBytes = md.digest(input.getBytes());

    StringBuilder result = new StringBuilder();
    for (byte hashByte : hashBytes) {
      result.append(String.format("%02x", hashByte));
    }
    return result.toString();
  }

  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter a string: ");
      String input = scanner.nextLine();

      String md5Hash = calculateMD5(input);
      System.out.println("MD5 Hash: " + md5Hash);

      scanner.close();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }
}
