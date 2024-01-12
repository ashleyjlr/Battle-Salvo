package cs3500.pa04.model.boardvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the CellState enum.
 */
class CellStateTest {

  /**
   * Test to string.
   */
  @Test
  void testToString() {
    assertEquals("*", CellState.HIT.toString());
    assertEquals("_", CellState.EMPTY.toString());
    assertEquals(".", CellState.MISS.toString());
  }
}