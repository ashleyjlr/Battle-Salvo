package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import cs3500.pa04.model.boardvalues.ShipStatus;
import java.util.Objects;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Coordinate class.
 */
class CoordinateTest {

  /**
   * The constant COORDINATE_ZERO.
   */
  private static final Coordinate COORDINATE_ZERO = new Coordinate(0, 0);
  /**
   * The constant COORDINATE_ONE.
   */
  private static final Coordinate COORDINATE_ONE = new Coordinate(1, 10);
  /**
   * The constant COORDINATE_TWO.
   */
  private static final Coordinate COORDINATE_TWO = new Coordinate(2, 1);

  /**
   * Test hash code.
   */
  @Test
  void testHashCode() {
    assertEquals(Objects.hash(0, 0), CoordinateTest.COORDINATE_ZERO.hashCode());
    assertEquals(Objects.hash(1, 10), CoordinateTest.COORDINATE_ONE.hashCode());
    assertEquals(Objects.hash(2, 1), CoordinateTest.COORDINATE_TWO.hashCode());
  }

  /**
   * Test equals.
   */
  @Test
  void testEquals() {
    assertNotEquals(CoordinateTest.COORDINATE_ZERO, CoordinateTest.COORDINATE_TWO);
    assertNotEquals(CoordinateTest.COORDINATE_ZERO, new Coordinate(0, 1));
    assertNotEquals(CoordinateTest.COORDINATE_ZERO, new Coordinate(1, 0));
    assertNotEquals(CoordinateTest.COORDINATE_ZERO, ShipStatus.NOT_HIT);
    assertNotEquals(null, CoordinateTest.COORDINATE_TWO);
    assertNotEquals(CoordinateTest.COORDINATE_ZERO, null);
    assertEquals(CoordinateTest.COORDINATE_ZERO, new Coordinate(0, 0));
  }

  /**
   * Test get x value.
   */
  @Test
  void testGetXvalue() {
    assertEquals(0, CoordinateTest.COORDINATE_ZERO.xvalue());
    assertEquals(1, CoordinateTest.COORDINATE_ONE.xvalue());
    assertEquals(2, CoordinateTest.COORDINATE_TWO.xvalue());
  }

  /**
   * Test get y value.
   */
  @Test
  void testGetYvalue() {
    assertEquals(0, CoordinateTest.COORDINATE_ZERO.yvalue());
    assertEquals(10, CoordinateTest.COORDINATE_ONE.yvalue());
    assertEquals(1, CoordinateTest.COORDINATE_TWO.yvalue());
  }

  /**
   * Test minimum.
   */
  @Test
  void testMinimum() {
    assertEquals(0, CoordinateTest.COORDINATE_ZERO.minimum());
    assertEquals(1, CoordinateTest.COORDINATE_ONE.minimum());
    assertEquals(1, CoordinateTest.COORDINATE_TWO.minimum());
  }
}