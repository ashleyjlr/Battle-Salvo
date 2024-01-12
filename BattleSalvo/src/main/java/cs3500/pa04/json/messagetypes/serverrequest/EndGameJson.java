package cs3500.pa04.json.messagetypes.serverrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameResult;

/**
 * represents the end game method json on for the server request
 *
 * @param result the result of the game
 * @param reason the reason for the game ending
 */
public record EndGameJson(
        @JsonProperty("result") GameResult result,
        @JsonProperty("reason") String reason) {
}
