package cs3500.pa04.model.boardvalues;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the ShipType enum.
 */
class ShipTypeTest {

  /**
   * Test to string.
   */
  @Test
  void testToString() {
    assertEquals("S", ShipType.SUBMARINE.toString());
    assertEquals("D", ShipType.DESTROYER.toString());
    assertEquals("B", ShipType.BATTLESHIP.toString());
    assertEquals("C", ShipType.CARRIER.toString());
  }

  /**
   * Test get size.
   */
  @Test
  void testGetSize() {
    assertEquals(3, ShipType.SUBMARINE.getSize());
    assertEquals(4, ShipType.DESTROYER.getSize());
    assertEquals(5, ShipType.BATTLESHIP.getSize());
    assertEquals(6, ShipType.CARRIER.getSize());
  }
}