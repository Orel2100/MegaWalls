package me.orel.listeners;

import me.orel.MegawallsFFA;
import me.orel.database.PlayerData;
import me.orel.database.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final MegawallsFFA plugin;
    private final PlayerDataManager playerDataManager;

    public PlayerQuitListener(MegawallsFFA plugin) {
        this.plugin = plugin;
        this.playerDataManager = PlayerDataManager.getInstance();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = playerDataManager.getPlayerData(player);
        if (playerData != null) {
            plugin.getDatabaseManager().savePlayerData(playerData);
            playerDataManager.removePlayerData(player);
        }
    }
}
