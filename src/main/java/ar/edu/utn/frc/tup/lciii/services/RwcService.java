package ar.edu.utn.frc.tup.lciii.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.tup.lciii.RestClient.matches.Match;
import ar.edu.utn.frc.tup.lciii.RestClient.matches.MatchClient;
import ar.edu.utn.frc.tup.lciii.RestClient.teams.Team;
import ar.edu.utn.frc.tup.lciii.RestClient.teams.TeamClient;
import ar.edu.utn.frc.tup.lciii.dtos.PoolDTO;
import ar.edu.utn.frc.tup.lciii.dtos.TeamDTO;

@Service
public class RwcService {

    @Autowired
    private MatchClient matchClient;
    @Autowired
    private TeamClient teamClient;

    /**
     * Retrieves a list of all pools.
     *
        * This method retrieves and returns a list of all pools, each represented by a
     * {@PoolDTO}.
     * The list includes information about the pool identifier and a list of teams
     * in each pool.
     *
     * @return A {@link List} of {@link PoolDTO} representing all pools.
     *         If no pools are available, the list will be empty.
     */
    public List<PoolDTO> getAllPools() {
        Integer pools = countPools();
        List<PoolDTO> poolList = new ArrayList<>();

        for (int i = 1; i <= pools; i++) {
            char letra = (char) ('A' + i - 1);
            String pool = String.valueOf(letra);
            PoolDTO poolDTO = getPoolById(pool);
            poolList.add(poolDTO);
        }

        return poolList;
    }

    private Integer countPools() {
    List<Match> matches = matchClient.getMatches();
    Set<String> uniquePools = new HashSet<>();

    for (Match match : matches) {
        char currentPool = match.getPool().charAt(0);
        if (currentPool >= 'A') {
            uniquePools.add(match.getPool());
        }
    }

    return uniquePools.size();
}

    public PoolDTO getPoolById(String id) {
        PoolDTO poolDTO = new PoolDTO();
        List<TeamDTO> teams = new ArrayList<>();
        List<Team> teamsByPool = getTeamsByPool(id);
        teamsByPool.forEach(team -> {
            TeamDTO teamDTO = new TeamDTO();
            List<Match> matchesByTeam = getMatchesByTeam(team.getId());
            Integer points = 0;
            teamDTO.setTeamId(team.getId());
            teamDTO.setTeamName(team.getName());
            teamDTO.setCountry(team.getCountry());
            teamDTO.setMatchesPlayed(calculateMatchesPlayed(team.getId()));
            teamDTO.setBonusPoints(0);
            teamDTO.setWins(calculateWins(team.getId(), matchesByTeam));
            teamDTO.setDraws(calculateDraws(team.getId(), matchesByTeam));
            teamDTO.setLosses(calculateLosses(teamDTO, matchesByTeam));
            points += calculatePoints(teamDTO);
            Integer pointsFor = calculatePointsFor(team.getId(), matchesByTeam);
            Integer pointsAgainst = calculatePointsAgainst(team.getId(), matchesByTeam);
            teamDTO.setPointsFor(pointsFor);
            teamDTO.setPointsAgainst(pointsAgainst);
            teamDTO.setPointsDifferential(pointsFor - pointsAgainst);
            teamDTO.setTriesMade(calculateTries(teamDTO, matchesByTeam));
            Integer bonusPoints = teamDTO.getBonusPoints();
            teamDTO.setPoints(points + bonusPoints);
            teamDTO.setTotalYellowCards(calculateYellowCards(team.getId(), matchesByTeam));
            teamDTO.setTotalRedCards(calculateRedCards(team.getId(), matchesByTeam));
            teams.add(teamDTO);
        });

        poolDTO.setPoolId(id);
        poolDTO.setTeams(teams);

        return poolDTO;

    }

    private Integer calculatePoints(TeamDTO teamDTO) {
        Integer wins = teamDTO.getWins() * 4;
        Integer draws = teamDTO.getDraws() * 2;

        return wins + draws;
    }

    private Integer calculateRedCards(Long id, List<Match> matchesByTeam) {
        Integer redCards = 0;
        for (Match match : matchesByTeam) {
            if (match.getTeams().get(0).getId().equals(id)) {
                redCards += match.getTeams().get(0).getRedCards();
            }
            if (match.getTeams().get(1).getId().equals(id)) {
                redCards += match.getTeams().get(1).getRedCards();
            }

        }
        return redCards;
    }

    private Integer calculateYellowCards(Long id, List<Match> matchesByTeam) {
        Integer yellowCards = 0;
        for (Match match : matchesByTeam) {
            if (match.getTeams().get(0).getId().equals(id)) {
                yellowCards += match.getTeams().get(0).getYellowCards();
            }
            if (match.getTeams().get(1).getId().equals(id)) {
                yellowCards += match.getTeams().get(1).getYellowCards();
            }

        }
        return yellowCards;
    }

    private Integer calculateTries(TeamDTO teamDTO, List<Match> matchesByTeam) {
       Integer tries = 0;
        for (Match match : matchesByTeam) {
            if (match.getTeams().get(0).getId().equals(teamDTO.getTeamId())) {
                tries += match.getTeams().get(0).getTries();
                if(tries>=4){
                    Integer bonusPoints = teamDTO.getBonusPoints();
                    teamDTO.setBonusPoints(bonusPoints+1);
                }
            }
            if (match.getTeams().get(1).getId().equals(teamDTO.getTeamId())) {
                tries += match.getTeams().get(1).getTries();
                if(tries>=4){
                    Integer bonusPoints = teamDTO.getBonusPoints();
                    teamDTO.setBonusPoints(bonusPoints+1);
                }
            }

        }
        return tries;
    }

    private Integer calculatePointsAgainst(Long id, List<Match> matchesByTeam) {
        Integer pointsAgainst = 0;
        for (Match match : matchesByTeam) {
            if (match.getTeams().get(0).getId().equals(id)) {
                pointsAgainst += match.getTeams().get(1).getPoints();
            }
            if (match.getTeams().get(1).getId().equals(id)) {
                pointsAgainst += match.getTeams().get(0).getPoints();
            }
        }

        return pointsAgainst;
    }

    private Integer calculatePointsFor(Long id, List<Match> matchesByTeam) {
        Integer pointsFor = 0;
        for (Match match : matchesByTeam) {
            if (match.getTeams().get(0).getId().equals(id)) {
                pointsFor += match.getTeams().get(0).getPoints();
            }
            if (match.getTeams().get(1).getId().equals(id)) {
                pointsFor += match.getTeams().get(1).getPoints();
            }

        }
        return pointsFor;
    }

    private Integer calculateLosses(TeamDTO teamDTO, List<Match> matchesByTeam) {
        Integer losses = 0;
        for (Match match : matchesByTeam) {
            if (match.getTeams().get(0).getId().equals(teamDTO.getTeamId())) {
                if (match.getTeams().get(0).getPoints() < match.getTeams().get(1).getPoints()) {
                    losses++;
                    Integer pointFor = match.getTeams().get(0).getPoints();
                    Integer pointsAgainst = match.getTeams().get(1).getPoints();
                    if (pointFor - pointsAgainst <= 7) {
                        Integer bonusPoints = teamDTO.getBonusPoints();
                        teamDTO.setBonusPoints(bonusPoints + 1);
                    }
                }
            }
            if (match.getTeams().get(1).getId().equals(teamDTO.getTeamId())) {
                if (match.getTeams().get(1).getPoints() < match.getTeams().get(0).getPoints()) {
                    losses++;
                      Integer pointFor = match.getTeams().get(1).getPoints();
                    Integer pointsAgainst = match.getTeams().get(0).getPoints();
                    if (pointFor - pointsAgainst <= 7) {
                        Integer bonusPoints = teamDTO.getBonusPoints();
                        teamDTO.setBonusPoints(bonusPoints + 1);
                    }
                }
            }

        }
        return losses;
    }

    private Integer calculateDraws(Long id, List<Match> matchesByTeam) {
        Integer draws = 0;
        for (Match match : matchesByTeam) {
            if (match.getTeams().get(0).getId().equals(id)) {
                if (match.getTeams().get(0).getPoints().equals(match.getTeams().get(1).getPoints())) {
                    draws++;
                }
            }

        }

        return draws;
    }

    private Integer calculateWins(Long id, List<Match> matchesByTeam) {
        Integer wins = 0;
        for (Match match : matchesByTeam) {
            if (match.getTeams().get(0).getId().equals(id)) {
                if (match.getTeams().get(0).getPoints() > match.getTeams().get(1).getPoints()) {
                    wins++;
                }
            }
            if (match.getTeams().get(1).getId().equals(id)) {
                if (match.getTeams().get(1).getPoints() > match.getTeams().get(0).getPoints()) {
                    wins++;
                }
            }

        }
        return wins;

    }

    private List<Match> getMatchesByTeam(Long id) {
        List<Match> matches = new ArrayList<>();
        List<Match> matchesPlayed = matchClient.getMatches();

        for (Match match : matchesPlayed) {
            if (match.getTeams().get(0).getId().equals(id) || match.getTeams().get(1).getId().equals(id)) {
                matches.add(match);
            }
        }
        return matches;
   
        
    }


    private Integer calculateMatchesPlayed(Long id) {
        Integer matches = 0;
        List<Match> matchesPlayed = matchClient.getMatches();

        for (Match match : matchesPlayed) {
            if (match.getTeams().get(0).getId().equals(id) || match.getTeams().get(1).getId().equals(id)) {
                matches++;
            }
        }
        return matches;
    }

    private List<Team> getTeamsByPool(String id) {
        List<Team> teams = teamClient.getTeams().stream().filter((x -> x.getPool().equals(id))).toList();
        return teams;
    }
}
