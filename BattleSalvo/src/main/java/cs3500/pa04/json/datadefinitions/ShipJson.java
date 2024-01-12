package cs3500.pa04.json.datadefinitions;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coordinate;

/**
 * represents a ship as a Json in the game of BattleSalvo
 *
 * @param coord     which is the starting coord of a ship
 * @param length    which is the length of the ship
 * @param direction which is the direction the ship is placed
 */
public record ShipJson(
    @JsonProperty("coord") Coordinate coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") Direction direction) {

}
