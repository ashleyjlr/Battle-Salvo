package cs3500.pa04.model.playerboards;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Coordinate;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ManualPlayerBoard class.
 */
class ManualPlayerBoardTest {

  /**
   * The constant PLAYER_BOARD.
   */
  private static final PlayerBoard PLAYER_BOARD = new ManualPlayerBoard(new Coordinate(6, 6));

  /**
   * Test add shots.
   */
  @Test
  void testAddShots() {
    ManualPlayerBoardTest.PLAYER_BOARD.addShots(List.of(new Coordinate(0, 0)));
    assertEquals(List.of(new Coordinate(0, 0)), ManualPlayerBoardTest.PLAYER_BOARD.takeShots());
  }
}