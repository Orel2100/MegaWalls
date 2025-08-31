package me.orel.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {

    private final MegawallsFFA plugin;
    private HikariDataSource dataSource;

    public DatabaseManager(MegawallsFFA plugin) {
        this.plugin = plugin;
    }

    public void connect() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + plugin.getConfig().getString("database.host") + ":" + plugin.getConfig().getInt("database.port") + "/" + plugin.getConfig().getString("database.database"));
        config.setUsername(plugin.getConfig().getString("database.username"));
        config.setPassword(plugin.getConfig().getString("database.password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public void disconnect() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    public void createTables() {
        try (Connection connection = dataSource.getConnection()) {
            String playerDataSql = "CREATE TABLE IF NOT EXISTS player_data (" +
                    "uuid VARCHAR(36) NOT NULL PRIMARY KEY," +
                    "kills INT NOT NULL DEFAULT 0," +
                    "deaths INT NOT NULL DEFAULT 0," +
                    "coins INT NOT NULL DEFAULT 0," +
                    "xp INT NOT NULL DEFAULT 0" +
                    ");";
            try (PreparedStatement statement = connection.prepareStatement(playerDataSql)) {
                statement.execute();
            }

            String unlockedKitsSql = "CREATE TABLE IF NOT EXISTS player_unlocked_kits (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "player_uuid VARCHAR(36) NOT NULL," +
                    "kit_name VARCHAR(255) NOT NULL," +
                    "FOREIGN KEY (player_uuid) REFERENCES player_data(uuid)" +
                    ");";
            try (PreparedStatement statement = connection.prepareStatement(unlockedKitsSql)) {
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerData getPlayerData(Player player) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM player_data WHERE uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, player.getUniqueId().toString());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int kills = resultSet.getInt("kills");
                    int deaths = resultSet.getInt("deaths");
                    int coins = resultSet.getInt("coins");
                    int xp = resultSet.getInt("xp");
                    List<String> unlockedKits = getUnlockedKits(player.getUniqueId());
                    return new PlayerData(player.getUniqueId(), kills, deaths, coins, xp, unlockedKits);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new PlayerData(player.getUniqueId(), 0, 0, 0, 0, new ArrayList<>()); // Return default data if not found
    }

    private List<String> getUnlockedKits(UUID uuid) {
        List<String> unlockedKits = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT kit_name FROM player_unlocked_kits WHERE player_uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, uuid.toString());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    unlockedKits.add(resultSet.getString("kit_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unlockedKits;
    }

    public void savePlayerData(PlayerData playerData) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO player_data (uuid, kills, deaths, coins, xp) VALUES (?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE kills = ?, deaths = ?, coins = ?, xp = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, playerData.getUuid().toString());
                statement.setInt(2, playerData.getKills());
                statement.setInt(3, playerData.getDeaths());
                statement.setInt(4, playerData.getCoins());
                statement.setInt(5, playerData.getXp());
                statement.setInt(6, playerData.getKills());
                statement.setInt(7, playerData.getDeaths());
                statement.setInt(8, playerData.getCoins());
                statement.setInt(9, playerData.getXp());
                statement.executeUpdate();
            }

            // Save unlocked kits
            String deleteSql = "DELETE FROM player_unlocked_kits WHERE player_uuid = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
                statement.setString(1, playerData.getUuid().toString());
                statement.executeUpdate();
            }

            String insertSql = "INSERT INTO player_unlocked_kits (player_uuid, kit_name) VALUES (?, ?)";
            for (String kitName : playerData.getUnlockedKits()) {
                try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
                    statement.setString(1, playerData.getUuid().toString());
                    statement.setString(2, kitName);
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
