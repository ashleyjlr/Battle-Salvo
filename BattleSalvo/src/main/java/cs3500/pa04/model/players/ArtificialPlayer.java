package cs3500.pa04.model.players;

import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.boardvalues.ShipType;
import cs3500.pa04.model.playerboards.ArtificialPlayerBoard;
import cs3500.pa04.model.playerboards.PlayerBoard;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents an artificial player of the game.
 */
public class ArtificialPlayer extends AbstractPlayer {

  /**
   * Instantiates a new Artificial player.
   *
   * @param name  the name for the player
   * @param board the board for the player
   * @param seed  the seed for the random used to place ships
   */
  public ArtificialPlayer(String name, PlayerBoard board, long seed) {
    super(name, board, seed);
  }

  /**
   * Instantiates a new Artificial player with a default name.
   *
   * @param board the board for the player
   * @param seed  the seed for the random used to place ships
   */
  public ArtificialPlayer(PlayerBoard board, long seed) {
    super("", board, seed);
  }

  /**
   * Notifies the player that the game is over. Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    if (result == GameResult.INPROGRESS || !Arrays.stream(GameResult.values()).toList()
        .contains(result)) {
      throw new IllegalArgumentException("Invalid game result: " + result);
    }
    System.out.println(reason);
  }


  /**
   * Sets up the board with a list of ships in their respective positions. Given a list of
   * specifications and the dimensions of the board, places the correct amount of each type of ship
   * at coordinates on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should appear
   *                       on the board
   * @return the list of ships with their coordinates on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    if (Objects.isNull(this.board)) {
      this.board = new ArtificialPlayerBoard(new Coordinate(width, height));
    }
    return super.setup(height, width, specifications);
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should equal
   * the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coordinate> takeShots() {
    this.board.addShots(List.of());
    return super.takeShots();
  }
}
