package io.papermc.testplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ItemRun extends JavaPlugin implements Listener {



    FreezeListener freezer = new FreezeListener();
    ItemListener itemDetector = new ItemListener(this);
    ItemGenerator itemGenerator = new ItemGenerator((ArrayList<String>) fetchItems("items.json"));
    private final ArrayList<Listener> listeners = new ArrayList<>(List.of(this, freezer, itemDetector));
    List<Team> teams;

    private List<String> fetchItems(String filename) {
        ItemsData itemsData = JsonParser.parseStream(this.getResource(filename), ItemsData.class);
        assert itemsData != null;
        return itemsData.getItems();
    }

    private void setupListeners(ArrayList<Listener> listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onEnable() {
        this.setupListeners(listeners);
        Objects.requireNonNull(this.getCommand("itemrun")).setExecutor(new CommandExecutor(this));
        teams = Teams.createTeams();
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
        PlayerScoreboard.showScoreboard(player);

    }

    @Override
    public void onDisable() {
        Teams.deleteTeams(teams);
    }


}
