package ar.edu.utn.frc.tup.lciii.RestClient.teams;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TeamClient {
    private final String url = "https://my-json-server.typicode.com/LCIV-2023/fake-api-rwc2023/teams";
    RestTemplate RestTemplate = new RestTemplate();

    public List<Team> getTeams() {
        Team[] teams =  RestTemplate.getForObject(url, Team[].class);
         return List.of(teams);
    }

}
