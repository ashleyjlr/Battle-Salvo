package cs3500.pa04.view;

import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.boardvalues.BoardValue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * Represents a terminal user view.
 */
public class TerminalUserView implements UserView {

  /**
   * The constant VIEW_SEPARATOR.
   */
  private static final String VIEW_SEPARATOR = "----------------------------"
      + "--------------------------";
  /**
   * The constant LINE_SEPARATOR.
   */
  private static final String LINE_SEPARATOR = System.lineSeparator();
  /**
   * The constant NEW_LINE_PATTERN to split lines at.
   */
  private static final Pattern NEW_LINE_PATTERN = Pattern.compile(";");

  /**
   * The Output.
   */
  private final Appendable output;
  /**
   * The Input.
   */
  private final BufferedReader input;


  /**
   * Instantiates a new Terminal user view with the given input and output.
   *
   * @param input  the input
   * @param output the output
   */
  public TerminalUserView(Reader input, Appendable output) {
    this.output = output;
    this.input = new BufferedReader(input);
  }

  /**
   * Instantiates a new Terminal user view with the default System.in and System.out.
   */
  public TerminalUserView() {
    this(new InputStreamReader(System.in, StandardCharsets.UTF_8), System.out);
  }

  /**
   * Converts a given 2D array of board values into a string representation.
   *
   * @param board the board to convert
   * @return the string representing the given board
   */
  private static String boardString(BoardValue[][] board) {
    StringJoiner result = new StringJoiner(TerminalUserView.LINE_SEPARATOR);
    StringJoiner resultRow = new StringJoiner("  ");
    for (BoardValue[] y : board) {
      for (BoardValue x : y) {
        resultRow.add(x.toString());
      }
      result.add(resultRow.toString());
      resultRow = new StringJoiner("  ");
    }
    return result.toString();
  }

  /**
   * Updates the view with the prompt and takes in user input.
   *
   * @param prompt the prompt to display to the user
   * @return the string representing the user's input
   */
  @Override
  public String askAndReceive(String prompt) {
    String result = TerminalUserView.VIEW_SEPARATOR
        + TerminalUserView.LINE_SEPARATOR
        + prompt
        + TerminalUserView.LINE_SEPARATOR
        + TerminalUserView.VIEW_SEPARATOR
        + TerminalUserView.LINE_SEPARATOR;
    this.show(result);
    try {
      return this.input.readLine();
    } catch (IOException e) {
      throw new IllegalStateException("Caught IOException: " + e.getMessage());
    }
  }

  /**
   * Takes in user input without showing a prompt first.
   *
   * @return the string representing the user's input
   */
  @Override
  public String askAndReceive() {
    try {
      return this.input.readLine();
    } catch (IOException e) {
      throw new IllegalStateException("Caught IOException: " + e.getMessage());
    }
  }

  /**
   * Shows the given boards in the view formatted properly. Utilizes a helper method to convert the
   * 2D array of board values into a string.
   *
   * @param userBoard     the user board to show
   * @param opponentBoard the opponent board to show
   */
  @Override
  public void showBoards(BoardValue[][] userBoard, BoardValue[][] opponentBoard) {
    String result = TerminalUserView.VIEW_SEPARATOR
        + TerminalUserView.LINE_SEPARATOR
        + TerminalUserView.LINE_SEPARATOR
        + "Opponent Board Data:"
        + TerminalUserView.LINE_SEPARATOR
        + TerminalUserView.boardString(opponentBoard)
        + TerminalUserView.LINE_SEPARATOR
        + TerminalUserView.LINE_SEPARATOR
        + "Your Board:"
        + TerminalUserView.LINE_SEPARATOR
        + TerminalUserView.boardString(userBoard)
        + TerminalUserView.LINE_SEPARATOR
        + TerminalUserView.LINE_SEPARATOR;
    this.show(result);
  }

  /**
   * Shows the given game result in the view with proper formatting.
   *
   * @param gameResult the game result to show
   */
  @Override
  public void showResult(GameResult gameResult) {
    String result = TerminalUserView.LINE_SEPARATOR
        + TerminalUserView.VIEW_SEPARATOR
        + TerminalUserView.LINE_SEPARATOR
        + "Game Over: You "
        + gameResult.name().toLowerCase()
        + '!'
        + TerminalUserView.LINE_SEPARATOR;
    this.show(result);
  }

  /**
   * Shows the given message to the user with proper formatting.
   *
   * @param message the message to show
   */
  @Override
  public void showMessage(String message) {
    String result = TerminalUserView.LINE_SEPARATOR
        + message
        + TerminalUserView.LINE_SEPARATOR
        + TerminalUserView.LINE_SEPARATOR;
    this.show(result);
  }

  /**
   * Shows the given text to the user. If the text contains anything that matches the new line
   * pattern, the text is split into a new line at the point.
   *
   * @param text the text to be shown
   */
  private void show(CharSequence text) {
    try {
      this.output.append(
          TerminalUserView.NEW_LINE_PATTERN.matcher(text)
              .replaceAll(TerminalUserView.LINE_SEPARATOR));
    } catch (IOException e) {
      throw new IllegalStateException("Caught IOException: " + e.getMessage());
    }
  }
}
