package cs3500.pa04.json.messagetypes.clientresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents the join method json for the client response
 *
 * @param name     which is the name of the user
 * @param gameType which is the type of game being played
 */
public record JoinJson(
        @JsonProperty("name") String name,
        @JsonProperty("game-type") GameType gameType
) {
}
