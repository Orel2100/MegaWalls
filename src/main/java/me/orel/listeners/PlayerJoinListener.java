package me.orel.listeners;

import me.orel.MegawallsFFA;
import me.orel.database.PlayerData;
import me.orel.database.PlayerDataManager;
import me.orel.gamemanager.GameManager;
import me.orel.gamemanager.GameState;
import me.orel.kits.KitManager;
import me.orel.kits.implementation.Warrior;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final MegawallsFFA plugin;
    private final GameManager gameManager;
    private final KitManager kitManager;
    private final PlayerDataManager playerDataManager;

    public PlayerJoinListener(MegawallsFFA plugin, GameManager gameManager) {
        this.plugin = plugin;
        this.gameManager = gameManager;
        this.kitManager = KitManager.getInstance();
        this.playerDataManager = PlayerDataManager.getInstance();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = plugin.getDatabaseManager().getPlayerData(player);
        playerDataManager.addPlayerData(playerData);

        kitManager.setPlayerKit(player, new Warrior()); // Set default kit
        player.getInventory().clear();
        kitManager.getPlayerKit(player).getItems().forEach(item -> player.getInventory().addItem(item));


        if (gameManager.getGameState() == GameState.WAITING) {
            // Logic for when the game is in the waiting state
            player.sendMessage("Welcome to MegaWallsFFA! The game is currently in the waiting state.");
        } else {
            // Logic for when the game is running
            player.sendMessage("Welcome to MegaWallsFFA! The game is currently in progress.");
        }
    }
}
