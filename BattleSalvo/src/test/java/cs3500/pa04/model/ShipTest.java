package cs3500.pa04.model;

import static cs3500.pa04.json.datadefinitions.Direction.HORIZONTAL;
import static cs3500.pa04.json.datadefinitions.Direction.VERTICAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.boardvalues.ShipStatus;
import cs3500.pa04.model.boardvalues.ShipType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ship class.
 */
class ShipTest {

  /**
   * The Submarine.
   */
  private Ship submarine;
  /**
   * The Destroyer.
   */
  private Ship destroyer;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    this.submarine = new Ship(ShipType.SUBMARINE);
    this.destroyer = new Ship(ShipType.DESTROYER);
  }

  /**
   * Tear down.
   */
  @AfterEach
  void tearDown() {
    this.submarine = null;
    this.destroyer = null;
    assertNull(this.submarine);
    assertNull(this.destroyer);
  }

  /**
   * Test update locations.
   */
  @Test
  void testUpdateLocations() {
    this.submarine.updateLocations(new Coordinate(0, 0));
    this.submarine.updateLocations(new Coordinate(1, 0));
    this.submarine.updateLocations(new Coordinate(0, 1));
    assertEquals(List.of(new Coordinate(1, 0), new Coordinate(0, 0), new Coordinate(0, 1)),
        this.submarine.coordinateList());
    assertThrows(IllegalStateException.class,
        () -> this.submarine.updateLocations(new Coordinate(1, 1)));
  }

  /**
   * Test is not sunk.
   */
  @Test
  void testIsNotSunk() {
    this.submarine.updateLocations(new Coordinate(0, 0));
    this.submarine.updateLocations(new Coordinate(1, 0));
    assertTrue(this.submarine.isNotSunk());
    this.submarine.takeHit(new Coordinate(0, 0));
    assertTrue(this.submarine.isNotSunk());
    this.submarine.takeHit(new Coordinate(1, 0));
    assertFalse(this.submarine.isNotSunk());
  }

  /**
   * Test to string.
   */
  @Test
  void testToString() {
    assertEquals("S", this.submarine.toString());
    assertEquals("D", this.destroyer.toString());
  }

  /**
   * Test size.
   */
  @Test
  void testSize() {
    assertEquals(3, this.submarine.size());
    assertEquals(4, this.destroyer.size());
  }

  /**
   * Test coordinate list.
   */
  @Test
  void testCoordinateList() {
    assertEquals(new ArrayList<>(), this.submarine.coordinateList());
    this.submarine.updateLocations(new Coordinate(0, 0));
    this.submarine.updateLocations(new Coordinate(1, 0));
    this.submarine.updateLocations(new Coordinate(0, 1));
    assertEquals(List.of(new Coordinate(1, 0), new Coordinate(0, 0), new Coordinate(0, 1)),
        this.submarine.coordinateList());
  }

  /**
   * Test status at.
   */
  @Test
  void testStatusAt() {
    this.submarine.updateLocations(new Coordinate(0, 0));
    this.submarine.updateLocations(new Coordinate(1, 0));
    this.submarine.takeHit(new Coordinate(0, 0));
    assertEquals(ShipStatus.NOT_HIT, this.submarine.statusAt(new Coordinate(1, 0)));
    assertEquals(ShipStatus.HIT, this.submarine.statusAt(new Coordinate(0, 0)));
  }

  /**
   * Test take hit.
   */
  @Test
  void testTakeHit() {
    this.submarine.updateLocations(new Coordinate(0, 0));
    this.submarine.takeHit(new Coordinate(0, 0));
    assertEquals(ShipStatus.HIT, this.submarine.statusAt(new Coordinate(0, 0)));
  }

  /**
   * Test get type.
   */
  @Test
  void testGetType() {
    assertEquals(ShipType.SUBMARINE, this.submarine.getType());
    assertEquals(ShipType.DESTROYER, this.destroyer.getType());
  }

  /**
   * Test get starting coord
   */
  @Test
  void testGetStartCoordHorizontal() {
    this.submarine.updateLocations(new Coordinate(0, 0));
    this.submarine.updateLocations(new Coordinate(1, 0));
    this.submarine.updateLocations(new Coordinate(2, 0));

    assertEquals(new Coordinate(0, 0), this.submarine.getStartCoordinate());
  }

  /**
   * Test get starting coord
   */
  @Test
  void testGetStartCoordVertical() {
    this.submarine.updateLocations(new Coordinate(5, 2));
    this.submarine.updateLocations(new Coordinate(5, 0));
    this.submarine.updateLocations(new Coordinate(5, 1));

    assertEquals(new Coordinate(5, 0), this.submarine.getStartCoordinate());
  }

  /**
   * Test get direction
   */
  @Test
  void testGetDirectionHorizontal() {
    this.submarine.updateLocations(new Coordinate(0, 0));
    this.submarine.updateLocations(new Coordinate(1, 0));
    this.submarine.updateLocations(new Coordinate(2, 0));

    assertEquals(HORIZONTAL, this.submarine.getDirection());
  }

  /**
   * Test get direction
   */
  @Test
  void testGetDirectionVertical() {
    this.submarine.updateLocations(new Coordinate(5, 2));
    this.submarine.updateLocations(new Coordinate(5, 0));
    this.submarine.updateLocations(new Coordinate(5, 1));

    assertEquals(VERTICAL, this.submarine.getDirection());
  }
}