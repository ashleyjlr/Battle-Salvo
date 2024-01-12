package cs3500.pa04;

import cs3500.pa04.controller.AutoGameController;
import cs3500.pa04.controller.GameController;
import cs3500.pa04.controller.ManualGameController;
import cs3500.pa04.model.players.ArtificialPlayer;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This is the main driver of this project.
 */
public class Driver {

  /**
   * This method connects to the server at the given host and port, builds a proxy referee to handle
   * communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   */
  private static void runClient(String host, int port) {
    try {
      Socket server = new Socket(host, port);
      GameController autoGameController = new AutoGameController(server,
          new ArtificialPlayer("janekamata", null, System.currentTimeMillis()));
      autoGameController.run();
    } catch (UnknownHostException e) {
      throw new IllegalStateException("Caught UnknownHostException: " + e.getMessage());
    } catch (IOException e) {
      throw new IllegalStateException("Caught IOException: " + e.getMessage());
    }
  }

  /**
   * Project entry point. If an invalid number of command line args are present, an exception is
   * thrown.
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      new ManualGameController().run();
    } else if (args.length == 2) {
      Driver.runClient(args[0], Integer.parseInt(args[1]));
    } else {
      throw new IllegalArgumentException(
          "There are " + args.length + " arguments, but there should be none or two.");
    }
  }
}