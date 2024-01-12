package cs3500.pa04.model.boardvalues;

/**
 * The enum Cell type which represents the state of a board cell.
 */
public enum CellState implements BoardValue {
  /**
   * Hit cell type.
   */
  HIT,
  /**
   * Miss cell type.
   */
  MISS,
  /**
   * Empty cell type.
   */
  EMPTY;

  /**
   * Returns a representation of this cell state as a single string.
   *
   * @return the string representation of this value
   */
  @Override
  public String toString() {
    return switch (this) {
      case HIT -> "*";
      case MISS -> ".";
      default -> "_";
    };
  }
}
