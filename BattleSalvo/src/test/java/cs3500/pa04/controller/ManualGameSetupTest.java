package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.boardvalues.ShipType;
import cs3500.pa04.view.TerminalUserView;
import cs3500.pa04.view.UserView;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the GameSetup class.
 */
class ManualGameSetupTest {

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
    this.expectedOutput = new StringJoiner(ManualGameSetupTest.LINE_SEPARATOR);
    this.userInput = new StringJoiner(ManualGameSetupTest.LINE_SEPARATOR);
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
   * Test get board size.
   */
  @Test
  void testGetBoardSize() {
    this.userInput.add("pepperoni");
    this.userInput.add("1 2 3");
    this.userInput.add("      0   1");
    this.userInput.add("      6   100");
    this.userInput.add("6 6");
    this.inContent = new ByteArrayInputStream(
        this.userInput.toString().getBytes(StandardCharsets.UTF_8));
    System.setIn(new BufferedInputStream(this.inContent));
    this.getBoardSizeTestExpectedOutput();
    UserView view = new TerminalUserView();
    GameSetup setup = new ManualGameSetup(view, new UserInputHandler(view));
    assertEquals(this.expectedOutput.toString(), this.outContent.toString());
    assertEquals(new Coordinate(6, 6), setup.setupBoardSize());
  }

  /**
   * Get board size test expected output.
   */
  private void getBoardSizeTestExpectedOutput() {
    this.expectedOutput.add("");
    this.expectedOutput.add("Hello! Welcome to the OOD BattleSalvo Game!");
    this.expectedOutput.add("");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("Please enter a valid height and width below:");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered invalid input. Please remember that the");
    this.expectedOutput.add(
        "input must be 2 integers separated by a space. Try entering the entire input again!");
    this.expectedOutput.add("");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("Please enter a valid height and width below:");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered invalid input. Please remember that the");
    this.expectedOutput.add(
        "input must be 2 integers separated by a space. Try entering the entire input again!");
    this.expectedOutput.add("");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("Please enter a valid height and width below:");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered invalid dimensions. Please remember that the");
    this.expectedOutput.add(
        "height and width of the game must be in the range [6, 15], inclusive. Try again!");
    this.expectedOutput.add("");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("Please enter a valid height and width below:");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered invalid dimensions. Please remember that the");
    this.expectedOutput.add(
        "height and width of the game must be in the range [6, 15], inclusive. Try again!");
    this.expectedOutput.add("");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("Please enter a valid height and width below:");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
  }

  /**
   * Test setup fleet size.
   */
  @Test
  void testSetupFleetSize() {
    this.userInput.add("pepperoni");
    this.userInput.add("1 2 3");
    this.userInput.add("      0   1");
    this.userInput.add("      6   100");
    this.userInput.add("6 6");
    this.userInput.add("pepperoni");
    this.userInput.add("1 2 3");
    this.userInput.add(" 0  0   0   1");
    this.userInput.add("  1 1     4   3");
    this.userInput.add("1 2 1 2");
    this.inContent = new ByteArrayInputStream(
        this.userInput.toString().getBytes(StandardCharsets.UTF_8));
    System.setIn(new BufferedInputStream(this.inContent));
    this.getBoardSizeTestExpectedOutput();
    UserView view = new TerminalUserView();
    GameSetup setup = new ManualGameSetup(view, new UserInputHandler(view));
    assertEquals(this.expectedOutput.toString(), this.outContent.toString());
    assertEquals(new Coordinate(6, 6), setup.setupBoardSize());
    this.outContent.reset();
    this.expectedOutput = new StringJoiner(ManualGameSetupTest.LINE_SEPARATOR);
    Map<ShipType, Integer> result = new EnumMap<>(ShipType.class);
    result.put(ShipType.CARRIER, 1);
    result.put(ShipType.BATTLESHIP, 2);
    result.put(ShipType.DESTROYER, 1);
    result.put(ShipType.SUBMARINE, 2);
    assertEquals(result, setup.setupFleetSize());
    this.setupFleetSizeTestExpectedOutput();
    assertEquals(this.expectedOutput.toString(), this.outContent.toString());
  }

  /**
   * Setup fleet size test expected output.
   */
  private void setupFleetSizeTestExpectedOutput() {
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
    this.expectedOutput.add("Remember, your fleet may not exceed size 6.");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered invalid input. Please remember that the");
    this.expectedOutput.add(
        "input must be 4 integers separated by a space. Try entering the entire input again!");
    this.expectedOutput.add("");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
    this.expectedOutput.add("Remember, your fleet may not exceed size 6.");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered invalid input. Please remember that the");
    this.expectedOutput.add(
        "input must be 4 integers separated by a space. Try entering the entire input again!");
    this.expectedOutput.add("");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
    this.expectedOutput.add("Remember, your fleet may not exceed size 6.");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered invalid fleet sizes.");
    this.expectedOutput.add("");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
    this.expectedOutput.add("Remember, your fleet may not exceed size 6.");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered invalid fleet sizes.");
    this.expectedOutput.add("");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
    this.expectedOutput.add("Remember, your fleet may not exceed size 6.");
    this.expectedOutput.add(ManualGameSetupTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
  }
}