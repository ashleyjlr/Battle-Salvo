package cs3500.pa04.model.playerboards;

import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.boardvalues.BoardValue;
import cs3500.pa04.model.boardvalues.CellState;
import cs3500.pa04.model.boardvalues.ShipStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a player's board in the game including the data the user knows about its opponent.
 */
public abstract class AbstractPlayerBoard implements PlayerBoard {

  /**
   * The Recent shots the player has made.
   */
  protected final List<Coordinate> recentShots;
  /**
   * The Opponent board.
   */
  protected final BoardValue[][] opponentBoard;
  /**
   * The User ships.
   */
  private final List<Ship> userShips;
  /**
   * The User board.
   */
  private final BoardValue[][] userBoard;
  /**
   * The Previous shots the user made.
   */
  private final List<Coordinate> previousShots;

  /**
   * Instantiates a new Player board.
   *
   * @param boardSize the board size
   */
  AbstractPlayerBoard(Coordinate boardSize) {
    if (boardSize.xvalue() <= 0 || boardSize.yvalue() <= 0) {
      throw new IllegalArgumentException("Invalid board size");
    }
    this.previousShots = new ArrayList<>();
    this.recentShots = new ArrayList<>();
    this.userShips = new ArrayList<>();
    this.opponentBoard = AbstractPlayerBoard.initializeEmptyBoard(boardSize.yvalue(),
        boardSize.xvalue());
    this.userBoard = AbstractPlayerBoard.initializeEmptyBoard(boardSize.yvalue(),
        boardSize.xvalue());
  }

  /**
   * Initializes the board with empty values based on the given dimensions.
   *
   * @param rows    the rows in the board
   * @param columns the columns in the board
   * @return the board value [ ] [ ] representing an empty board
   */
  private static BoardValue[][] initializeEmptyBoard(int rows, int columns) {
    BoardValue[][] result = new BoardValue[rows][columns];
    for (int y = 0; y < rows; y += 1) {
      for (int x = 0; x < columns; x += 1) {
        result[y][x] = CellState.EMPTY;
      }
    }
    return result;
  }

  private static boolean isHit(Ship ship, Coordinate coordinate,
      Collection<? super Coordinate> result) {
    boolean checkDamage = true;
    if (ship.coordinateList().stream().anyMatch(coordinate1 -> coordinate1.equals(coordinate))) {
      ship.takeHit(coordinate);
      result.add(coordinate);
    } else {
      checkDamage = false;
    }
    return checkDamage;
  }

  /**
   * Counts the number of empty values in the opponent's board.
   *
   * @return the int representing the empty spots in the opponent's board
   */
  private int countOpponentEmpty() {
    return Math.toIntExact(
        Arrays.stream(this.opponentBoard)
            .flatMap(Arrays::stream)
            .filter(cell -> cell.equals(CellState.EMPTY))
            .count()
    );
  }

  /**
   * Sets up the player board with the given list of ships and update the board to show the ships.
   *
   * @param ships the ships the player has on their board
   */
  @Override
  public void setup(List<? extends Ship> ships) {
    this.userShips.addAll(ships);
    this.updateBoardValue();
  }

  /**
   * Given the list of shots the opponent has fired on this player board, reports which shots hit a
   * ship on this board. Updates the user's list of ships and user board based on the damage.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a ship
   *     on this board
   */
  @Override
  public List<Coordinate> reportDamage(List<Coordinate> opponentShotsOnBoard) {
    List<Coordinate> result = new ArrayList<>();
    for (Coordinate coordinate : opponentShotsOnBoard) {
      boolean noHits = this.userShips.stream()
          .noneMatch(ship -> AbstractPlayerBoard.isHit(ship, coordinate, result));
      if (noHits) {
        this.userBoard[coordinate.yvalue()][coordinate.xvalue()] = CellState.MISS;
      }
    }
    this.updateBoardStatus();
    return result;
  }

  /**
   * Reports what shots in the player's previous volley successfully hit the opponent's ship.
   * Updates the opponent's board to show hits and misses.
   *
   * @param shotsThatHitOpponentShips the shots that hit opponent ships
   */
  @Override
  public void successfulHits(List<Coordinate> shotsThatHitOpponentShips) {
    for (Coordinate shot : this.previousShots) {
      if (shotsThatHitOpponentShips.contains(shot)) {
        this.opponentBoard[shot.yvalue()][shot.xvalue()] = CellState.HIT;
      } else {
        this.opponentBoard[shot.yvalue()][shot.xvalue()] = CellState.MISS;
      }
    }
  }

  /**
   * Gets the opponent's board from this board's point of view.
   *
   * @return the board value [ ] [ ] representing the opponent's board
   */
  @Override
  public BoardValue[][] getOpponentBoardGrid() {
    return this.opponentBoard.clone();
  }

  /**
   * Gets the user's board from this board's point of view.
   *
   * @return the board value [ ] [ ] representing the user's board
   */
  @Override
  public BoardValue[][] getUserBoardGrid() {
    return this.userBoard.clone();
  }

  /**
   * Determines how many of the user's ships are not sunk.
   *
   * @return the int representing the number of ships a float
   */
  @Override
  public int shipsAfloat() {
    return (int) this.userShips.stream().filter(Ship::isNotSunk).count();
  }

  /**
   * Determines how many shots the user is allowed to take. If the number of empty spots on the
   * opponent board is less than the number of ships a float, then the smaller number is used.
   *
   * @return the int representing the number of allowed shots
   */
  @Override
  public int shotsAllowed() {
    return Math.min(this.shipsAfloat(), this.countOpponentEmpty());
  }

  /**
   * Updates the previous shots to be the recent shots taken, and clears the list of previous shots.
   * The shots taken is then the contents of the previous shots since they have been "taken."
   *
   * @return the list of shots taken
   */
  @Override
  public List<Coordinate> takeShots() {
    this.previousShots.clear();
    this.previousShots.addAll(this.recentShots);
    this.recentShots.clear();
    return Collections.unmodifiableList(this.previousShots);
  }

  /**
   * Updates the user board to show the status of ships based on the list of user ships.
   */
  private void updateBoardStatus() {
    for (Ship ship : this.userShips) {
      ship.coordinateList().forEach((Coordinate coordinate) ->
          this.userBoard[coordinate.yvalue()][coordinate.xvalue()] = ship.statusAt(
              coordinate));
    }
  }

  /**
   * Updates the user board to show the ship type for ships that have not been hit based on the list
   * of user ships.
   */
  @Override
  public void updateBoardValue() {
    for (Ship ship : this.userShips) {
      ship.coordinateList().stream()
          .filter(coordinate ->
              this.userBoard[coordinate.yvalue()][coordinate.xvalue()] != ShipStatus.HIT)
          .forEach(coordinate ->
              this.userBoard[coordinate.yvalue()][coordinate.xvalue()] = ship.getType());
    }
  }

  /**
   * Determines if a given obj and this player board are equal based on their boards.
   *
   * @param obj the object this player board is being compared to
   * @return whether the given object and this player board are equal
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = false;
    if (obj != null) {
      if (this.getClass() == obj.getClass()) {
        PlayerBoard other = (PlayerBoard) obj;
        result = Arrays.deepEquals(this.userBoard, other.getUserBoardGrid()) && Arrays.deepEquals(
            this.opponentBoard, other.getOpponentBoardGrid());
      }
    }
    return result;
  }

  /**
   * Returns the hashCode for this player board that's used for bucketing in Hash implementations.
   *
   * @return hash generated by the hashing algorithm
   */
  @Override
  public int hashCode() {
    return Objects.hash(Arrays.deepHashCode(this.userBoard),
        Arrays.deepHashCode(this.opponentBoard));
  }

}
