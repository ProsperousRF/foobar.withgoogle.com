import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stanislav Rakitov
 */
public class Solution {
  public static String solution(int[] xs) {

    if (xs.length == 0) {
      return "0";
    }

    if (xs.length == 1) {
      return Integer.toString(xs[0]);
    }

    List<Integer> positives = new ArrayList<>();
    List<Integer> negatives = new ArrayList<>();

    for (int x : xs) {
        if (x > 0) {
          positives.add(x);
        } else if (x < 0) {
          negatives.add(x);
        }
    }
    int pS = positives.size();
    int nS = negatives.size();
    if (pS == 0 && ( nS == 0 || nS == 1)) {
      return "0";
    }

    BigInteger result = new BigInteger("1");
    for (int x : positives) {
      result = result.multiply(new BigInteger(String.valueOf(x)));
    }

    if (nS %2 == 1) {
      negatives.remove(Collections.max(negatives));
    }
    
    for (int x : negatives) {
      result = result.multiply(new BigInteger(String.valueOf(x)));
    }

    return result.toString();
  }
}
