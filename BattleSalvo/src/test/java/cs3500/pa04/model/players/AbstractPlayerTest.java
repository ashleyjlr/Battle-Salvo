package cs3500.pa04.model.players;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.mockmodels.MockPlayerBoard;
import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.boardvalues.ShipType;
import cs3500.pa04.model.playerboards.ArtificialPlayerBoard;
import cs3500.pa04.model.playerboards.ManualPlayerBoard;
import cs3500.pa04.model.playerboards.PlayerBoard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the AbstractPlayer class.
 */
class AbstractPlayerTest {

  /**
   * The constant MOCK_BOARD.
   */
  private static final PlayerBoard MOCK_BOARD = new MockPlayerBoard();
  /**
   * The constant MOCKED_PLAYER.
   */
  private static final Player MOCKED_PLAYER = new ManualPlayer("", AbstractPlayerTest.MOCK_BOARD,
      0);

  /**
   * The constant PLAYER_BOARD.
   */
  private static final PlayerBoard PLAYER_BOARD = new ManualPlayerBoard(new Coordinate(6, 8));
  /**
   * The constant MANUAL_PLAYER.
   */
  private static final Player MANUAL_PLAYER = new ManualPlayer("Pastrami",
      AbstractPlayerTest.PLAYER_BOARD, 0);
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
   * Test name.
   */
  @Test
  void testName() {
    assertEquals("Pastrami", AbstractPlayerTest.MANUAL_PLAYER.name());
  }

  /**
   * Test set up.
   */
  @Test
  void testSetUpSmallManual() {
    List<Ship> shipList = new ArrayList<>();
    Ship submarineOne = new Ship(ShipType.SUBMARINE);
    Ship destroyerOne = new Ship(ShipType.DESTROYER);
    Ship destroyerTwo = new Ship(ShipType.DESTROYER);
    Ship battleshipOne = new Ship(ShipType.BATTLESHIP);
    Ship carrierOne = new Ship(ShipType.CARRIER);
    shipList.add(submarineOne);
    shipList.add(battleshipOne);
    shipList.add(destroyerTwo);
    shipList.add(destroyerOne);
    shipList.add(carrierOne);
    Collections.shuffle(shipList, new Random(0));
    ManualPlayer manualPlayer = new ManualPlayer("bob", null, 0);
    manualPlayer.setup(6, 6, this.specifications);
    List<Ship> result = AbstractPlayerTest.MANUAL_PLAYER.setup(6, 6,
        this.specifications);
    assertEquals(shipList.get(0).size(), result.get(0).size());
    assertTrue(result.get(0).coordinateList().stream()
        .allMatch(coordinate -> coordinate.xvalue() == 0));
    assertEquals(shipList.get(1).size(), result.get(2).size());
    assertTrue(result.get(1).coordinateList().stream()
        .allMatch(coordinate -> coordinate.xvalue() == 4));
    assertEquals(shipList.get(2).size(), result.get(1).size());
    assertTrue(result.get(2).coordinateList().stream()
        .allMatch(coordinate -> coordinate.xvalue() == 3));
    assertEquals(shipList.get(3).size(), result.get(3).size());
    assertEquals(shipList.get(4).size(), result.get(4).size());
  }

  /**
   * Test set up.
   */
  @Test
  void testSetUp() {
    ArtificialPlayer aiPlayer = new ArtificialPlayer("bob",
        new ArtificialPlayerBoard(new Coordinate(15, 15), 10), 1);
    assertDoesNotThrow(() -> aiPlayer.setup(15, 15, this.specifications));
  }

  /**
   * Test set up.
   */
  @Test
  void testSetUpSmall() {
    this.specifications.put(ShipType.SUBMARINE, 1);
    this.specifications.put(ShipType.DESTROYER, 1);
    this.specifications.put(ShipType.BATTLESHIP, 1);
    this.specifications.put(ShipType.CARRIER, 3);
    ArtificialPlayer aiPlayer = new ArtificialPlayer("bob",
        new ArtificialPlayerBoard(new Coordinate(6, 6), 5), 5);
    assertDoesNotThrow(() -> aiPlayer.setup(6, 6, this.specifications));
  }

  /**
   * Test set up.
   */
  @Test
  void testSetUpSmallManual2() {
    this.specifications.put(ShipType.SUBMARINE, 1);
    this.specifications.put(ShipType.DESTROYER, 1);
    this.specifications.put(ShipType.BATTLESHIP, 1);
    this.specifications.put(ShipType.CARRIER, 3);
    ManualPlayer manualPlayer = new ManualPlayer("bob", null, 5);
    assertDoesNotThrow(() -> manualPlayer.setup(6, 6, this.specifications));
  }

  /**
   * Test report damage.
   */
  @Test
  void testReportDamage() {
    assertDoesNotThrow(() -> AbstractPlayerTest.MANUAL_PLAYER.reportDamage(List.of()));
    AbstractPlayerTest.PLAYER_BOARD.setup(AbstractPlayerTest.MANUAL_PLAYER.setup(6, 6,
        this.specifications));
    List<Coordinate> opponentShots = new ArrayList<>();
    opponentShots.add(new Coordinate(5, 5));
    opponentShots.add(new Coordinate(0, 0));
    opponentShots.add(new Coordinate(0, 1));
    assertEquals(List.of(new Coordinate(5, 5), new Coordinate(0, 0), new Coordinate(0, 1)),
        AbstractPlayerTest.MANUAL_PLAYER.reportDamage(opponentShots));
    assertThrows(IllegalStateException.class,
        () -> AbstractPlayerTest.MOCKED_PLAYER.reportDamage(opponentShots));
  }

  /**
   * Test successful hits.
   */
  @Test
  void testSuccessfulHits() {
    assertThrows(IllegalStateException.class, () ->
        AbstractPlayerTest.MOCKED_PLAYER.successfulHits(List.of()));
    assertDoesNotThrow(() -> AbstractPlayerTest.MANUAL_PLAYER.successfulHits(List.of()));
  }
}