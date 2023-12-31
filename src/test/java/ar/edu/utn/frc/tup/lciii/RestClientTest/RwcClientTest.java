package ar.edu.utn.frc.tup.lciii.RestClientTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.edu.utn.frc.tup.lciii.RestClient.matches.Match;
import ar.edu.utn.frc.tup.lciii.RestClient.matches.MatchClient;
import ar.edu.utn.frc.tup.lciii.RestClient.teams.Team;
import ar.edu.utn.frc.tup.lciii.RestClient.teams.TeamClient;

@SpringBootTest
public class RwcClientTest {
    @Autowired
    private MatchClient matchClient;
    @Autowired
    private TeamClient teamClient;

    @Test 
    public void getMatchesTest() {
        List<Match> matches = matchClient.getMatches();
        assertNotNull(matches);
    }

    @Test
    public void getTeams() {
        List<Team> teams = teamClient.getTeams();
        assertNotNull(teams);
    }

}
