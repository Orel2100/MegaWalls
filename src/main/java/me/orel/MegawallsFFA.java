package me.orel;

import me.orel.commands.ShopCommand;
import me.orel.database.DatabaseManager;
import me.orel.gamemanager.GameManager;
import me.orel.listeners.PlayerDeathListener;
import me.orel.listeners.PlayerJoinListener;
import me.orel.listeners.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MegawallsFFA extends JavaPlugin {

    private GameManager gameManager;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.gameManager = GameManager.getInstance();
        this.databaseManager = new DatabaseManager(this);
        this.databaseManager.connect();
        this.databaseManager.createTables();


        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new me.orel.listeners.ShopListener(), this);
        getServer().getPluginManager().registerEvents(new me.orel.listeners.SkillListener(), this);

        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("stats").setExecutor(new me.orel.commands.StatsCommand());
        getCommand("megawallsffa").setExecutor(new me.orel.commands.MegaWallsFFACommand());

        getLogger().info("MegaWallsFFA has been enabled!");
    }

    @Override
    public void onDisable() {
        this.databaseManager.disconnect();
        getLogger().info("MegaWallsFFA has been disabled!");
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
