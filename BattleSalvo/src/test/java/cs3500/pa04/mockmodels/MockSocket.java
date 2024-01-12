package cs3500.pa04.mockmodels;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Mock a Socket to simulate behaviors of ProxyControllers being connected to a server.
 */
public class MockSocket extends Socket {

  private final InputStream testInputs;
  private final ByteArrayOutputStream testLog;

  /**
   * @param testLog what the server has received from the client
   * @param toSend  what the server will send to the client
   */
  public MockSocket(ByteArrayOutputStream testLog, Iterable<String> toSend) {
    this.testLog = testLog;

    // Set up the list of inputs as separate messages of JSON for the ProxyDealer to handle
    StringWriter stringWriter = new StringWriter();
    try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
      for (String message : toSend) {
        printWriter.println(message);
      }
      this.testInputs = new ByteArrayInputStream(stringWriter.toString().getBytes(
          StandardCharsets.UTF_8));
    }
  }

  @Override
  public InputStream getInputStream() {
    return this.testInputs;
  }

  @Override
  public OutputStream getOutputStream() {
    return this.testLog;
  }
}
