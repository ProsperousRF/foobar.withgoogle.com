import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/** @author Stanislav Rakitov */
class SolutionTest {

  @Test
  void solution1() {
    int[][] testcase = {
      {0, 2, 1, 0, 0},
      {0, 0, 0, 3, 4},
      {0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0}
    };
    int[] expected = {7, 6, 8, 21};

    assertArrayEquals(expected, Solution.solution(testcase));
  }

  @Test
  void solution2() {
    int[][] testcase = {
      {0, 1, 0, 0, 0, 1},
      {4, 0, 0, 3, 2, 0},
      {0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0},
      {0, 0, 0, 0, 0, 0}
    };
    int[] expected = {0, 3, 2, 9, 14};

    assertArrayEquals(expected, Solution.solution(testcase));
  }
}
