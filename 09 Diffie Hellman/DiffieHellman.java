import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/** This class implements the Diffie-Hellman key exchange algorithm for secure communication. */
public class DiffieHellman {

  /**
   * Calculate the result of (base^exponent) % modulus using modular exponentiation.
   *
   * @param base The base value.
   * @param exponent The exponent value.
   * @param modulus The modulus value.
   * @return The result of (base^exponent) % modulus.
   */
  public static long calculateModularExponentiation(long base, long exponent, long modulus) {
    if (exponent == 0) {
      return 1;
    } else if (exponent == 1) {
      return base % modulus;
    } else {
      long result = 1;
      for (int i = 0; i < exponent; i++) {
        result = (result * base) % modulus;
      }
      return result;
    }
  }

  /**
   * Check if a number is a primitive root modulo a given modulus.
   *
   * @param candidate The candidate primitive root.
   * @param modulus The modulus value.
   * @return True if the number is a primitive root, false otherwise.
   */
  public static boolean isPrimitiveRoot(long candidate, long modulus) {
    Set<Long> residues = new HashSet<>();
    long result = 1;
    for (int i = 0; i < modulus - 1; i++) {
      result = (result * candidate) % modulus;
      if (residues.contains(result) || result == 0) {
        return false;
      }
      residues.add(result);
    }
    return residues.size() == modulus - 1;
  }

  /**
   * Find all primitive roots modulo a given modulus.
   *
   * @param modulus The modulus value.
   * @return A list of primitive roots.
   */
  public static List<Long> findPrimitiveRoots(long modulus) {
    List<Long> primitiveRoots = new ArrayList<>();
    for (int i = 2; i < modulus; i++) {
      if (isPrimitiveRoot(i, modulus)) {
        primitiveRoots.add((long) i);
      }
    }
    return primitiveRoots;
  }

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    System.out.print("Enter a Prime Number (P): ");
    long modulus = input.nextLong();
    List<Long> primitiveRoots = findPrimitiveRoots(modulus);

    if (primitiveRoots.isEmpty()) {
      System.out.println("No primitive roots found for " + modulus);
      input.close();
      return;
    }

    System.out.println("Enter the Private Keys for Person A and Person B:");
    long privateKeyA = input.nextLong();
    long privateKeyB = input.nextLong();

    for (Long primitiveRoot : primitiveRoots) {
      long publicKeyA = calculateModularExponentiation(primitiveRoot, privateKeyA, modulus);
      long publicKeyB = calculateModularExponentiation(primitiveRoot, privateKeyB, modulus);

      long sharedKeyA = calculateModularExponentiation(publicKeyB, privateKeyA, modulus);
      long sharedKeyB = calculateModularExponentiation(publicKeyA, privateKeyB, modulus);

      System.out.println("Using Primitive Root G = " + primitiveRoot);
      System.out.println("Shared Secret Keys: A=" + sharedKeyA + ", B=" + sharedKeyB);

      if (sharedKeyA == sharedKeyB) {
        System.out.println("Shared Secret Keys Match!");
        break;
      } else {
        System.out.println("Shared Secret Keys Do Not Match!");
      }
    }
    input.close();
  }
}
