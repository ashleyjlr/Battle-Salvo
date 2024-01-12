package cs3500.pa04.controller;

import cs3500.pa04.model.Coordinate;
import cs3500.pa04.model.boardvalues.ShipType;
import java.util.Map;

/**
 * Setups up the board size and fleet size for the user.
 */
public interface GameSetup {

  /**
   * Sets up the board size.
   *
   * @return the board size from the setup
   */
  Coordinate setupBoardSize();

  /**
   * Sets up the fleet size.
   *
   * @return the map between ship type to integer representing the amount of each type of ship
   */
  Map<ShipType, Integer> setupFleetSize();

}
