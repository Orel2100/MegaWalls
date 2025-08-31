package me.orel.listeners;

import me.orel.database.PlayerData;
import me.orel.database.PlayerDataManager;
import me.orel.kits.Kit;
import me.orel.kits.KitManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    private final KitManager kitManager;
    private final PlayerDataManager playerDataManager;

    public ShopListener() {
        this.kitManager = KitManager.getInstance();
        this.playerDataManager = PlayerDataManager.getInstance();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Kit Shop")) {
            return;
        }

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        String kitName = clickedItem.getItemMeta().getDisplayName();
        PlayerData playerData = playerDataManager.getPlayerData(player);

        for (Kit kit : kitManager.getKits()) {
            if (kit.getName().equals(kitName)) {
                if (playerData.getUnlockedKits().contains(kitName)) {
                    // Kit is already unlocked, just select it
                    kitManager.setPlayerKit(player, kit);
                    player.sendMessage("You have selected the " + kit.getName() + " kit.");
                    player.closeInventory();
                    player.getInventory().clear();
                    kit.getItems().forEach(item -> player.getInventory().addItem(item));
                } else {
                    // Kit is not unlocked, try to purchase it
                    if (playerData.getCoins() >= kit.getPrice()) {
                        playerData.setCoins(playerData.getCoins() - kit.getPrice());
                        playerData.addUnlockedKit(kitName);
                        player.sendMessage("You have purchased the " + kit.getName() + " kit!");
                        // Optionally, select the kit immediately after purchase
                        kitManager.setPlayerKit(player, kit);
                        player.getInventory().clear();
                        kit.getItems().forEach(item -> player.getInventory().addItem(item));
                    } else {
                        player.sendMessage("You don't have enough coins to purchase this kit.");
                    }
                    player.closeInventory();
                }
                break;
            }
        }
    }
}
