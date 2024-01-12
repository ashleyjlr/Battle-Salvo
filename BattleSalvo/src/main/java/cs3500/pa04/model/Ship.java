package cs3500.pa04.model;

import cs3500.pa04.json.datadefinitions.Direction;
import cs3500.pa04.model.boardvalues.ShipStatus;
import cs3500.pa04.model.boardvalues.ShipType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a ship in the game.
 */
public class Ship {

  /**
   * The locations this ship is located at and the status of the ship at those locations.
   */
  private final Map<Coordinate, ShipStatus> locationStatuses = new HashMap<>();
  /**
   * The Type of ship.
   */
  private final ShipType type;

  /**
   * Instantiates a new Ship with the given type.
   *
   * @param type the type of ship
   */
  public Ship(ShipType type) {
    this.type = type;
  }

  /**
   * Updates the location of this ship with the given coordinate. Sets the default status of the
   * ship at this location to not hit.
   *
   * @param coordinate the coordinate to add to the ship locations
   */
  public void updateLocations(Coordinate coordinate) {
    if (this.locationStatuses.size() >= this.type.getSize()) {
      throw new IllegalStateException("Ship size exceeded");
    }
    this.locationStatuses.put(coordinate, ShipStatus.NOT_HIT);
  }

  /**
   * Determines if this ship is sunk based on the locations that the ship is not hit at.
   *
   * @return the boolean representing whether this ship is not sunk
   */
  public boolean isNotSunk() {
    return this.locationStatuses.values().stream()
        .anyMatch((ShipStatus status) -> status == ShipStatus.NOT_HIT);
  }

  /**
   * Returns a representation of this cell ship as a single string.
   *
   * @return the string representation of this ship
   */
  @Override
  public String toString() {
    return this.type.toString();
  }

  /**
   * Determines the length of this ship based on its type.
   *
   * @return the int length of this ship
   */
  public int size() {
    return this.type.getSize();
  }

  /**
   * Determines the list of coordinates this ship is located at.
   *
   * @return the list of coordinates of this ship
   */
  public List<Coordinate> coordinateList() {
    return new ArrayList<>(this.locationStatuses.keySet());
  }

  /**
   * Determines what the status of this ship is at the given coordinate.
   *
   * @param coordinate the coordinate to check the status of
   * @return the ship status at the given coordinate
   */
  public ShipStatus statusAt(Coordinate coordinate) {
    return this.locationStatuses.get(coordinate);
  }

  /**
   * Changes the status of the ship at the given coordinate to be hit.
   *
   * @param coordinate the coordinate the ship has been hit at
   */
  public void takeHit(Coordinate coordinate) {
    this.locationStatuses.replace(coordinate, ShipStatus.HIT);
  }

  /**
   * Gets the type of this ship.
   *
   * @return the type of this ship.
   */
  public ShipType getType() {
    return this.type;
  }

  /**
   * gets the starting coordinate for this ship
   *
   * @return the coordinate that is top-most or left-most for the ship placement
   */
  public Coordinate getStartCoordinate() {
    List<Coordinate> coordinates = new ArrayList<>(this.locationStatuses.keySet());
    coordinates.sort(Comparator.comparingInt(Coordinate::xvalue)
        .thenComparingInt(Coordinate::yvalue));
    return coordinates.get(0);
  }

  /**
   * checks the direction of the ship by seeing if the all the x coords are one value or if all the
   * y coords are one value
   *
   * @return the direction of this ship
   */
  public Direction getDirection() {
    List<Coordinate> coords = new ArrayList<>(this.locationStatuses.keySet());
    Coordinate firstCoord = coords.get(0);
    Coordinate secondCoord = coords.get(1);
    Direction result = Direction.HORIZONTAL;
    if (firstCoord.xvalue() == secondCoord.xvalue()) {
      result = Direction.VERTICAL;
    }
    return result;
  }
}


