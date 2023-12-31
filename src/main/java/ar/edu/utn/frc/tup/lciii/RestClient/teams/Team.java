package ar.edu.utn.frc.tup.lciii.RestClient.teams;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Team {    
    private Long id;
    private String name;
    private String country;
    @JsonProperty("world_ranking")
    private Integer worldRanking;
    private String pool;
}
