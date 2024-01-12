package cs3500.pa04.controller;

import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.boardvalues.BoardValue;
import cs3500.pa04.model.boardvalues.ShipType;
import cs3500.pa04.model.playerboards.ArtificialPlayerBoard;
import cs3500.pa04.model.playerboards.ManualPlayerBoard;
import cs3500.pa04.model.playerboards.PlayerBoard;
import cs3500.pa04.model.players.ArtificialPlayer;
import cs3500.pa04.model.players.ManualPlayer;
import cs3500.pa04.model.players.Player;
import cs3500.pa04.view.TerminalUserView;
import cs3500.pa04.view.UserView;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents the controller for a manual game based on user input from the console.
 */
public class ManualGameController implements GameController {

  /**
   * The User view.
   */
  private final UserView userView;
  /**
   * The User input handler.
   */
  private final UserInputHandler userInputHandler;

  /**
   * The User.
   */
  private final Player user;
  /**
   * The Opponent.
   */
  private final Player opponent;

  /**
   * The User board.
   */
  private final PlayerBoard userBoard;
  /**
   * The Opponent board.
   */
  private final PlayerBoard opponentBoard;
  /**
   * The Board size as set by the user.
   */
  private final Coordinate boardSize;

  /**
   * Instantiates a new controller with the default System.in, System.in, and a random seed based on
   * the system time.
   */
  public ManualGameController() {
    this(new InputStreamReader(System.in, StandardCharsets.UTF_8),
        new PrintStream(System.out, true, StandardCharsets.UTF_8),
        System.currentTimeMillis());
  }

  /**
   * Instantiates a new Manual game controller using the given input, output, and seed.
   *
   * @param input  the input
   * @param output the output
   * @param seed   the seed used to place ships
   */
  ManualGameController(Reader input, Appendable output, long seed) {
    this.userView = new TerminalUserView(input, output);
    this.userInputHandler = new UserInputHandler(this.userView);
    GameSetup gameSetup = new ManualGameSetup(this.userView, this.userInputHandler);
    this.boardSize = gameSetup.setupBoardSize();
    this.userBoard = new ManualPlayerBoard(this.boardSize);
    this.opponentBoard = new ArtificialPlayerBoard(this.boardSize, seed);
    this.user = new ManualPlayer(this.userBoard, Long.reverse(seed));
    Map<ShipType, Integer> fleetSize = gameSetup.setupFleetSize();
    this.user.setup(this.boardSize.yvalue(), this.boardSize.xvalue(), fleetSize);
    this.opponent = new ArtificialPlayer(this.opponentBoard, seed);
    this.opponent.setup(this.boardSize.yvalue(), this.boardSize.xvalue(), fleetSize);
  }

  /**
   * Runs the game until the user has no more shots. If it's the end, results are shown including
   * where the opponent's ships were.
   */
  @Override
  public void run() {
    this.updateViewBoards(this.userBoard.getOpponentBoardGrid());
    while (!this.shouldRunShots()) {
      this.updateViewBoards(this.userBoard.getOpponentBoardGrid());
    }
    this.userView.showResult(this.determineGameResult());
    this.userBoard.updateBoardValue();
    this.opponentBoard.updateBoardValue();
    this.updateViewBoards(this.opponentBoard.getUserBoardGrid());
  }

  /**
   * Updates the view to show the user board and the opponent board.
   *
   * @param opponentBoard the opponent board to show
   */
  private void updateViewBoards(BoardValue[][] opponentBoard) {
    this.userView.showBoards(this.userBoard.getUserBoardGrid(), opponentBoard);
  }

  /**
   * Determines if the game should continue running the shots flow by running the shots flow since
   * the ending of the flow is based on the results of the flow.
   *
   * @return the boolean representing whether the shots flow should continue
   */
  private boolean shouldRunShots() {
    this.runSetupUserShots();
    List<Coordinate> userShots = this.user.takeShots();
    List<Coordinate> opponentShots = this.opponent.takeShots();
    List<Coordinate> opponentDamage = this.opponent.reportDamage(userShots);
    List<Coordinate> userDamage = this.user.reportDamage(opponentShots);
    this.user.successfulHits(opponentDamage);
    this.opponent.successfulHits(userDamage);
    return this.userBoard.shipsAfloat() == 0 || this.opponentBoard.shipsAfloat() == 0;
  }

  /**
   * Determines the result of the game based on the number of players' ships afloat.
   *
   * @return the game result
   */
  private GameResult determineGameResult() {
    GameResult result;
    if (this.userBoard.shipsAfloat() > this.opponentBoard.shipsAfloat()) {
      result = GameResult.WIN;
    } else if (this.userBoard.shipsAfloat() < this.opponentBoard.shipsAfloat()) {
      result = GameResult.LOSE;
    } else {
      result = GameResult.TIED;
    }
    return result;
  }

  /**
   * Runs the setup for the user's shots. Checks that the user inputs are valid shots and asks them
   * to re-enter any. If a shot is deemed invalid by the handler (ex. too few arguments or invalid
   * type) the user is prompted to re-enter that shot.
   */
  private void runSetupUserShots() {
    List<Coordinate> shots = new ArrayList<>();
    while (shots.size() < this.userBoard.shotsAllowed()) {
      int[] userInput;
      if (shots.isEmpty()) {
        userInput = this.userInputHandler.updateViewAskAndReceive(
            "Please Enter " + this.userBoard.shotsAllowed() + " Shots:", 2);
      } else {
        userInput = this.userInputHandler.updateViewAskAndReceive();
      }
      if (this.isValidShot(userInput)) {
        shots.add(new Coordinate(userInput[0], userInput[1]));
      } else {
        shots.clear();
        this.userView.showMessage(
            "Uh Oh! You've entered invalid shots. Please remember that the;size"
                + " of the board is [" + this.boardSize.xvalue() + ", "
                + this.boardSize.yvalue()
                + "], inclusive. Coordinates start at 0;from the top left. Try again!");
      }
    }
    this.userBoard.addShots(shots);
  }

  /**
   * Determines if a given input of an int array is a valid shot based on the board size.
   *
   * @param userInput the user input representing the coordinates of a shot
   * @return the boolean representing if the input represents a valid shot
   */
  private boolean isValidShot(int[] userInput) {
    return userInput[0] < this.boardSize.xvalue() && userInput[1] < this.boardSize.yvalue()
        && userInput[0] >= 0 && userInput[1] >= 0;
  }
}
