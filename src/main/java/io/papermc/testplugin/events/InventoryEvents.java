package io.papermc.testplugin.events;

import io.papermc.testplugin.GeneratorScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static org.bukkit.Bukkit.getLogger;


public class InventoryEvents implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        getLogger().info(e.getClickedInventory().toString());
        getLogger().info("holder: " + e.getClickedInventory().getHolder().toString());

        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof GeneratorScreen) {
            getLogger().info("in correct inventory");
            e.setCancelled(true);
        }
    }
}
