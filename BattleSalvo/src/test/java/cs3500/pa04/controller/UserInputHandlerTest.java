package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import cs3500.pa04.view.TerminalUserView;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the UserInputHandler class.
 */
class UserInputHandlerTest {

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
    this.expectedOutput = new StringJoiner(UserInputHandlerTest.LINE_SEPARATOR);
    this.userInput = new StringJoiner(UserInputHandlerTest.LINE_SEPARATOR);
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
   * Test update view ask and receive no arguments.
   */
  @Test
  void testUpdateViewAskAndReceiveNoArguments() {
    this.userInput.add("pepperoni");
    this.userInput.add("1 2 3");
    this.userInput.add("      0   2");
    this.inContent = new ByteArrayInputStream(
        this.userInput.toString().getBytes(StandardCharsets.UTF_8));
    System.setIn(new BufferedInputStream(this.inContent));
    assertArrayEquals(new int[]{0, 2},
        new UserInputHandler(new TerminalUserView()).updateViewAskAndReceive());
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered an invalid line of input. Please remember that the");
    this.expectedOutput.add(
        "input must be 2 integers separated by a space. Enter the corrected input:");
    this.expectedOutput.add("");
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered an invalid line of input. Please remember that the");
    this.expectedOutput.add(
        "input must be 2 integers separated by a space. Enter the corrected input:");
    this.expectedOutput.add("");
    this.expectedOutput.add("");
    assertEquals(this.expectedOutput.toString(), this.outContent.toString());
  }

  /**
   * Test update view ask and receive arguments.
   */
  @Test
  void testUpdateViewAskAndReceiveArguments() {
    this.userInput.add("pepperoni");
    this.userInput.add("1 2 3");
    this.userInput.add("      0   2");
    this.inContent = new ByteArrayInputStream(
        this.userInput.toString().getBytes(StandardCharsets.UTF_8));
    System.setIn(new BufferedInputStream(this.inContent));
    this.expectedOutput.add(UserInputHandlerTest.VIEW_SEPARATOR);
    this.expectedOutput.add("Enter: ");
    this.expectedOutput.add(UserInputHandlerTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered invalid input. Please remember that the");
    this.expectedOutput.add(
        "input must be 2 integers separated by a space. Try entering the entire input again!");
    this.expectedOutput.add("");
    this.expectedOutput.add(UserInputHandlerTest.VIEW_SEPARATOR);
    this.expectedOutput.add("Enter: ");
    this.expectedOutput.add(UserInputHandlerTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    this.expectedOutput.add(
        "Uh Oh! You've entered invalid input. Please remember that the");
    this.expectedOutput.add(
        "input must be 2 integers separated by a space. Try entering the entire input again!");
    this.expectedOutput.add("");
    this.expectedOutput.add(UserInputHandlerTest.VIEW_SEPARATOR);
    this.expectedOutput.add("Enter: ");
    this.expectedOutput.add(UserInputHandlerTest.VIEW_SEPARATOR);
    this.expectedOutput.add("");
    assertArrayEquals(new int[]{0, 2},
        new UserInputHandler(new TerminalUserView()).updateViewAskAndReceive("Enter: ", 2));
    assertEquals(this.expectedOutput.toString(), this.outContent.toString());
  }
}