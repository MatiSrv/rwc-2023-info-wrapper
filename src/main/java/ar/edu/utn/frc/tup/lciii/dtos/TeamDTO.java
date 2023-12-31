package ar.edu.utn.frc.tup.lciii.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ar.edu.utn.frc.tup.lciii.RestClient.matches.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"team_Id", "team_name", "country", "matches_played", "wins", "draws", "losses", "points_for", "points_against", "points_differential", "tries_made", "bonus_points", "points", "total_yellow_cards", "total_red_cards"})
public class TeamDTO {

    @JsonProperty("team_Id")
    private Long teamId;

    @JsonProperty("team_name")
    private String teamName;

    private String country;

    @JsonProperty("matches_played")
    private Integer matchesPlayed;

    private Integer wins;
    private Integer draws;
    private Integer losses;

    @JsonProperty("points_for")
    private Integer pointsFor;
    @JsonProperty("points_against")
    private Integer pointsAgainst;

    @JsonProperty("points_differential")
    private Integer pointsDifferential;

    @JsonProperty("tries_made")
    private Integer triesMade;

    @JsonProperty("bonus_points")
    private Integer bonusPoints;

    private Integer points;

    @JsonProperty("total_yellow_cards")
    private Integer totalYellowCards;

    @JsonProperty("total_red_cards")    
    private Integer totalRedCards;


}
