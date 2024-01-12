package cs3500.pa04.model.players;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.mockmodels.MockPlayerBoard;
import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.boardvalues.ShipType;
import cs3500.pa04.model.playerboards.ArtificialPlayerBoard;
import cs3500.pa04.model.playerboards.PlayerBoard;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ArtificialPlayer class.
 */
class ArtificialPlayerTest {

  /**
   * The constant ARTIFICIAL_BOARD.
   */
  private static final PlayerBoard ARTIFICIAL_BOARD =
      new ArtificialPlayerBoard(new Coordinate(6, 6), 0);
  /**
   * The constant ARTIFICIAL_PLAYER.
   */
  private static final Player ARTIFICIAL_PLAYER = new ArtificialPlayer("",
      ArtificialPlayerTest.ARTIFICIAL_BOARD, 0);
  /**
   * The constant MOCK_BOARD.
   */
  private static final PlayerBoard MOCK_BOARD = new MockPlayerBoard();
  /**
   * The constant MOCKED_PLAYER.
   */
  private static final Player MOCKED_PLAYER = new ArtificialPlayer(ArtificialPlayerTest.MOCK_BOARD,
      0);
  /**
   * The constant LINE_SEPARATOR.
   */
  private static final String LINE_SEPARATOR = System.lineSeparator();
  /**
   * The Specifications.
   */
  private final Map<ShipType, Integer> specifications = new EnumMap<>(ShipType.class);
  /**
   * The Expected output.
   */
  private StringJoiner expectedOutput;
  /**
   * The Out content.
   */
  private ByteArrayOutputStream outContent;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    this.specifications.put(ShipType.SUBMARINE, 1);
    this.specifications.put(ShipType.DESTROYER, 2);
    this.specifications.put(ShipType.BATTLESHIP, 1);
    this.specifications.put(ShipType.CARRIER, 1);
    this.expectedOutput = new StringJoiner(ArtificialPlayerTest.LINE_SEPARATOR);
    this.outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(this.outContent, true, StandardCharsets.UTF_8));
  }

  /**
   * Tear down.
   */
  @AfterEach
  void tearDown() {
    this.specifications.clear();
    this.expectedOutput = null;
    this.outContent.reset();
    assertNull(this.expectedOutput);
    System.setOut(System.out);
  }

  /**
   * Test take shots.
   */
  @Test
  void testTakeShots() {
    ArtificialPlayerTest.ARTIFICIAL_PLAYER.setup(6, 6, this.specifications);
    Collection<Coordinate> shots = new ArrayList<>();
    shots.add(new Coordinate(1, 2));
    shots.add(new Coordinate(1, 4));
    shots.add(new Coordinate(0, 1));
    shots.add(new Coordinate(5, 0));
    shots.add(new Coordinate(0, 5));
    assertEquals(shots, ArtificialPlayerTest.ARTIFICIAL_PLAYER.takeShots());
    assertThrows(IllegalStateException.class, ArtificialPlayerTest.MOCKED_PLAYER::takeShots);
  }

  /**
   * Test end game.
   */
  @Test
  void testEndGame() {
    this.expectedOutput.add("You won!");
    this.expectedOutput.add("");
    assertDoesNotThrow(
        () -> ArtificialPlayerTest.ARTIFICIAL_PLAYER.endGame(GameResult.WIN, "You won!"));
    assertEquals(this.expectedOutput.toString(), this.outContent.toString());
  }

  /**
   * Test end game.
   */
  @Test
  void testEndGameInvalid() {
    assertThrows(IllegalArgumentException.class,
        () -> ArtificialPlayerTest.ARTIFICIAL_PLAYER.endGame(GameResult.INPROGRESS, ""));
    assertThrows(IllegalArgumentException.class,
        () -> ArtificialPlayerTest.ARTIFICIAL_PLAYER.endGame(null, ""));
  }

}