package cs3500.pa04.controller;

import cs3500.pa04.view.UserView;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a handler for user input parsed into int arrays.
 */
class UserInputHandler {

  /**
   * The constant SPACES_PATTERN.
   */
  private static final Pattern SPACES_PATTERN = Pattern.compile("\\s+");

  /**
   * The User view.
   */
  private final UserView userView;

  /**
   * Instantiates a new User input handler.
   *
   * @param userView the user view
   */
  UserInputHandler(UserView userView) {
    this.userView = userView;
  }

  /**
   * Parses input into an int array.
   *
   * @param input the input representing the user input string
   * @return the int [ ] representing the input as an array of ints
   */
  private static int[] parseInput(String input) {
    String[] parts = input.split(" ");
    return Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
  }

  /**
   * Takes in user input without showing a prompt first. If the user input is invalid based on
   * format or number of ints, then the user is asked to re-enter the value.
   *
   * @return the int [ ] representing the user input
   */
  int[] updateViewAskAndReceive() {
    int[] result = null;
    String errorMessage =
        "Uh Oh! You've entered an invalid line of input. Please remember that the;"
            + "input must be 2 integers separated by a space. Enter the corrected input:";
    while (Objects.isNull(result)) {
      try {
        String rawInput = UserInputHandler.SPACES_PATTERN.matcher(this.userView.askAndReceive())
            .replaceAll(" ").strip();
        int[] userInput = UserInputHandler.parseInput(rawInput);
        if (userInput.length == 2) {
          result = userInput;
        } else {
          this.userView.showMessage(errorMessage);
        }
      } catch (NumberFormatException e) {
        this.userView.showMessage(errorMessage);
      }
    }
    return result;
  }

  /**
   * Updates the view with the prompt and takes in user input. If the user input is invalid based on
   * format or number of ints, then the user is asked to re-enter the entire input again.
   *
   * @param prompt      the prompt to show the user
   * @param inputLength the input length representing the number of ints the user is expected to
   *                    input
   * @return the int [ ] representing the user input
   */
  int[] updateViewAskAndReceive(String prompt, int inputLength) {
    int[] result = null;
    String errorMessage =
        "Uh Oh! You've entered invalid input. Please remember that the;"
            + "input must be " + inputLength
            + " integers separated by a space. Try entering the entire input again!";
    while (Objects.isNull(result)) {
      try {
        String rawInput = UserInputHandler.SPACES_PATTERN.matcher(
            this.userView.askAndReceive(prompt)).replaceAll(" ").strip();
        int[] userInput = UserInputHandler.parseInput(rawInput);
        if (userInput.length == inputLength) {
          result = userInput;
        } else {
          this.userView.showMessage(errorMessage);
        }
      } catch (NumberFormatException e) {
        this.userView.showMessage(errorMessage);
      }
    }
    return result;
  }
}
