package cs3500.pa04.json.datadefinitions;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * represents a fleet as a Json in a game of BattleSalvo
 *
 * @param fleet which is a list of ShipJsons
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipJson> fleet) {

}


