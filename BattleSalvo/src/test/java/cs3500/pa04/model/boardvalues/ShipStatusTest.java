package cs3500.pa04.model.boardvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the ShipStatus enum.
 */
class ShipStatusTest {

  /**
   * Test to string.
   */
  @Test
  void testToString() {
    assertEquals("S", ShipStatus.NOT_HIT.toString());
    assertEquals("*", ShipStatus.HIT.toString());
  }
}