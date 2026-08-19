package io.papermc.testplugin;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.List;



public class Teams {

    static List<Team> createTeams() {
        // Creates teams hunter and runner for players to join
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        Team runner = board.registerNewTeam("Runners");
        runner.setColor(ChatColor.BLUE);
        Team hunter = board.registerNewTeam("Hunters");
        hunter.setColor(ChatColor.RED);
        return List.of(runner, hunter);
    }

    static void deleteTeams(List<Team> teams) {
        for (Team team : teams) {
            team.unregister();
        }
    }
}
