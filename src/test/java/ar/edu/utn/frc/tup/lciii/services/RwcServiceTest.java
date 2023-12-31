package ar.edu.utn.frc.tup.lciii.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import ar.edu.utn.frc.tup.lciii.RestClient.matches.Match;
import ar.edu.utn.frc.tup.lciii.RestClient.matches.MatchClient;
import ar.edu.utn.frc.tup.lciii.RestClient.matches.TeamMatch;
import ar.edu.utn.frc.tup.lciii.RestClient.teams.Team;
import ar.edu.utn.frc.tup.lciii.dtos.PoolDTO;
import ar.edu.utn.frc.tup.lciii.dtos.TeamDTO;

@SpringBootTest
public class RwcServiceTest {

    @MockBean
    private MatchClient matchClient;
    @SpyBean
    private RwcService rwcService;

    private Team team1;
    private Team team2;
    private Team team3;

    private Match match1;
    private Match match2;
    private Match match3;

    TeamMatch teamMatch1;
    TeamMatch teamMatch2;
    TeamMatch teamMatch3;
    TeamMatch teamMatch4;
    TeamMatch teamMatch5;
    TeamMatch teamMatch6;

    TeamDTO teamDTO; // Fix: Provide a variable name for the TeamDTO type declaration
    TeamDTO teamDTO2; // Fix: Provide a variable name for the TeamDTO type declaration

    List<Match> matches = new ArrayList<>();
    List<Match> matchesFrance = new ArrayList<>();
    List<Match> matchesNz = new ArrayList<>();

    List<TeamMatch> listTeamatch1 = new ArrayList<>();
    List<TeamMatch> listTeamMatch2 = new ArrayList<>();
    List<TeamMatch> listTeamMatch3 = new ArrayList<>();

    private List<Team> teams = new ArrayList<>();

    @BeforeEach
    void setUp() {

        //SET TEAMS
        team1 = new Team(1L, "Les Bleus", "France", 2, "A");
        team2 = new Team(2L, "The all blacks", "New Zealand", 4, "A");
        team3 = new Team(3L, "Gli Azurri", "Italy", 11, "A");
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);

        //SET EACH MATCH
        teamMatch1 = new TeamMatch(1L, 20, 4, 1, 0);
        teamMatch2 = new TeamMatch(2L, 0, 0, 0, 1);
        listTeamatch1.add(teamMatch1);
        listTeamatch1.add(teamMatch2);
        match1 = new Match(1L, null, listTeamatch1, "A");


        teamMatch3 = new TeamMatch(1L, 10, 2, 0, 0);
        teamMatch4 = new TeamMatch(3L, 0, 0, 0, 0);
        listTeamMatch2.add(teamMatch3);
        listTeamMatch2.add(teamMatch4);
        match2 = new Match(2L, null, listTeamMatch2, "A");


        teamMatch5 = new TeamMatch(2L, 5, 1, 0, 0);
        teamMatch6 = new TeamMatch(3L, 10, 2, 0, 0);
        listTeamMatch3.add(teamMatch5);
        listTeamMatch3.add(teamMatch6);
        match3 = new Match(3L, null, listTeamMatch3, "A");

        //Add match to list 
        matches.add(match1);
        matches.add(match2);
        matches.add(match3);

        //SET MATCHES BY TEAM
        //france
        matchesFrance.add(match1);
        matchesFrance.add(match2);

        //nz
        matchesNz.add(match1);
        matchesNz.add(match3);
    }

    @Test
    public void getPoolByIdTest() {
       String poolId = "A";

       PoolDTO poolDto = rwcService.getPoolById(poolId);
       // Assert
        assertNotNull(poolDto);
        assertEquals(poolId, poolDto.getPoolId());
    }


    //Private Methods

    @Test
    public void calculatePointsTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        //
        teamDTO = new TeamDTO(null, null, null, null, 2, 2
        ,null, null, null, null, null, null, null, null, null);

        teamDTO2 = new TeamDTO(null, null, null, null, 0, 1, null, null, null, null, null, null, null, null, null);

        Method method = RwcService.class.getDeclaredMethod("calculatePoints", TeamDTO.class);
        method.setAccessible(true);
        Integer result = (Integer)method.invoke(rwcService, teamDTO);
        Integer result2 = (Integer)method.invoke(rwcService, teamDTO2);


        assertEquals(12, result);
        assertNotEquals(6, result2);
        
    }

    @Test
    public void calculateRedCardsTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = RwcService.class.getDeclaredMethod("calculateRedCards", Long.class, List.class);
        method.setAccessible(true);
        Integer result = (Integer)method.invoke(rwcService, 1L, matchesFrance);
        Integer result2 = (Integer)method.invoke(rwcService, 2L, matchesNz);

        assertEquals(0, result);
        assertEquals(1, result2);
    }

     @Test
    public void calculateYellowCardsTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = RwcService.class.getDeclaredMethod("calculateYellowCards", Long.class, List.class);
        method.setAccessible(true);
        Integer result = (Integer)method.invoke(rwcService, 1L, matchesFrance);
        Integer result2 = (Integer)method.invoke(rwcService, 2L, matchesNz);

        assertEquals(1, result);
        assertEquals(0, result2);
    }

    @Test
    public void calculateTriesTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        teamDTO = new TeamDTO(1L, null, null, null, null, null, null, null, null, null, null, 0, null, null, null);

        Method method = RwcService.class.getDeclaredMethod("calculateTries", TeamDTO.class, List.class);
        method.setAccessible(true);
        Integer result = (Integer)method.invoke(rwcService, teamDTO, matchesFrance);

        assertEquals(6, result);
    }

    @Test
    public void calculatePointsAgainstTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Method method = RwcService.class.getDeclaredMethod("calculatePointsAgainst", Long.class, List.class);
        method.setAccessible(true);
        Integer result = (Integer)method.invoke(rwcService, 1L, matchesFrance);
        Integer result2 = (Integer)method.invoke(rwcService, 2L, matchesNz);

        assertEquals(0, result);
        assertEquals(30, result2);
    }

    
    @Test
    public void calculatePointsForTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Method method = RwcService.class.getDeclaredMethod("calculatePointsFor", Long.class, List.class);
        method.setAccessible(true);
        Integer result = (Integer)method.invoke(rwcService, 1L, matchesFrance);
        
        assertEquals(30, result);
    }
    
    @Test
   public void calculateLossesTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        teamDTO = new TeamDTO(1L, null, null, null, null, null, null, null, null, null, null, 0, null, null, null);
        teamDTO2 = new TeamDTO(2L, null, null, null, null, null, null, null, null, null, null, 0, null, null, null);
       Method method = RwcService.class.getDeclaredMethod("calculateLosses", TeamDTO.class, List.class);
       method.setAccessible(true);
       Integer result = (Integer)method.invoke(rwcService, teamDTO, matchesFrance);
       Integer result2 = (Integer)method.invoke(rwcService, teamDTO2, matchesNz);

       assertEquals(0, result);
       assertEquals(2, result2);
   }

    @Test
    public void calculateDrawsTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Method method = RwcService.class.getDeclaredMethod("calculateDraws", Long.class, List.class);
        method.setAccessible(true);
        Integer result = (Integer)method.invoke(rwcService, 1L, matchesFrance);
        
        assertEquals(0, result);
    }

      @Test
    public void calculateWinsTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Method method = RwcService.class.getDeclaredMethod("calculateWins", Long.class, List.class);
        method.setAccessible(true);
        Integer result = (Integer)method.invoke(rwcService, 1L, matchesFrance);
        Integer result2 = (Integer)method.invoke(rwcService, 2L, matchesNz);
        
        assertEquals(2, result);
        assertEquals(0, result2);
    }

    @Test
    public void getMatchesByTeamTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        when(matchClient.getMatches()).thenReturn(matches);


        Method method = RwcService.class.getDeclaredMethod("getMatchesByTeam", Long.class);
        method.setAccessible(true);
        Object result = method.invoke(rwcService, 1L); 
        
        assertEquals(matchesFrance, (List<Match>) result);
    }

     @Test
    public void calculateMatchesPlayedTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        when(matchClient.getMatches()).thenReturn(matches);


        Method method = RwcService.class.getDeclaredMethod("calculateMatchesPlayed", Long.class);
        method.setAccessible(true);
        int result = (int)method.invoke(rwcService, 1L); 
        
        assertEquals(2, result);
    }
}
