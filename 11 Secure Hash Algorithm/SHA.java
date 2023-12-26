import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class SHA1Hash {
  public static String calculateSHA1(String input) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-1");
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

      String sha1Hash = calculateSHA1(input);
      System.out.println("SHA-1 Hash: " + sha1Hash);

      scanner.close();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }
}
