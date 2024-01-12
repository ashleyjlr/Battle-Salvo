package cs3500.pa04.model.playerboards;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.boardvalues.ShipType;
import cs3500.pa04.model.players.ArtificialPlayer;
import cs3500.pa04.model.players.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ArtificialPlayerBoard class.
 */
class ArtificialPlayerBoardTest {

  /**
   * The constant PLAYER_BOARD.
   */
  private static final PlayerBoard PLAYER_BOARD = new ArtificialPlayerBoard(new Coordinate(6, 6),
      0);
  /**
   * The constant ARTIFICIAL_PLAYER.
   */
  private static final Player ARTIFICIAL_PLAYER = new ArtificialPlayer("",
      ArtificialPlayerBoardTest.PLAYER_BOARD, 0);

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
   * Test add shots.
   */
  @Test
  void testAddShots() {
    ArtificialPlayerBoardTest.ARTIFICIAL_PLAYER.setup(6, 6, this.specifications);
    ArtificialPlayerBoardTest.PLAYER_BOARD.addShots(List.of());
    Collection<Coordinate> shots = new ArrayList<>();
    shots.add(new Coordinate(1, 2));
    shots.add(new Coordinate(1, 4));
    shots.add(new Coordinate(0, 1));
    shots.add(new Coordinate(5, 0));
    shots.add(new Coordinate(0, 5));
    List<Coordinate> shotsTaken = ArtificialPlayerBoardTest.PLAYER_BOARD.takeShots();
    assertEquals(shots, shotsTaken);
    ArtificialPlayerBoardTest.PLAYER_BOARD.successfulHits(shotsTaken);
    ArtificialPlayerBoardTest.PLAYER_BOARD.addShots(List.of());
    ArtificialPlayerBoardTest.PLAYER_BOARD.addShots(List.of());
    shotsTaken = ArtificialPlayerBoardTest.PLAYER_BOARD.takeShots();
    shots.clear();
    shots.add(new Coordinate(4, 0));
    shots.add(new Coordinate(5, 1));
    shots.add(new Coordinate(1, 1));
    shots.add(new Coordinate(0, 2));
    shots.add(new Coordinate(0, 0));
    shots.add(new Coordinate(2, 2));
    shots.add(new Coordinate(1, 3));
    shots.add(new Coordinate(2, 4));
    shots.add(new Coordinate(0, 4));
    shots.add(new Coordinate(1, 5));
    assertEquals(shots, shotsTaken);
  }
}