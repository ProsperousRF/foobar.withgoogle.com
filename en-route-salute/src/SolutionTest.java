import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** @author Stanislav Rakitov */
class SolutionTest {

  @Test
  @DisplayName("<<>><")
  void solution4() {
    String testcase = "<<>><";
    int expected = 4;

    assertEquals(expected, Solution.solution(testcase));
  }

  @Test
  @DisplayName(">----<")
  void solution2() {
    String testcase = ">----<";
    int expected = 2;

    assertEquals(expected, Solution.solution(testcase));
  }

  @Test
  @DisplayName("Empty string")
  void empty() {
    String testcase = "";
    int expected = 0;
    assertEquals(expected, Solution.solution(testcase));
  }

  @Test
  @DisplayName("Null string")
  void nullString() {
    int expected = 0;
    assertEquals(expected, Solution.solution(null));
  }
}
