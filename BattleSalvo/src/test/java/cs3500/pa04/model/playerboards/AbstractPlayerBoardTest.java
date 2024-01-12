package cs3500.pa04.model.playerboards;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.boardvalues.BoardValue;
import cs3500.pa04.model.boardvalues.CellState;
import cs3500.pa04.model.boardvalues.ShipStatus;
import cs3500.pa04.model.boardvalues.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the AbstractPlayerBoard class.
 */
class AbstractPlayerBoardTest {

  /**
   * The Board.
   */
  private PlayerBoard board;
  /**
   * The Other board.
   */
  private PlayerBoard otherBoard;

  /**
   * Gets coordinate list.
   *
   * @return the coordinate list
   */
  private static List<Coordinate> getCoordinateList() {
    List<Coordinate> successfulShots = new ArrayList<>();
    successfulShots.add(new Coordinate(0, 0));
    successfulShots.add(new Coordinate(0, 1));
    successfulShots.add(new Coordinate(0, 2));
    successfulShots.add(new Coordinate(0, 3));
    successfulShots.add(new Coordinate(0, 4));
    successfulShots.add(new Coordinate(0, 5));
    successfulShots.add(new Coordinate(1, 0));
    successfulShots.add(new Coordinate(1, 1));
    successfulShots.add(new Coordinate(1, 2));
    successfulShots.add(new Coordinate(1, 3));
    successfulShots.add(new Coordinate(1, 4));
    successfulShots.add(new Coordinate(1, 5));
    successfulShots.add(new Coordinate(2, 0));
    successfulShots.add(new Coordinate(2, 1));
    successfulShots.add(new Coordinate(2, 2));
    successfulShots.add(new Coordinate(2, 3));
    successfulShots.add(new Coordinate(2, 4));
    successfulShots.add(new Coordinate(2, 5));
    successfulShots.add(new Coordinate(3, 0));
    successfulShots.add(new Coordinate(3, 1));
    successfulShots.add(new Coordinate(3, 2));
    successfulShots.add(new Coordinate(3, 3));
    successfulShots.add(new Coordinate(3, 4));
    successfulShots.add(new Coordinate(3, 5));
    successfulShots.add(new Coordinate(4, 0));
    successfulShots.add(new Coordinate(4, 1));
    successfulShots.add(new Coordinate(4, 2));
    successfulShots.add(new Coordinate(4, 3));
    successfulShots.add(new Coordinate(4, 4));
    successfulShots.add(new Coordinate(4, 5));
    successfulShots.add(new Coordinate(5, 0));
    successfulShots.add(new Coordinate(5, 1));
    successfulShots.add(new Coordinate(5, 2));
    return successfulShots;
  }

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    this.board = new ManualPlayerBoard(new Coordinate(6, 6));
    this.otherBoard = new ManualPlayerBoard(new Coordinate(6, 6));
    Ship submarineOne = new Ship(ShipType.SUBMARINE);
    submarineOne.updateLocations(new Coordinate(0, 0));
    submarineOne.updateLocations(new Coordinate(0, 1));
    submarineOne.updateLocations(new Coordinate(0, 2));
    Ship destroyerOne = new Ship(ShipType.DESTROYER);
    destroyerOne.updateLocations(new Coordinate(1, 0));
    destroyerOne.updateLocations(new Coordinate(1, 1));
    destroyerOne.updateLocations(new Coordinate(1, 2));
    destroyerOne.updateLocations(new Coordinate(1, 3));
    Ship battleshipOne = new Ship(ShipType.BATTLESHIP);
    battleshipOne.updateLocations(new Coordinate(2, 0));
    battleshipOne.updateLocations(new Coordinate(2, 1));
    battleshipOne.updateLocations(new Coordinate(2, 2));
    battleshipOne.updateLocations(new Coordinate(2, 3));
    battleshipOne.updateLocations(new Coordinate(2, 4));
    Ship carrierOne = new Ship(ShipType.CARRIER);
    carrierOne.updateLocations(new Coordinate(3, 0));
    carrierOne.updateLocations(new Coordinate(3, 1));
    carrierOne.updateLocations(new Coordinate(3, 2));
    carrierOne.updateLocations(new Coordinate(3, 3));
    carrierOne.updateLocations(new Coordinate(3, 4));
    carrierOne.updateLocations(new Coordinate(3, 5));
    List<Ship> shipList = new ArrayList<>();
    shipList.add(submarineOne);
    shipList.add(destroyerOne);
    shipList.add(battleshipOne);
    shipList.add(carrierOne);
    this.board.setup(shipList);
    this.otherBoard.setup(shipList);
  }

  /**
   * Tear down.
   */
  @AfterEach
  void tearDown() {
    this.board = null;
    assertNull(this.board);
  }

  /**
   * Test abstract player board constructor exceptions.
   */
  @Test
  void testAbstractPlayerBoardConstructorExceptions() {
    assertThrows(IllegalArgumentException.class,
        () -> new ArtificialPlayerBoard(new Coordinate(0, -1)));
    assertThrows(IllegalArgumentException.class,
        () -> new ArtificialPlayerBoard(new Coordinate(-1, 5)));
    assertThrows(IllegalArgumentException.class,
        () -> new ArtificialPlayerBoard(new Coordinate(5, 0)));
  }

  /**
   * Test setup.
   */
  @Test
  void testSetup() {
    BoardValue[][] userBoard = new BoardValue[][]{
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, ShipType.DESTROYER, ShipType.BATTLESHIP, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, ShipType.BATTLESHIP, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY}};
    assertArrayEquals(userBoard, this.board.getUserBoardGrid());
  }

  /**
   * Test update board value.
   */
  @Test
  void testUpdateBoardValue() {
    this.board.updateBoardValue();
    BoardValue[][] userBoard = new BoardValue[][]{
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, ShipType.DESTROYER, ShipType.BATTLESHIP, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, ShipType.BATTLESHIP, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY}};
    assertArrayEquals(userBoard, this.board.getUserBoardGrid());
    this.board.reportDamage(List.of(new Coordinate(0, 0), new Coordinate(5, 5)));
    this.board.updateBoardValue();
    userBoard[0][0] = ShipStatus.HIT;
    userBoard[5][5] = CellState.MISS;
    assertArrayEquals(userBoard, this.board.getUserBoardGrid());
  }

  /**
   * Test successful hits.
   */
  @Test
  void testSuccessfulHits() {
    this.board.addShots(List.of(new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(1, 1)));
    this.board.takeShots();
    this.board.successfulHits(List.of(new Coordinate(0, 0), new Coordinate(1, 1)));
    BoardValue[][] opponentBoard = new BoardValue[][]{
        new BoardValue[]{CellState.HIT, CellState.MISS, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.HIT, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY}};
    assertArrayEquals(opponentBoard, this.board.getOpponentBoardGrid());
  }

  /**
   * Test get opponent board grid.
   */
  @Test
  void testGetOpponentBoardGrid() {
    BoardValue[][] opponentBoard = new BoardValue[][]{
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY}};
    assertArrayEquals(opponentBoard, this.board.getOpponentBoardGrid());
  }

  /**
   * Test get user board grid.
   */
  @Test
  void testGetUserBoardGrid() {
    BoardValue[][] userBoard = new BoardValue[][]{
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, ShipType.DESTROYER, ShipType.BATTLESHIP, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, ShipType.BATTLESHIP, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY}};
    assertArrayEquals(userBoard, this.board.getUserBoardGrid());
  }

  /**
   * Test ships afloat.
   */
  @Test
  void testShipsAfloat() {
    List<Coordinate> opponentShots = new ArrayList<>();
    opponentShots.add(new Coordinate(0, 0));
    opponentShots.add(new Coordinate(5, 5));
    this.board.reportDamage(opponentShots);
    assertEquals(4, this.board.shipsAfloat());
    opponentShots.clear();
    opponentShots.add(new Coordinate(0, 1));
    opponentShots.add(new Coordinate(0, 2));
    this.board.reportDamage(opponentShots);
    assertEquals(3, this.board.shipsAfloat());
  }

  /**
   * Test shots allowed ships.
   */
  @Test
  void testShotsAllowedShips() {
    List<Coordinate> opponentShots = new ArrayList<>();
    opponentShots.add(new Coordinate(0, 0));
    opponentShots.add(new Coordinate(5, 5));
    this.board.reportDamage(opponentShots);
    assertEquals(4, this.board.shotsAllowed());
    opponentShots.clear();
    opponentShots.add(new Coordinate(0, 1));
    opponentShots.add(new Coordinate(0, 2));
    this.board.reportDamage(opponentShots);
    assertEquals(3, this.board.shotsAllowed());
  }

  /**
   * Test shots allowed empty.
   */
  @Test
  void testShotsAllowedEmpty() {
    List<Coordinate> successfulShots = AbstractPlayerBoardTest.getCoordinateList();
    this.board.addShots(successfulShots);
    this.board.takeShots();
    this.board.successfulHits(successfulShots);
    assertEquals(3, this.board.shotsAllowed());
  }

  /**
   * Test take shots.
   */
  @Test
  void testTakeShots() {
    this.board.addShots(List.of(new Coordinate(0, 0), new Coordinate(1, 1)));
    assertEquals(List.of(new Coordinate(0, 0), new Coordinate(1, 1)), this.board.takeShots());
  }

  /**
   * Test equals.
   */
  @Test
  void testEquals() {
    assertEquals(new ManualPlayerBoard(new Coordinate(1, 1)),
        new ManualPlayerBoard(new Coordinate(1, 1)));
    assertNotEquals(null, this.board);
    assertNotEquals(this.board, null);
    assertNotEquals(ShipType.CARRIER, this.board);
    assertNotEquals(new ArtificialPlayerBoard(new Coordinate(1, 1)), this.board);
    this.otherBoard.addShots(List.of(new Coordinate(0, 0)));
    this.otherBoard.takeShots();
    this.otherBoard.successfulHits(List.of(new Coordinate(0, 0)));
    assertNotEquals(this.board, this.otherBoard);
    this.board.addShots(List.of(new Coordinate(0, 0)));
    this.board.takeShots();
    this.board.successfulHits(List.of(new Coordinate(0, 0)));
    assertEquals(this.otherBoard, this.board);
    this.otherBoard.reportDamage(List.of(new Coordinate(0, 0)));
    assertNotEquals(this.otherBoard, this.board);
  }

  /**
   * Test hash code.
   */
  @Test
  void testHashCode() {
    BoardValue[][] opponentBoard = new BoardValue[][]{
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY,
            CellState.EMPTY, CellState.EMPTY, CellState.EMPTY}};
    BoardValue[][] userBoard = new BoardValue[][]{
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{ShipType.SUBMARINE, ShipType.DESTROYER, ShipType.BATTLESHIP,
            ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, ShipType.DESTROYER, ShipType.BATTLESHIP, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, ShipType.BATTLESHIP, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY},
        new BoardValue[]{CellState.EMPTY, CellState.EMPTY, CellState.EMPTY, ShipType.CARRIER,
            CellState.EMPTY, CellState.EMPTY}};
    assertEquals(Objects.hash(Arrays.deepHashCode(userBoard), Arrays.deepHashCode(opponentBoard)),
        this.board.hashCode());
  }
}