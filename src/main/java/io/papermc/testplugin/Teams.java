package io.papermc.testplugin;


import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.World;

import static org.bukkit.Bukkit.getLogger;

import java.util.*;


public class Teams {

    static TeamsData createTeams() {
        // Creates teams hunter and runner for players to join
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Team runner = board.registerNewTeam("Runner");
        Team hunters = board.registerNewTeam("Hunters");
        TeamsData teamsData = new TeamsData(runner, hunters);
        teamsData.configTeam(TeamName.RUNNER);
        teamsData.configTeam(TeamName.HUNTERS);


        return teamsData;
    }

    static void deleteTeams(List<Team> teams) {
        for (Team team : teams) {
            team.unregister();
        }
    }







}
