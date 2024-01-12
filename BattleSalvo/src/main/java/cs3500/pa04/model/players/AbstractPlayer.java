package cs3500.pa04.model.players;

import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.boardvalues.ShipType;
import cs3500.pa04.model.playerboards.PlayerBoard;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Represents an abstract player in the game.
 */
public abstract class AbstractPlayer implements Player {

  /**
   * The constant MAX_ATTEMPTS.
   */
  private static final int MAX_ATTEMPTS = 1000;
  /**
   * The player's name.
   */
  private final String name;
  /**
   * The Random used to place the player's ships.
   */
  private final Random random;
  /**
   * The player's board.
   */
  protected PlayerBoard board;

  /**
   * Instantiates a new Abstract player.
   *
   * @param name  the name of the player
   * @param board the board for the player
   * @param seed  the seed for the random
   */
  AbstractPlayer(String name, PlayerBoard board, long seed) {
    this.name = name;
    this.board = board;
    this.random = new Random(seed);
  }

  /**
   * @param ships      the ships list that exist
   * @param coordinate the coordinate we are checking
   * @return a boolean if there is collision
   */
  private static boolean hasCollision(Collection<? extends Ship> ships, Coordinate coordinate) {
    return ships.stream().anyMatch(ship -> ship.coordinateList().contains(coordinate));
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should appear
   *                       on the board
   * @return the list of ships
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> ships = new ArrayList<>();
    int attemptCount = 0;
    while (attemptCount < AbstractPlayer.MAX_ATTEMPTS) {
      ships.clear();
      boolean isValidPlacement = true;
      for (Map.Entry<ShipType, Integer> entry : new TreeMap<>(specifications).entrySet()) {
        ShipType shipType = entry.getKey();
        int numShips = entry.getValue();
        for (int i = 0; i < numShips; i++) {
          Ship ship = this.generateShip(shipType, height, width, ships);
          if (ship == null) {
            isValidPlacement = false;
            break; // Restart placement attempt
          }
          ships.add(ship);
        }
        if (!isValidPlacement) {
          break; // Restart the outer loop
        }
      }
      if (isValidPlacement) {
        break; // Valid placement found, exit loop
      }
      attemptCount++;
    }
    if (attemptCount >= AbstractPlayer.MAX_ATTEMPTS) {
      return this.setup(height, width, specifications); // Ship setup failed, retry
    }
    this.board.setup(ships);
    return ships;
  }

  private Ship generateShip(ShipType shipType, int height, int width,
      Collection<? extends Ship> existingShips) {
    int shipSize = shipType.getSize();
    boolean isHorizontal = this.random.nextBoolean();
    int startHeight = this.random.nextInt(height - (isHorizontal ? 0 : shipSize - 1));
    int startWidth = this.random.nextInt(width - (isHorizontal ? shipSize - 1 : 0));
    Collection<Coordinate> shipCoords = new ArrayList<>(shipSize);

    for (int j = 0; j < shipSize; j++) {
      int coordHeight = isHorizontal ? startHeight : startHeight + j;
      int coordWidth = isHorizontal ? startWidth + j : startWidth;
      Coordinate coord = new Coordinate(coordWidth, coordHeight);
      if (AbstractPlayer.hasCollision(existingShips, coord)) {
        return null; // Invalid placement, return null
      }
      shipCoords.add(coord);
    }
    Ship ship = new Ship(shipType);
    shipCoords.forEach(ship::updateLocations);
    return ship;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should equal
   * the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coordinate> takeShots() {
    return this.board.takeShots();
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which shots hit a
   * ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a ship
   *     on this board
   */
  @Override
  public List<Coordinate> reportDamage(List<Coordinate> opponentShotsOnBoard) {
    return this.board.reportDamage(opponentShotsOnBoard);
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coordinate> shotsThatHitOpponentShips) {
    this.board.successfulHits(shotsThatHitOpponentShips);
  }

}
