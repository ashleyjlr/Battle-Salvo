package cs3500.pa04.model.players;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.mockmodels.MockPlayerBoard;
import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.boardvalues.ShipType;
import cs3500.pa04.model.playerboards.ManualPlayerBoard;
import cs3500.pa04.model.playerboards.PlayerBoard;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ManualPlayer class.
 */
class ManualPlayerTest {

  /**
   * The constant MANUAL_BOARD.
   */
  private static final PlayerBoard MANUAL_BOARD =
      new ManualPlayerBoard(new Coordinate(6, 6));
  /**
   * The constant MANUAL_PLAYER.
   */
  private static final Player MANUAL_PLAYER = new ManualPlayer("",
      ManualPlayerTest.MANUAL_BOARD, 0);

  /**
   * The constant MOCK_BOARD.
   */
  private static final PlayerBoard MOCK_BOARD = new MockPlayerBoard();
  /**
   * The constant MOCKED_PLAYER.
   */
  private static final Player MOCKED_PLAYER = new ManualPlayer(ManualPlayerTest.MOCK_BOARD,
      0);
  /**
   * The Specifications.
   */
  private final Map<ShipType, Integer> specifications = new EnumMap<>(ShipType.class);


  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    this.specifications.put(ShipType.SUBMARINE, 1);
    this.specifications.put(ShipType.DESTROYER, 2);
    this.specifications.put(ShipType.BATTLESHIP, 1);
    this.specifications.put(ShipType.CARRIER, 1);
  }

  /**
   * Tear down.
   */
  @AfterEach
  void tearDown() {
    this.specifications.clear();
  }

  /**
   * Test take shots.
   */
  @Test
  void testTakeShots() {
    ManualPlayerTest.MANUAL_BOARD.setup(
        ManualPlayerTest.MANUAL_PLAYER.setup(6, 6, this.specifications));
    List<Coordinate> shots = new ArrayList<>();
    shots.add(new Coordinate(1, 0));
    shots.add(new Coordinate(2, 0));
    shots.add(new Coordinate(3, 0));
    shots.add(new Coordinate(4, 0));
    ManualPlayerTest.MANUAL_BOARD.addShots(shots);
    assertEquals(shots, ManualPlayerTest.MANUAL_PLAYER.takeShots());
    assertThrows(IllegalStateException.class, ManualPlayerTest.MOCKED_PLAYER::takeShots);
  }

  /**
   * Test end game.
   */
  @Test
  void testEndGame() {
    assertDoesNotThrow(() -> ManualPlayerTest.MANUAL_PLAYER.endGame(GameResult.WIN, ""));
  }

  /**
   * Test end game.
   */
  @Test
  void testEndGameLose() {
    assertDoesNotThrow(() -> ManualPlayerTest.MANUAL_PLAYER.endGame(GameResult.LOSE, ""));
  }


  /**
   * Test end game.
   */
  @Test
  void testEndGameTie() {
    assertDoesNotThrow(() -> ManualPlayerTest.MANUAL_PLAYER.endGame(GameResult.TIED, ""));
  }

}