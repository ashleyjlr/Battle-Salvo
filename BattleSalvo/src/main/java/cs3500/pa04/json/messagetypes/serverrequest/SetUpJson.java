package cs3500.pa04.json.messagetypes.serverrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.boardvalues.ShipType;
import java.util.Map;

/**
 * represents the set-up method json for a server request
 *
 * @param width     represents the width of a board
 * @param height    represents the height of a board
 * @param fleetSpec represents the ships and corresponding amount of ships for this game to be
 *                  played with
 */
public record SetUpJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpec
) {

}
