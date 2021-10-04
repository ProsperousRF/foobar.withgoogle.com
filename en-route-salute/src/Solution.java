/** @author Stanislav Rakitov */
public class Solution {
  public static int solution(String s) {
    int counter = 0;
    if (s != null) {
      int index;
      while ((index = s.indexOf('>')) != -1) {
        s = s.substring(index + 1);
        int count = (int) s.chars().filter(ch -> ch == '<').count();
        counter += count * 2;
      }
    }
    return counter;
  }
}
