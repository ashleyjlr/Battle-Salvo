package cs3500.pa04.json.datadefinitions;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coordinate;
import java.util.List;

/**
 * represents a volley as a Json for a game of BattleSalvo
 *
 * @param volley which is a list of coordinates
 */
public record VolleyJson(
    @JsonProperty("coordinates") List<Coordinate> volley) {

}
