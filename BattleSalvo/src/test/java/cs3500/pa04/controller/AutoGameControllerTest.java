package cs3500.pa04.controller;

import static cs3500.pa04.model.GameResult.WIN;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.datadefinitions.VolleyJson;
import cs3500.pa04.json.messagetypes.clientresponse.GameType;
import cs3500.pa04.json.messagetypes.clientresponse.JoinJson;
import cs3500.pa04.json.messagetypes.serverrequest.EndGameJson;
import cs3500.pa04.json.messagetypes.serverrequest.SetUpJson;
import cs3500.pa04.mockmodels.MockSocket;
import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.boardvalues.ShipType;
import cs3500.pa04.model.playerboards.ArtificialPlayerBoard;
import cs3500.pa04.model.players.ArtificialPlayer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutoGameControllerTest {

  private ByteArrayOutputStream testLog;
  private AutoGameController autoController;
  private ArtificialPlayerBoard artificialPlayerBoard;
  private ArtificialPlayer artificialPlayer;

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private static JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName,
        JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  void setup() {
    this.artificialPlayerBoard = new ArtificialPlayerBoard(new Coordinate(10, 10));
    this.artificialPlayer = new ArtificialPlayer(this.artificialPlayerBoard, 1);
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", this.logToString());
  }

  /**
   * Tear down.
   */
  @AfterEach
  void tearDown() {
    this.artificialPlayerBoard = null;
    this.artificialPlayer = null;
    assertNull(this.artificialPlayerBoard);
    assertNull(this.artificialPlayer);
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return this.testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try (JsonParser jsonParser = new ObjectMapper().createParser(this.logToString())) {
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  void testRun() {
    // Create sample hint
    JoinJson join = new JoinJson("ashley", GameType.SINGLE);
    JsonNode jsonNode = AutoGameControllerTest.createSampleMessage("join", join);

    // Create socket with sample input
    MockSocket socket = new MockSocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.autoController = new AutoGameController(socket, new ArtificialPlayer("ashley", null, 1));
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.autoController.run();
    this.responseToClass(Object.class);
  }


  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  void testHandleJoin() {
    // Create sample hint
    JoinJson join = new JoinJson("ashley", GameType.SINGLE);
    JsonNode jsonNode = AutoGameControllerTest.createSampleMessage("join", join);

    // Create socket with sample input
    MockSocket socket = new MockSocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.autoController = new AutoGameController(socket, new ArtificialPlayer("ashley", null, 1));
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.autoController.run();
    this.responseToClass(Object.class);
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  void testHandleSetUp() {
    // Create sample hint
    Map<ShipType, Integer> specifications = new EnumMap<>(ShipType.class);
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);

    SetUpJson setUp = new SetUpJson(15, 15, specifications);
    JsonNode jsonNode = AutoGameControllerTest.createSampleMessage("setup", setUp);

    // Create socket with sample input
    MockSocket socket = new MockSocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.autoController = new AutoGameController(socket, new ArtificialPlayer("ashley", null, 1));
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.autoController.run();
    this.responseToClass(Object.class);
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  void testHandleTakeShots() {
    // Create sample hint
    List<Coordinate> coords = new ArrayList<>();
    coords.add(new Coordinate(0, 0));
    coords.add(new Coordinate(0, 1));
    coords.add(new Coordinate(5, 2));
    VolleyJson volley = new VolleyJson(coords);
    JsonNode jsonNode = AutoGameControllerTest.createSampleMessage("take-shots", volley);

    // Create socket with sample input
    MockSocket socket = new MockSocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.autoController = new AutoGameController(socket, this.artificialPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.autoController.run();
    this.responseToClass(Object.class);
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  void testHandleReportDamage() {
    // Create sample hint
    List<Coordinate> coords = new ArrayList<>();
    coords.add(new Coordinate(0, 0));
    coords.add(new Coordinate(0, 1));
    coords.add(new Coordinate(5, 2));
    VolleyJson volley = new VolleyJson(coords);
    JsonNode jsonNode = AutoGameControllerTest.createSampleMessage("report-damage", volley);

    // Create socket with sample input
    MockSocket socket = new MockSocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.autoController = new AutoGameController(socket, this.artificialPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.autoController.run();
    this.responseToClass(Object.class);
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  void testHandleSuccessfulHits() {
    // Create sample hint
    List<Coordinate> coords = new ArrayList<>();
    coords.add(new Coordinate(0, 0));
    coords.add(new Coordinate(0, 1));
    coords.add(new Coordinate(5, 2));
    VolleyJson volley = new VolleyJson(coords);
    JsonNode jsonNode = AutoGameControllerTest.createSampleMessage("successful-hits", volley);

    // Create socket with sample input
    MockSocket socket = new MockSocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.autoController = new AutoGameController(socket, this.artificialPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.autoController.run();
    this.responseToClass(Object.class);
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  void testHandleEndGame() {
    EndGameJson endGame = new EndGameJson(WIN, "you won");
    JsonNode jsonNode = AutoGameControllerTest.createSampleMessage("end-game", endGame);

    // Create socket with sample input
    MockSocket socket = new MockSocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.autoController = new AutoGameController(socket, this.artificialPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    this.autoController.run();
    this.responseToClass(Object.class);
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  void testHandleBad() {
    JsonNode jsonNode = AutoGameControllerTest.createSampleMessage("invalid", new Coordinate(1, 1));

    // Create socket with sample input
    MockSocket socket = new MockSocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.autoController = new AutoGameController(socket, this.artificialPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    assertThrows(IllegalStateException.class, () -> this.autoController.run());
  }


  @Test
  public void testHandleClosedServer() throws IOException {
    JsonNode jsonNode = AutoGameControllerTest.createSampleMessage("invalid", new Coordinate(1, 1));

    // Create socket with sample input
    MockSocket socket = new MockSocket(this.testLog, List.of(jsonNode.toString()));
    socket.close();
    // Create a dealer
    try {
      this.autoController = new AutoGameController(socket, this.artificialPlayer);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // Run dealer and verify response.
    assertDoesNotThrow(() -> this.autoController.run());
  }
}
