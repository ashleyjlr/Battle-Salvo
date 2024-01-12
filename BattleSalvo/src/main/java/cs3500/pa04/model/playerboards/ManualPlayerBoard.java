package cs3500.pa04.model.playerboards;

import cs3500.pa04.model.Coordinate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a manual player's board in the game.
 */
public class ManualPlayerBoard extends AbstractPlayerBoard {

  /**
   * Instantiates a new Player board based on the given size.
   *
   * @param boardSize the board size
   */
  public ManualPlayerBoard(Coordinate boardSize) {
    super(boardSize);
  }

  /**
   * Updates the list of recent shots with new shots.
   *
   * @param shots the shots to add to the user's recent shots
   */
  @Override
  public void addShots(List<Coordinate> shots) {
    this.recentShots.addAll(new ArrayList<>(shots));
  }
}
