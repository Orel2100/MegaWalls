package me.orel.database;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private int kills;
    private int deaths;
    private int coins;
    private int xp;
    private List<String> unlockedKits;

    public PlayerData(UUID uuid, int kills, int deaths, int coins, int xp, List<String> unlockedKits) {
        this.uuid = uuid;
        this.kills = kills;
        this.deaths = deaths;
        this.coins = coins;
        this.xp = xp;
        this.unlockedKits = unlockedKits;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public List<String> getUnlockedKits() {
        return unlockedKits;
    }

    public void addUnlockedKit(String kitName) {
        this.unlockedKits.add(kitName);
    }
}
