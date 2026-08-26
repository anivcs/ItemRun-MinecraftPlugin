package io.papermc.testplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getName;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

    private final ItemRun plugin;

    public CommandExecutor(ItemRun plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("itemrun")) {
            return false;
        }

        if (args.length == 0) {
            return false;
        }

        return switch (args[0].toLowerCase()) {
            case "start" -> {
                if (sender instanceof Player starter) {
                    // Currently it is using the sender to do all commands.
                    // We want to have hunter and runner assigned before the game actually start.
                    // Check if there is at least one player as hunter and one player as runner.
                    // Edge Case: Make sure the hunter isn't the same player as the runner
                    // Create team using ./team join

                    if (! plugin.teams.validateTeams()) {
                        starter.sendMessage(ChatColor.RED + "Error: At least one of the teams is empty or there is more than one runner.");
                        yield true;
                    }
                    Player runner = null;
                    ArrayList<Player> hunters = new ArrayList<>();
                    for (Player player : starter.getWorld().getPlayers()) {
                        if (plugin.teams.contains(player, TeamName.HUNTERS) ) {
                            hunters.add(player);
                        }
                        else if (plugin.teams.contains(player, TeamName.RUNNER)) {
                            runner = player;
                        }
                        player.sendMessage(ChatColor.GREEN + "Game started!");
                    }



                    StartItemRun startItemRun = new StartItemRun(runner, hunters, plugin);
                    startItemRun.run();





                } else {
                    getLogger().info("This command must be run by a player!");
                }
                yield true;
            }
            case "pause" -> {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.sendMessage(ChatColor.GOLD + "Game Paused.");
                } else {
                    getLogger().info("This command must be run by a player!");
                }
                yield true;
            }
            case "resume" -> {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.sendMessage(ChatColor.GOLD + "Game Resumed");
                    getLogger().info("This command must be run by a player!");
                }
                yield true;
            }
            case "quit" -> {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.sendMessage(ChatColor.RED + "Game ended.");
                } else {
                    getLogger().info("This command must be run by a player!");
                }
                yield true;
            }
            case "team_runner" -> {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + player.getName() + " is added to Team Speedrunner.");
                } else {
                    getLogger().info("This command must be run by a player!");
                }
                yield true;
            }
            case "team_hunter" -> {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + player.getName() + " is added to Team Hunter.");
                } else {
                    getLogger().info("This command must be run by a player!");
                }
                yield true;
            }
            default -> {
                sender.sendMessage("Error.");
                yield false;
            }
        };



    }


}
