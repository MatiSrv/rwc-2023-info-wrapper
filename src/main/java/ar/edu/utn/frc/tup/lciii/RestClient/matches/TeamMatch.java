package ar.edu.utn.frc.tup.lciii.RestClient.matches;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamMatch {
    private Long id;
    private Integer points;
    private Integer tries;
    @JsonProperty("yellow_cards")
    private Integer yellowCards;
    @JsonProperty("red_cards")
    private Integer redCards;
}
