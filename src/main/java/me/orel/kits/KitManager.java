package me.orel.kits;

import me.orel.kits.implementation.Archer;
import me.orel.kits.implementation.Warrior;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KitManager {

    private static KitManager instance;
    private final List<Kit> kits;
    private final HashMap<UUID, Kit> playerKits;

    private KitManager() {
        this.kits = new ArrayList<>();
        this.playerKits = new HashMap<>();
        registerKits();
    }

    public static KitManager getInstance() {
        if (instance == null) {
            instance = new KitManager();
        }
        return instance;
    }

    private void registerKits() {
        kits.add(new Warrior());
        kits.add(new Archer());
    }

    public List<Kit> getKits() {
        return kits;
    }

    public Kit getPlayerKit(Player player) {
        return playerKits.get(player.getUniqueId());
    }

    public void setPlayerKit(Player player, Kit kit) {
        playerKits.put(player.getUniqueId(), kit);
    }
}
