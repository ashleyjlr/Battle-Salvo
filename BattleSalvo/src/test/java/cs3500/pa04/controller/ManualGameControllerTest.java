package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringJoiner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the class ManualGameController.
 */
class ManualGameControllerTest {

  /**
   * The constant FILE_SEPARATOR.
   */
  private static final String FILE_SEPARATOR = File.separator;
  /**
   * The constant LINE_SEPARATOR.
   */
  private static final String LINE_SEPARATOR = System.lineSeparator();
  /**
   * The User input.
   */
  private StringJoiner userInput;
  /**
   * The Out content.
   */
  private ByteArrayOutputStream outContent;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    this.userInput = new StringJoiner(ManualGameControllerTest.LINE_SEPARATOR);
    this.outContent = new ByteArrayOutputStream();
  }

  /**
   * Tear down.
   */
  @AfterEach
  void tearDown() {
    this.userInput = null;
    this.outContent.reset();
    assertNull(this.userInput);
  }

  /**
   * Test constructor for ManualGameController.
   */
  @Test
  void testManualGameControllerConstructor() {
    assertThrows(NullPointerException.class, ManualGameController::new);
  }

  /**
   * Test run win.
   */
  @Test
  void testRunWin() {
    try {
      this.runWonTestExpectedInput();
      GameController controller = new ManualGameController(
          new StringReader(this.userInput.toString()),
          new PrintStream(this.outContent, true, StandardCharsets.UTF_8), 0);
      controller.run();
      assertEquals(Files.readString(Path.of(
              String.join(ManualGameControllerTest.FILE_SEPARATOR, "src", "test", "resources",
                  "controllerTestWonExpectedOutput.txt"))),
          this.outContent.toString());
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * Test run lost.
   */
  @Test
  void testRunLost() {
    try {
      this.runLostTestExpectedInput();
      GameController controller = new ManualGameController(
          new StringReader(this.userInput.toString()),
          new PrintStream(this.outContent, true, StandardCharsets.UTF_8), 0);
      controller.run();
      assertEquals(Files.readString(Path.of(
              String.join(ManualGameControllerTest.FILE_SEPARATOR, "src", "test", "resources",
                  "controllerTestLostExpectedOutput.txt"))),
          this.outContent.toString());
    } catch (IOException e) {
      fail();
    }
  }

//  /**
//   * Test run tie.
//   */
//  @Test
//  void testRunTie() {
//    try {
//      this.runTieTestExpectedInput();
//      GameController controller = new ManualGameController(
//          new StringReader(this.userInput.toString()),
//          new PrintStream(this.outContent, true, StandardCharsets.UTF_8), 0);
//      controller.run();
//      assertEquals(Files.readString(Path.of(
//              String.join(ManualGameControllerTest.FILE_SEPARATOR, "src", "test", "resources",
//                  "controllerTestTieExpectedOutput.txt"))),
//          this.outContent.toString());
//    } catch (IOException e) {
//      fail();
//    }
//  }

  /**
   * Run won test expected input.
   */
  private void runWonTestExpectedInput() {
    this.userInput.add("6 6");
    this.userInput.add("1 1 1 1");
    this.userInput.add("10 1");
    this.userInput.add("5 -1");
    this.userInput.add("-1 5");
    this.userInput.add("1 10");
    this.userInput.add("0 0");
    this.userInput.add("1 0");
    this.userInput.add("2 0");
    this.userInput.add("3 0");
    this.userInput.add("4 0");
    this.userInput.add("5 0");
    this.userInput.add("0 2");
    this.userInput.add("1 2");
    this.userInput.add("salami");
    this.userInput.add("0");
    this.userInput.add("20 20");
    this.userInput.add("2 2");
    this.userInput.add("3 2");
    this.userInput.add("4 2");
    this.userInput.add("help");
    this.userInput.add("2 2");
    this.userInput.add("3 2");
    this.userInput.add("4 2");
    this.userInput.add("2 3");
    this.userInput.add("3 3");
    this.userInput.add("4 3");
    this.userInput.add("0 5");
    this.userInput.add("1 5");
    this.userInput.add("2 5");
    this.userInput.add("3 5");
    this.userInput.add("4 5");
    this.userInput.add("2 1");
    this.userInput.add("3 1");
    this.userInput.add("1 1");
    this.userInput.add("1 1");
    this.userInput.add("1 1");
    this.userInput.add("1 1");
  }

  /**
   * Run lost test expected input.
   */
  private void runLostTestExpectedInput() {
    this.userInput.add("6 6");
    this.userInput.add("1 1 1 1");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
    this.userInput.add("5 5");
  }

  /**
   * Run won test expected input.
   */
  private void runTieTestExpectedInput() {
    this.userInput.add("6 6");
    this.userInput.add("1 1 1 1");
    this.userInput.add("1 2");
    this.userInput.add("1 4");
    this.userInput.add("0 1");
    this.userInput.add("5 0");
    this.userInput.add("4 0");
    this.userInput.add("5 1");
    this.userInput.add("2 2");
    this.userInput.add("0 2");
    this.userInput.add("1 3");
    this.userInput.add("1 1");
    this.userInput.add("4 0");
    this.userInput.add("5 1");
    this.userInput.add("2 2");
    this.userInput.add("0 2");
    this.userInput.add("3 0");
    this.userInput.add("4 1");
    this.userInput.add("0 3");
    this.userInput.add("3 2");
    this.userInput.add("2 3");
    this.userInput.add("2 1");
    this.userInput.add("1 3");
    this.userInput.add("1 1");
    this.userInput.add("3 0");
    this.userInput.add("4 1");
    this.userInput.add("2 0");
    this.userInput.add("3 1");
    this.userInput.add("0 3");
    this.userInput.add("3 2");
    this.userInput.add("2 3");
    this.userInput.add("2 1");
    this.userInput.add("4 2");
    this.userInput.add("3 3");
    this.userInput.add("2 4");
    this.userInput.add("2 0");
    this.userInput.add("3 1");
    this.userInput.add("4 2");
    this.userInput.add("3 3");
    this.userInput.add("1 0");
    this.userInput.add("5 2");
    this.userInput.add("4 3");
    this.userInput.add("3 4");
    this.userInput.add("2 4");
    this.userInput.add("1 0");
    this.userInput.add("5 2");
    this.userInput.add("4 3");
    this.userInput.add("0 0");
    this.userInput.add("5 3");
    this.userInput.add("4 4");
    this.userInput.add("3 4");
    this.userInput.add("0 0");
    this.userInput.add("5 3");
    this.userInput.add("4 4");
    this.userInput.add("2 5");
    this.userInput.add("4 5");
    this.userInput.add("0 5");
    this.userInput.add("5 4");
    this.userInput.add("1 5");
    this.userInput.add("0 4");
    this.userInput.add("3 5");
    this.userInput.add("1 5");
    this.userInput.add("0 4");
    this.userInput.add("3 5");
    this.userInput.add("5 5");
  }
}