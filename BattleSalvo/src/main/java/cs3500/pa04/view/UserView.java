package cs3500.pa04.view;

import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.boardvalues.BoardValue;

/**
 * Represents the user's view during the game.
 */
public interface UserView {

  /**
   * Updates the view with the prompt and gets back input.
   *
   * @param prompt the prompt to display
   * @return the string representing the input
   */
  String askAndReceive(String prompt);

  /**
   * Takes in input without showing a prompt first.
   *
   * @return the string representing the input
   */
  String askAndReceive();

  /**
   * Shows the given boards in the view formatted properly.
   *
   * @param userBoard     the user board to show
   * @param opponentBoard the opponent board to show
   */
  void showBoards(BoardValue[][] userBoard, BoardValue[][] opponentBoard);

  /**
   * Shows the given game result in the view with proper formatting.
   *
   * @param gameResult the game result to show
   */
  void showResult(GameResult gameResult);

  /**
   * Shows the given message in the view with proper formatting.
   *
   * @param message the message to show
   */
  void showMessage(String message);
}
