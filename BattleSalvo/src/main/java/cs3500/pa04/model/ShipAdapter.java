package cs3500.pa04.model;

import cs3500.pa04.json.datadefinitions.Direction;
import cs3500.pa04.json.datadefinitions.ShipJson;

/**
 * represents an adapter for a ship to be created as ShipJson
 */
public class ShipAdapter {

  /**
   * Adapts ship class object to ship json.
   *
   * @param ship the ship to adapt
   * @return the ship json representing the given ship object
   */
  public ShipJson adaptShip(Ship ship) {
    Coordinate startCoord = ship.getStartCoordinate();
    int length = ship.size();
    Direction direction = ship.getDirection();
    return new ShipJson(startCoord, length, direction);
  }
}
