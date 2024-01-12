package cs3500.pa04.controller;

import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.boardvalues.ShipType;
import cs3500.pa04.view.UserView;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Setups up the board size and fleet size for the user based on manual input.
 */
class ManualGameSetup implements GameSetup {

  /**
   * The User view.
   */
  private final UserView userView;
  /**
   * The Max board size the game allows.
   */
  private final Coordinate maxBoardSize = new Coordinate(6, 15);
  /**
   * The Board size the user inputs.
   */
  private final Coordinate boardSize;
  /**
   * The User input handler.
   */
  private final UserInputHandler userInputHandler;

  /**
   * Instantiates a new Game setup.
   *
   * @param userView         the user view
   * @param userInputHandler the user input handler
   */
  ManualGameSetup(UserView userView, UserInputHandler userInputHandler) {
    this.userView = userView;
    this.userInputHandler = userInputHandler;
    this.boardSize = this.runSetupBoardSize();
  }

  /**
   * Determines if the user's input is an invalid fleet size based on the max fleet size.
   *
   * @param userInput    the user input as a map between ShipType and Integer
   * @param maxFleetSize the max fleet size
   * @return the boolean representing if it's an invalid fleet size
   */
  private static boolean isInvalidFleetSize(Map<ShipType, Integer> userInput, int maxFleetSize) {
    return userInput.size() != ShipType.values().length || !userInput.values().stream()
        .allMatch((Integer num) -> num > 0)
        || userInput.values().stream().mapToInt(Integer::intValue).sum() > maxFleetSize;
  }

  /**
   * Runs the setup of the board size coordinate using user input. Includes checks that the user
   * enters a valid board size.
   *
   * @return the coordinate representing the board size
   */
  private Coordinate runSetupBoardSize() {
    this.userView.showMessage("Hello! Welcome to the OOD BattleSalvo Game!");
    Coordinate result = null;
    while (Objects.isNull(result)) {
      int[] userInput = this.userInputHandler.updateViewAskAndReceive(
          "Please enter a valid height and width below:",
          2);
      if (this.isInvalidBoardSize(userInput)) {
        this.userView.showMessage(
            "Uh Oh! You've entered invalid dimensions. Please remember that the;height and width"
                + " of the game must be in the range [6, 15], inclusive. Try again!");
      } else {
        result = new Coordinate(userInput[1], userInput[0]);
      }
    }
    return result;
  }

  /**
   * Determines if the given user input is an invalid board size.
   *
   * @param userInput the user input as an array of ints
   * @return the boolean representing if the arrays represents an invalid board size
   */
  private boolean isInvalidBoardSize(int[] userInput) {
    return Arrays.stream(userInput)
        .anyMatch(
            (int n) -> n < this.maxBoardSize.xvalue() || n > this.maxBoardSize.yvalue());
  }

  /**
   * Gets the board size.
   *
   * @return the board size
   */
  @Override
  public Coordinate setupBoardSize() {
    return this.boardSize;
  }

  /**
   * Sets up the fleet size based on user input.
   *
   * @return the map between ship type to integer representing the amount of each type of ship
   */
  @Override
  public Map<ShipType, Integer> setupFleetSize() {
    return this.runSetupFleetSize(this.boardSize.minimum());
  }

  /**
   * Runs the setup for the fleet size based on the maximum fleet size. Checks that the given input
   * as a valid fleet size.
   *
   * @param maxFleetSize the max fleet size
   * @return the map representing the fleet size
   */
  private Map<ShipType, Integer> runSetupFleetSize(int maxFleetSize) {
    String setup =
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].;"
            + "Remember, your fleet may not exceed size "
            + maxFleetSize + '.';
    Map<ShipType, Integer> result = new EnumMap<>(ShipType.class);
    while (ManualGameSetup.isInvalidFleetSize(result, maxFleetSize)) {
      List<ShipType> keys = Arrays.stream(ShipType.values()).toList();
      List<Integer> values = Arrays.stream(
              this.userInputHandler.updateViewAskAndReceive(setup, ShipType.values().length))
          .boxed().toList();
      result = IntStream.range(0, keys.size()).boxed()
          .collect(Collectors.toMap(keys::get, values::get));
      if (ManualGameSetup.isInvalidFleetSize(result, maxFleetSize)) {
        this.userView.showMessage("Uh Oh! You've entered invalid fleet sizes.");
      }
    }
    return result;
  }
}
