package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.mockmodels.MockAppendable;
import cs3500.pa04.mockmodels.MockInputStream;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.boardvalues.BoardValue;
import cs3500.pa04.model.boardvalues.CellState;
import cs3500.pa04.model.boardvalues.ShipStatus;
import cs3500.pa04.model.boardvalues.ShipType;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the TerminalUserView class.
 */
class TerminalUserViewTest {

  /**
   * The constant MOCK_IN.
   */
  private static final MockInputStream MOCK_IN = new MockInputStream();
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
   * The constant MOCK_SESSION_USER_VIEW.
   */
  private static final UserView MOCK_USER_VIEW = new TerminalUserView(
      new InputStreamReader(TerminalUserViewTest.MOCK_IN, StandardCharsets.UTF_8),
      new MockAppendable());
  /**
   * The Expected output.
   */
  private StringJoiner expectedOutput;
  /**
   * The User input.
   */
  private StringJoiner userInput;
  /**
   * The In content.
   */
  private ByteArrayInputStream inContent;
  /**
   * The Out content.
   */
  private ByteArrayOutputStream outContent;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    this.expectedOutput = new StringJoiner(TerminalUserViewTest.LINE_SEPARATOR);
    this.userInput = new StringJoiner(TerminalUserViewTest.LINE_SEPARATOR);
    this.outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(this.outContent, true, StandardCharsets.UTF_8));
  }

  /**
   * Tear down.
   */
  @AfterEach
  void tearDown() {
    this.expectedOutput = null;
    this.userInput = null;
    this.outContent.reset();
    assertNull(this.expectedOutput);
    assertNull(this.userInput);
    System.setOut(System.out);
    System.setIn(System.in);
  }

  /**
   * Tests ask and receive no arguments.
   */
  @Test
  void testAskAndReceiveNoArguments() {
    this.userInput.add("pepperoni");
    this.inContent = new ByteArrayInputStream(
        this.userInput.toString().getBytes(StandardCharsets.UTF_8));
    System.setIn(new BufferedInputStream(this.inContent));
    new TerminalUserView().askAndReceive();
    assertEquals(this.expectedOutput.toString(), this.outContent.toString());
    assertThrows(IllegalStateException.class,
        TerminalUserViewTest.MOCK_USER_VIEW::askAndReceive);
  }

  /**
   * Test ask and receive.
   */
  @Test
  void testAskAndReceiveArguments() {
    this.userInput.add("pepperoni");
    this.inContent = new ByteArrayInputStream(
        this.userInput.toString().getBytes(StandardCharsets.UTF_8));
    System.setIn(new BufferedInputStream(this.inContent));
    new TerminalUserView().askAndReceive("Enter: ");
    this.expectedOutput.add(TerminalUserViewTest.VIEW_SEPARATOR);
    this.expectedOutput.add("Enter: ");
    this.expectedOutput.add(TerminalUserViewTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    assertEquals(this.expectedOutput.toString(), this.outContent.toString());
    assertThrows(IllegalStateException.class,
        () -> new TerminalUserView(
            new InputStreamReader(TerminalUserViewTest.MOCK_IN, StandardCharsets.UTF_8),
            new PrintStream(System.out, true, StandardCharsets.UTF_8)).askAndReceive("Jello"));
  }

  /**
   * Tests show boards.
   */
  @Test
  void testShowBoards() {
    BoardValue[][] sampleBoard = new BoardValue[][]{
        new BoardValue[]{CellState.EMPTY, CellState.HIT, CellState.MISS},
        new BoardValue[]{ShipType.CARRIER, ShipType.BATTLESHIP, ShipType.DESTROYER},
        new BoardValue[]{ShipType.SUBMARINE, ShipStatus.HIT, ShipStatus.NOT_HIT}};
    new TerminalUserView().showBoards(sampleBoard, sampleBoard);
    this.expectedOutput.add(TerminalUserViewTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    this.expectedOutput.add("Opponent Board Data:");
    this.expectedOutput.add("_  *  .");
    this.expectedOutput.add("C  B  D");
    this.expectedOutput.add("S  *  S");
    this.expectedOutput.add("");
    this.expectedOutput.add("Your Board:");
    this.expectedOutput.add("_  *  .");
    this.expectedOutput.add("C  B  D");
    this.expectedOutput.add("S  *  S");
    this.expectedOutput.add("");
    this.expectedOutput.add("");
    assertEquals(this.expectedOutput.toString(), this.outContent.toString());
    assertThrows(IllegalStateException.class,
        () -> TerminalUserViewTest.MOCK_USER_VIEW.showBoards(sampleBoard, sampleBoard));
  }

  /**
   * Tests show result.
   */
  @Test
  void testShowResult() {
    new TerminalUserView().showResult(GameResult.TIED);
    this.expectedOutput.add(TerminalUserViewTest.VIEW_SEPARATOR);
    this.expectedOutput.add("Game Over: You tied!");
    assertEquals(this.expectedOutput.toString().strip(), this.outContent.toString().strip());
    assertThrows(IllegalStateException.class,
        () -> TerminalUserViewTest.MOCK_USER_VIEW.showResult(GameResult.LOSE));
  }

  /**
   * Test show message.
   */
  @Test
  void testShowMessage() {
    new TerminalUserView().showMessage("There is pizza, ;but no pineapple.");
    this.expectedOutput.add("");
    this.expectedOutput.add("There is pizza, ");
    this.expectedOutput.add("but no pineapple.");
    this.expectedOutput.add("");
    this.expectedOutput.add("");
    assertEquals(this.expectedOutput.toString(), this.outContent.toString());
    assertThrows(IllegalStateException.class,
        () -> TerminalUserViewTest.MOCK_USER_VIEW.showMessage(""));
  }
}