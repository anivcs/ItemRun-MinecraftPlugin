package io.papermc.testplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.Set;


public class TeamsData {
    private final Team runner;
    private final Team hunters;

    public TeamsData(Team runner, Team hunters) {
        this.runner = runner;
        this.hunters = hunters;
    }

    public List<Team> getTeams() {
        return List.of(runner, hunters);
    }

    public Team getTeam(TeamName teamName) {
        return switch (teamName) {
            case RUNNER -> runner;
            case HUNTERS -> hunters;
        };

    }

    public Set<String> getPlayers() {
        Set<String> players = runner.getEntries();
        players.addAll(hunters.getEntries());
        return players;
    }

    public Set<String> getPlayers(TeamName teamName) {
        return switch (teamName) {
            case RUNNER -> runner.getEntries();
            case HUNTERS -> hunters.getEntries();
        };
    }

    public boolean contains(Player player) {
        return getPlayers().contains(player.getName());
    }

    public boolean contains(Player player, TeamName teamName) {
        return getPlayers(teamName).contains(player.getName());
    }


    public void configTeam(TeamName teamName) {
        switch (teamName) {
            case RUNNER:
                runner.setColor(ChatColor.BLUE);
                runner.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            case HUNTERS:
                hunters.setColor(ChatColor.RED);
                hunters.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        }
    }

    public boolean validateTeams() {


        for (Team team : getTeams()) {
            if (team.getEntries().isEmpty()) {
                return false;
            }
            if (team.getName().equals("Runner") && team.getEntries().size() > 1) {
                return false;
            }
        }
        return true;
    }

}
