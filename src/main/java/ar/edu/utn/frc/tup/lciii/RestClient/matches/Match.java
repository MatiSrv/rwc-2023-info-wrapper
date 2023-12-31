package ar.edu.utn.frc.tup.lciii.RestClient.matches;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {
    private Long id;
    private String date;
    private List<TeamMatch> teams;
    private String pool;
}




