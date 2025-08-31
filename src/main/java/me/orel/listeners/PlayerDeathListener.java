package me.orel.listeners;

import me.orel.MegawallsFFA;
import me.orel.database.PlayerData;
import me.orel.database.PlayerDataManager;
import me.orel.kits.KitManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerDeathListener implements Listener {

    private final MegawallsFFA plugin;
    private final KitManager kitManager;
    private final PlayerDataManager playerDataManager;

    public PlayerDeathListener(MegawallsFFA plugin) {
        this.plugin = plugin;
        this.kitManager = KitManager.getInstance();
        this.playerDataManager = PlayerDataManager.getInstance();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        PlayerData victimData = playerDataManager.getPlayerData(player);
        if (victimData != null) {
            victimData.setDeaths(victimData.getDeaths() + 1);
        }

        if (killer != null) {
            event.setDeathMessage(player.getName() + " was slain by " + killer.getName());
            PlayerData killerData = playerDataManager.getPlayerData(killer);
            if (killerData != null) {
                int coinsPerKill = plugin.getConfig().getInt("game.coins-per-kill", 10);
                int xpPerKill = plugin.getConfig().getInt("game.xp-per-kill", 50);

                killerData.setKills(killerData.getKills() + 1);
                killerData.setCoins(killerData.getCoins() + coinsPerKill);
                killerData.setXp(killerData.getXp() + xpPerKill);
                killer.sendMessage("You earned " + coinsPerKill + " coins and " + xpPerKill + " XP for killing " + player.getName());
                killer.setExp((float) killerData.getXp() / 1000); // Example: 1000 XP for next level
                killer.setLevel(killerData.getXp());
            }
        } else {
            event.setDeathMessage(player.getName() + " died.");
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
                player.getInventory().clear();
                kitManager.getPlayerKit(player).getItems().forEach(item -> player.getInventory().addItem(item));
            }
        }.runTaskLater(plugin, 20L); // 1 second delay (20 ticks)
    }
}
