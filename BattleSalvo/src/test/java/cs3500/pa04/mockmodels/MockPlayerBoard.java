package cs3500.pa04.mockmodels;

import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.boardvalues.BoardValue;
import cs3500.pa04.model.playerboards.PlayerBoard;
import java.util.List;

/**
 * MockPlayerBoard for testing IOExceptions.
 */
public class MockPlayerBoard implements PlayerBoard {

  /**
   * Updates the user board to show the ship type for ships that have not been hit based on the list
   * of user ships.
   */
  @Override
  public void updateBoardValue() {
    throw new IllegalStateException("Throwing a mock exception");
  }

  /**
   * Sets up the player board with the given list of ships and update the board to show the ships.
   *
   * @param ships the ships the player has on their board
   */
  @Override
  public void setup(List<? extends Ship> ships) {
    throw new IllegalStateException("Throwing a mock exception");
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
    throw new IllegalStateException("Throwing a mock exception");
  }

  /**
   * Reports what shots in the player's previous volley successfully hit the opponent's ship.
   * Updates the opponent's board to show hits and misses.
   *
   * @param shotsThatHitOpponentShips the shots that hit opponent ships
   */
  @Override
  public void successfulHits(List<Coordinate> shotsThatHitOpponentShips) {
    throw new IllegalStateException("Throwing a mock exception");
  }

  /**
   * Gets the opponent's board from this board's point of view.
   *
   * @return the board value [ ] [ ] representing the opponent's board
   */
  @Override
  public BoardValue[][] getOpponentBoardGrid() {
    throw new IllegalStateException("Throwing a mock exception");
  }

  /**
   * Gets the user's board from this board's point of view.
   *
   * @return the board value [ ] [ ] representing the user's board
   */
  @Override
  public BoardValue[][] getUserBoardGrid() {
    throw new IllegalStateException("Throwing a mock exception");
  }

  /**
   * Determines how many of the user's ships are not sunk.
   *
   * @return the int representing the number of ships a float
   */
  @Override
  public int shipsAfloat() {
    throw new IllegalStateException("Throwing a mock exception");
  }

  /**
   * Determines how many shots the user is allowed to take. If the number of empty spots on the
   * opponent board is less than the number of ships a float, then the smaller number is used.
   *
   * @return the int representing the number of allowed shots
   */
  @Override
  public int shotsAllowed() {
    throw new IllegalStateException("Throwing a mock exception");
  }

  /**
   * Updates the list of recent shots with new shots.
   *
   * @param shots the shots that could be added to the recent shots
   */
  @Override
  public void addShots(List<Coordinate> shots) {
    throw new IllegalStateException("Throwing a mock exception");
  }

  /**
   * Updates the previous shots to be the recent shots taken, and clears the list of previous shots.
   * The shots taken is then the contents of the previous shots since they have been "taken."
   *
   * @return the list of shots taken
   */
  @Override
  public List<Coordinate> takeShots() {
    throw new IllegalStateException("Throwing a mock exception");
  }
}
