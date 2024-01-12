package cs3500.pa04.model.playerboards;

import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.boardvalues.BoardValue;
import cs3500.pa04.model.boardvalues.CellState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Represents an artificial player's board in the game.
 */
public class ArtificialPlayerBoard extends AbstractPlayerBoard {

  /**
   * The Random.
   */
  private final Random random;
  /**
   * The Available shots.
   */
  private final List<Coordinate> availableShots = new ArrayList<>();
  /**
   * The Potential targets.
   */
  private final List<Coordinate> potentialTargets = new ArrayList<>();

  /**
   * Instantiates a new Player board based on the given size. Passes in the current system time as
   * the random seed.
   *
   * @param boardSize the board size
   */
  public ArtificialPlayerBoard(Coordinate boardSize) {
    this(boardSize, System.currentTimeMillis());
  }

  /**
   * Instantiates a new Artificial player board with the given seed for the random.
   *
   * @param boardSize the board size
   * @param seed      the seed for the random to take shots
   */
  public ArtificialPlayerBoard(Coordinate boardSize, long seed) {
    super(boardSize);
    this.random = new Random(seed);
  }

  /**
   * Find the coordinates of values in the board with the given state.
   *
   * @param board the board
   * @param state the state
   * @return the list of coordinates corresponding to the given states in the board
   */
  private static List<Coordinate> findValuesWithState(BoardValue[][] board, CellState state) {
    List<Coordinate> opponentHits = new ArrayList<>();
    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[y].length; x++) {
        if (board[y][x] == state) {
          opponentHits.add(new Coordinate(x, y));
        }
      }
    }
    return opponentHits;
  }

  /**
   * Adds shots to the recent shots list using a hunt (with a parity) and target strategy.
   *
   * @param shots the shots that are ignored by the artificial player board
   */
  @Override
  public void addShots(List<Coordinate> shots) {
    this.updateTargetLists();
    for (int i = 0; i < this.shotsAllowed(); i++) {
      List<Coordinate> availableParityShots = this.availableShots.stream()
          .filter(coordinate -> (coordinate.xvalue() % 2 != 0) == (coordinate.yvalue() % 2 == 0))
          .toList();
      Coordinate shot;
      if (this.potentialTargets.isEmpty()) {
        // Hunt
        shot = availableParityShots.isEmpty()
            ? this.availableShots.get(this.random.nextInt(this.availableShots.size()))
            : availableParityShots.get(this.random.nextInt(availableParityShots.size()));
      } else {
        // Target
        shot = this.potentialTargets.get(0);
      }
      this.checkAndAdd(this.recentShots, shot);
      this.potentialTargets.remove(shot);
      this.availableShots.remove(shot);
    }
  }

  /**
   * Updates the available shots list, previousHits, and potential targets lists..
   */
  private void updateTargetLists() {
    this.availableShots.clear();
    this.availableShots.addAll(
        ArtificialPlayerBoard.findValuesWithState(this.opponentBoard, CellState.EMPTY).stream()
            .filter(coordinate -> !this.recentShots.contains(coordinate))
            .toList());
    List<Coordinate> previousHits = ArtificialPlayerBoard.findValuesWithState(this.opponentBoard,
        CellState.HIT);
    for (Coordinate coordinate : previousHits) {
      this.checkAndAdd(this.potentialTargets,
          new Coordinate(coordinate.xvalue() + 1, coordinate.yvalue()));
      this.checkAndAdd(this.potentialTargets,
          new Coordinate(coordinate.xvalue() - 1, coordinate.yvalue()));
      this.checkAndAdd(this.potentialTargets,
          new Coordinate(coordinate.xvalue(), coordinate.yvalue() + 1));
      this.checkAndAdd(this.potentialTargets,
          new Coordinate(coordinate.xvalue(), coordinate.yvalue() - 1));
    }
  }

  /**
   * Checks if the given coordinate is in the available shots and not in the given list. If it meets
   * the criteria, the coordinate is then added to the given list.
   *
   * @param coordinateList the coordinate list
   * @param coordinate     the coordinate
   */
  private void checkAndAdd(Collection<? super Coordinate> coordinateList, Coordinate coordinate) {
    if (this.availableShots.contains(coordinate) && !coordinateList.contains(coordinate)) {
      coordinateList.add(coordinate);
      System.err.println(
          "this.userInput.add(\"" + coordinate.xvalue() + ' ' + coordinate.yvalue() + "\");");
    }
  }
}
