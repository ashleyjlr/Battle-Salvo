package cs3500.pa04.model.boardvalues;

/**
 * Represents the types of ships available in the game.
 */
public enum ShipType implements BoardValue {
  /**
   * Carrier ship type.
   */
  CARRIER(6),
  /**
   * Battleship ship type.
   */
  BATTLESHIP(5),
  /**
   * Destroyer ship type.
   */
  DESTROYER(4),
  /**
   * Submarine ship type.
   */
  SUBMARINE(3);


  /**
   * The Size of the ship.
   */
  private final int size;

  /**
   * Instantiates a new Ship type.
   *
   * @param size the size of the ship type
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Returns a representation of this ship type as a single string.
   *
   * @return the string representation of this value
   */
  @Override
  public String toString() {
    return switch (this) {
      case CARRIER -> "C";
      case BATTLESHIP -> "B";
      case DESTROYER -> "D";
      default -> "S";
    };
  }

  /**
   * Gets the size of this ship type.
   *
   * @return the size of the type.
   */
  public int getSize() {
    return this.size;
  }
}
