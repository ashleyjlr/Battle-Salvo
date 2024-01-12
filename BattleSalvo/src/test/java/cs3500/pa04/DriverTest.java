package cs3500.pa04;

import static cs3500.pa04.Driver.main;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.mockmodels.MockInputStream;
import cs3500.pa04.mockmodels.MockOutputStream;
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
 * Tests for the Driver class.
 */
class DriverTest {

  /**
   * The constant LINE_SEPARATOR.
   */
  private static final String LINE_SEPARATOR = System.lineSeparator();
  /**
   * The constant ARGS.
   */
  private static final String[] ARGS = new String[]{};
  private static final String[] ARGS_AUTO = new String[]{"0.0.0.0", "35001"};

  /**
   * The User input.
   */
  private StringJoiner userInput;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    this.userInput = new StringJoiner(DriverTest.LINE_SEPARATOR);
  }

  /**
   * Tear down.
   */
  @AfterEach
  void tearDown() {
    this.userInput = null;
    assertNull(this.userInput);
    System.setOut(System.out);
    System.setIn(System.in);
  }

  /**
   * Test Driver initialization.
   */
  @Test
  void testDriver() {
    Driver driver = new Driver();
    assertSame(Driver.class, driver.getClass());
  }

  /**
   * Test main exceptions.
   */
  @Test
  void testMainExceptions() {
    assertThrows(IllegalArgumentException.class, () -> main(new String[]{"", " 1", "1", "1"}));
    assertThrows(IllegalArgumentException.class, () -> main(new String[]{"", "filename"}));
  }

  /**
   * Test main.
   */
  @Test
  void testMain() {
    System.setIn(new MockInputStream());
    System.setOut(new PrintStream(new MockOutputStream(), true, StandardCharsets.UTF_8));
    assertThrows(IllegalStateException.class, () -> main(DriverTest.ARGS));
    this.runLostTestExpectedInput();
    ByteArrayInputStream inContent = new ByteArrayInputStream(
        this.userInput.toString().getBytes(StandardCharsets.UTF_8));
    System.setIn(new BufferedInputStream(inContent));
    System.setOut(new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8));
    assertDoesNotThrow(() -> main(DriverTest.ARGS));
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
  }

    @Test
    void testAutoMain() {
      System.setIn(new MockInputStream());
      System.setOut(new PrintStream(new MockOutputStream(), true, StandardCharsets.UTF_8));
      assertThrows(IllegalStateException.class, () -> main(DriverTest.ARGS_AUTO));
      this.runLostTestExpectedInput();
      ByteArrayInputStream inContent = new ByteArrayInputStream(
          this.userInput.toString().getBytes(StandardCharsets.UTF_8));
      System.setIn(new BufferedInputStream(inContent));
      System.setOut(new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8));
      assertThrows(IllegalStateException.class, () -> main(DriverTest.ARGS_AUTO));
    }

//    @Test
//    void testThis() {
//        assertDoesNotThrow(() -> main(DriverTest.ARGS_AUTO));
//    }
}