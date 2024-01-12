package cs3500.pa04.model.boardvalues;

/**
 * The enum Ship status, which represents the status of a ship.
 */
public enum ShipStatus implements BoardValue {
  /**
   * Hit ship status.
   */
  HIT,
  /**
   * Not hit ship status.
   */
  NOT_HIT;

  /**
   * Returns a representation of this ship status as a single string.
   *
   * @return the string representation of this value
   */
  @Override
  public String toString() {
    return (this == ShipStatus.HIT) ? CellState.HIT.toString() : "S";
  }
}
