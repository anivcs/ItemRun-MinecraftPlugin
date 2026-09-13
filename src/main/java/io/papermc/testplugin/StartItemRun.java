package io.papermc.testplugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;


public class StartItemRun {
    Player runner;
    List<Player> hunters;
    ItemRun plugin;


    public StartItemRun(Player runner, List<Player> hunters, ItemRun plugin) {
        this.hunters = hunters;
        this.runner = runner;
        this.plugin = plugin;
    }

    private void teleportPlayers() {

        boolean success = TeleportPlayer.notOceanSpawn(runner, 950, 1050);

        if (!success) {
            runner.sendMessage(ChatColor.DARK_RED + "Could not find a safe location to spawn. Starting in current location");
            return;
        }
        for (Player hunter : hunters) {
            TeleportPlayer.teleportPlayer(hunter, runner);
        }

    }

    private void giveBlindness(LivingEntity player, int seconds) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, seconds * 20, 1));
    }


    private void runnerHeadstart(int seconds) {
        for (Player hunter : hunters) {
            this.giveBlindness(hunter, seconds);
            plugin.freezer.freezePlayer(hunter, seconds);
        }


    }

    private void setTargetItemAndPlayer() {
        String targetItem = plugin.itemGenerator.generateNextItem();
        GeneratorScreen screen = new GeneratorScreen(plugin);
        screen.openWheel(runner, targetItem);
        plugin.itemDetector.setTargetItem(targetItem);
        plugin.itemDetector.setPlayer(runner);
        runner.sendMessage(Component.text("You must find " + targetItem + "!", NamedTextColor.RED));

    }



    public void run() {
        this.teleportPlayers();
        this.setTargetItemAndPlayer();
        this.runnerHeadstart(10);


    }
}
