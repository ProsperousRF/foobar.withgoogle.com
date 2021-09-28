import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/** @author Stanislav Rakitov */
class SolutionTest {
  @Test
  void test12() {
    int testCase = 12;
    int[] testRes = {9, 1, 1, 1};
    int[] solRes = Solution.solution(testCase);
    assertArrayEquals(solRes, testRes);
  }

  @Test
  void testBig() {
    int testCase = 15324;
    int[] testRes = {15129, 169, 25, 1};
    int[] solRes = Solution.solution(testCase);
    assertArrayEquals(solRes, testRes);
  }
}
