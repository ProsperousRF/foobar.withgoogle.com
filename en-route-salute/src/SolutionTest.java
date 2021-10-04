import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** @author Stanislav Rakitov */
class SolutionTest {

  @Test
  void solution4() {
    String testcase = "<<>><";
    int expected = 4;

    assertEquals(expected, Solution.solution(testcase));
  }

  @Test
  void solution2() {
    String testcase = ">----<";
    int expected = 2;

    assertEquals(expected, Solution.solution(testcase));
  }

}
