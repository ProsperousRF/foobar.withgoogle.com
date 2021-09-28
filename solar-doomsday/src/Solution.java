import java.util.ArrayList;

/** @author Stanislav Rakitov */
public class Solution {
  public static int[] solution(int area) {
    ArrayList<Integer> list = new ArrayList<>();
    int tempArea = area;
    while (tempArea > 0) {
      int square = (int) Math.pow((int) Math.sqrt(tempArea), 2);
      list.add(square);
      tempArea -= square;
    }

    int[] result = new int[list.size()];
    for (int i = 0; i < list.size(); i++) {
      result[i] = list.get(i);
    }
    return result;
  }
}
