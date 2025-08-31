package me.orel.database;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    private static PlayerDataManager instance;
    private final HashMap<UUID, PlayerData> playerDataMap;

    private PlayerDataManager() {
        this.playerDataMap = new HashMap<>();
    }

    public static PlayerDataManager getInstance() {
        if (instance == null) {
            instance = new PlayerDataManager();
        }
        return instance;
    }

    public void addPlayerData(PlayerData playerData) {
        playerDataMap.put(playerData.getUuid(), playerData);
    }

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }

    public void removePlayerData(Player player) {
        playerDataMap.remove(player.getUniqueId());
    }
}
