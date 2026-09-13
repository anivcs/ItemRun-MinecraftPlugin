package io.papermc.testplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;

public class TeleportPlayer {
    public static boolean notOceanSpawn(Player player, int minDistance, int maxDistance) {
        World world = player.getWorld();
        Location origin = player.getLocation();
        Random random = new Random();

        for (int attempts = 0; attempts < 20; attempts++) {

            double angle = random.nextDouble() * 2 * Math.PI;
            int distance = minDistance + random.nextInt(maxDistance - minDistance);
            int targetX = origin.getBlockX() + (int) (Math.cos(angle) * distance);
            int targetZ = origin.getBlockZ() + (int) (Math.sin(angle) * distance);

            Block highestBlock = world.getHighestBlockAt(targetX, targetZ);
            Biome biome = highestBlock.getBiome();

            if(!isOceanBiome(biome) && highestBlock.getType() != Material.WATER && highestBlock.getType() != Material.LAVA) {
                Location safeLocation = highestBlock.getLocation().add(0.5,1.0,0.5);
                safeLocation.setPitch(origin.getPitch());
                safeLocation.setYaw(origin.getYaw());

                player.teleport(safeLocation);
                player.getWorld().setSpawnLocation(safeLocation);
                world.setTime(1000);
                return true;
            }

        }
        return false;
    }

    public static void teleportPlayer(Player from, Player to) {
        from.teleport(to);
    }


    private static boolean isOceanBiome(Biome biome) {
        String name = biome.name ();
        return name.contains("OCEAN") || name.contains("DEEP_");
    }
}
