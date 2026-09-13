package io.papermc.testplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class GeneratorScreen implements InventoryHolder{
    ItemRun plugin;

    private final int spinningIndex = 4;
    Inventory inv; // Middle of a 1 by 9 inventory

    public GeneratorScreen(ItemRun plugin) {

        this.plugin = plugin;
        inv = Bukkit.createInventory(this, 9, ChatColor.GOLD + "Item Generator");

    }

    // plugin.itemGenerator
    public void openWheel(Player player, String targetItem) {


        String itemName = plugin.itemGenerator.generateNextItem();
        inv.setItem(spinningIndex, new ItemStack(Material.matchMaterial(itemName)));


        player.openInventory(inv);

        new BukkitRunnable() {
            int delay = 1;
            int ticksElapsed = 0;
            int maxTicks = 60;

            @Override
            public void run() {
                ticksElapsed += delay;
                if (ticksElapsed >= maxTicks) {
                    inv.setItem(spinningIndex, new ItemStack(Material.matchMaterial(targetItem)));
                    this.cancel();
                    return;
                }

                String itemName;
                for (int i = 8; i > 0; i--) {
                    itemName = plugin.itemGenerator.generateNextItem();
                    inv.setItem(spinningIndex, new ItemStack(Material.matchMaterial(itemName)));
                }
                if (ticksElapsed > 40) {
                    delay = 5;
                } else if (ticksElapsed > 20) {
                    delay = 3;
                }


            }
        }.runTaskTimer(plugin, 0L, 2L);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
