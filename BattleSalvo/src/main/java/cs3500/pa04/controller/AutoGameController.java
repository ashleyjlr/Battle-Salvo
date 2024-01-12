package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.datadefinitions.FleetJson;
import cs3500.pa04.json.datadefinitions.ShipJson;
import cs3500.pa04.json.datadefinitions.VolleyJson;
import cs3500.pa04.json.messagetypes.clientresponse.GameType;
import cs3500.pa04.json.messagetypes.clientresponse.JoinJson;
import cs3500.pa04.json.messagetypes.serverrequest.EndGameJson;
import cs3500.pa04.json.messagetypes.serverrequest.SetUpJson;
import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipAdapter;
import cs3500.pa04.model.players.Player;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * represents a game controller for when you want to have your AI play BattleSalvo
 */
public class AutoGameController implements GameController {

  private final Socket server;
  private final ObjectMapper mapper;
  private final InputStream in;
  private final PrintStream out;
  private final Player computerUser;

  /**
   * Instantiates a new Auto game controller.
   *
   * @param server the server
   * @param user   the user
   * @throws IOException the io exception
   */
  public AutoGameController(Socket server, Player user) throws IOException {
    this.server = server;
    this.mapper = new ObjectMapper();
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream(), true, StandardCharsets.UTF_8);
    this.computerUser = user;
  }


  /**
   * runs the game, BattleSalvo, between this client and the server until someone loses, wins, or
   * they tie
   */
  @Override
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        this.delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }


  /**
   * delegates the message sent by the server to the corresponding handler for the specified method
   *
   * @param message the message the server sends to this controller
   */
  private void delegateMessage(MessageJson message) {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      this.handleJoin(arguments);
    } else if ("setup".equals(name)) {
      this.handleSetUp(arguments);
    } else if ("take-shots".equals(name)) {
      this.handleTakeShots(arguments);
    } else if ("report-damage".equals(name)) {
      this.handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      this.handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      this.handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * return this users name and game type to the server
   *
   * @param arguments the arguments for the join method
   */
  private void handleJoin(JsonNode arguments) {
    JoinJson response = new JoinJson(this.computerUser.name(), GameType.SINGLE);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);

    MessageJson message = new MessageJson("join", jsonResponse);
    jsonResponse = JsonUtils.serializeRecord(message);

    this.out.println(jsonResponse);
  }

  /**
   * sends the arguments for the set-up method to call the method and get it's return
   *
   * @param arguments the arguments for the set-up method
   */
  private void handleSetUp(JsonNode arguments) {
    SetUpJson setUpArgs = this.mapper.convertValue(arguments, SetUpJson.class);

    List<Ship> ships = this.computerUser.setup(setUpArgs.height(), setUpArgs.width(),
        setUpArgs.fleetSpec());

    ShipAdapter adapter = new ShipAdapter();
    List<ShipJson> shipsJson = ships.stream().map(adapter::adaptShip).collect(Collectors.toList());

    FleetJson fleetJson = new FleetJson(shipsJson);
    JsonNode jsonResponse = JsonUtils.serializeRecord(fleetJson);

    MessageJson message = new MessageJson("setup", jsonResponse);
    jsonResponse = JsonUtils.serializeRecord(message);
    
    this.out.println(jsonResponse);
  }

  /**
   * sends the arguments for the take shots method to call the method and get it's return
   *
   * @param arguments the arguments for the take shots method
   */
  private void handleTakeShots(JsonNode arguments) {
    List<Coordinate> coords = this.computerUser.takeShots();

    VolleyJson volley = new VolleyJson(coords);

    JsonNode jsonResponse = JsonUtils.serializeRecord(volley);

    MessageJson message = new MessageJson("take-shots", jsonResponse);
    jsonResponse = JsonUtils.serializeRecord(message);

    this.out.println(jsonResponse);
  }

  /**
   * sends the arguments for the report damage method to call the method and get it's return
   *
   * @param arguments the arguments for the report damage method
   */
  private void handleReportDamage(JsonNode arguments) {
    VolleyJson reportDamageArgs = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coordinate> allSuccessfulCoords = this.computerUser.reportDamage(
        reportDamageArgs.volley());

    VolleyJson volley = new VolleyJson(allSuccessfulCoords);
    JsonNode jsonResponse = JsonUtils.serializeRecord(volley);

    MessageJson message = new MessageJson("report-damage", jsonResponse);
    jsonResponse = JsonUtils.serializeRecord(message);

    this.out.println(jsonResponse);
  }

  /**
   * sends the arguments for the successful hits method to call the method and get it's return
   *
   * @param arguments the arguments for the successful hits method
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    VolleyJson successfulHitsArgs = this.mapper.convertValue(arguments, VolleyJson.class);
    this.computerUser.successfulHits(successfulHitsArgs.volley());
    JsonNode jsonResponse = this.mapper.createObjectNode();

    MessageJson message = new MessageJson("successful-hits", jsonResponse);
    jsonResponse = JsonUtils.serializeRecord(message);

    this.out.println(jsonResponse);
  }

  /**
   * sends the arguments for the end game method to call the method and get it's return
   *
   * @param arguments the arguments for the end game method
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGameArgs = this.mapper.convertValue(arguments, EndGameJson.class);
    this.computerUser.endGame(endGameArgs.result(), endGameArgs.reason());
    JsonNode jsonResponse = this.mapper.createObjectNode();

    MessageJson message = new MessageJson("end-game", jsonResponse);
    jsonResponse = JsonUtils.serializeRecord(message);

    this.out.println(jsonResponse);
  }
}
