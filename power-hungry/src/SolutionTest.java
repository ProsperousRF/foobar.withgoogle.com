import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** @author Stanislav Rakitov */
class SolutionTest {

  @Test
  void solution1() {
    int[] testCase = {2, 0, 2, 2, 0};
    String expected = "8";
    assertEquals(expected, Solution.solution(testCase));
  }

  @Test
  void solution2() {
    int[] testCase = {-2, -3, 4, -5};
    String expected = "60";
    assertEquals(expected, Solution.solution(testCase));
  }

  @Test
  void maxValues() {
    int[] testCase = {
            1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000,
            1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000,
            1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000,
            1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000,
            1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000,
    };
    String expected = "1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
    assertEquals(expected, Solution.solution(testCase));
  }
}
