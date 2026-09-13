package io.papermc.testplugin;

import io.papermc.testplugin.events.InventoryEvents;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ItemRun extends JavaPlugin implements Listener {



    FreezeListener freezer = new FreezeListener();
    ItemListener itemDetector = new ItemListener(this);
    ItemGenerator itemGenerator = new ItemGenerator((ArrayList<String>) fetchItems("items.json"));
    private final ArrayList<Listener> listeners = new ArrayList<>(List.of(this, freezer, itemDetector));

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
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
    }




    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.getPlayer().sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
        PlayerScoreboard.showScoreboard(player);
        player.sendMessage(net.kyori.adventure.text.minimessage.MiniMessage.miniMessage().deserialize(
                "\n" +
                "<yellow><bold>Welcome to ItemRun!</bold></yellow>\n" +
                        "<gray>======================</gray>\n" +
                        "Race your friends to \n" +
                        "gather a random item \n" +
                        "without dying!\n" +
                        "<yellow>Play with up to 4 players!</yellow>\n" +
                        "<gray>----------------------</gray>\n" +
                        "Before starting, make sure to\n" +
                        "assign yourselves into teams.\n" +
                        "Assign in chat:\n" +
                        "\n" +
                        "<red><u>/team join hunters</u></red>\n" +
                        "<blue><u>/team join runner</u></blue>\n" +
                        "\n" +
                        "To start game type in:\n" +
                        "\n" +
                        "<green><u>/itemrun start</u></green>\n" +
                        "\n" +
                        "Have fun!"
        ));

    }


}
