package ar.edu.utn.frc.tup.lciii.RestClient.matches;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MatchClient {
    private final String url = "https://my-json-server.typicode.com/LCIV-2023/fake-api-rwc2023/matches";
    RestTemplate restTemplate = new RestTemplate();

    public List<Match> getMatches() {
        Match[] matches =  restTemplate.getForObject(url, Match[].class);
        return List.of(matches);
    }

  
}
