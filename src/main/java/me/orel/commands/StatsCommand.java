package me.orel.commands;

import me.orel.database.PlayerData;
import me.orel.database.PlayerDataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    private final PlayerDataManager playerDataManager;

    public StatsCommand() {
        this.playerDataManager = PlayerDataManager.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = playerDataManager.getPlayerData(player);

        if (playerData == null) {
            player.sendMessage(ChatColor.RED + "Could not retrieve your stats.");
            return true;
        }

        player.sendMessage(ChatColor.GOLD + "--- Your Stats ---");
        player.sendMessage(ChatColor.AQUA + "Kills: " + ChatColor.WHITE + playerData.getKills());
        player.sendMessage(ChatColor.AQUA + "Deaths: " + ChatColor.WHITE + playerData.getDeaths());
        player.sendMessage(ChatColor.AQUA + "Coins: " + ChatColor.WHITE + playerData.getCoins());
        player.sendMessage(ChatColor.AQUA + "XP: " + ChatColor.WHITE + playerData.getXp());
        player.sendMessage(ChatColor.GOLD + "------------------");

        return true;
    }
}
