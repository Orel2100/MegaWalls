package me.orel.shop;

import me.orel.kits.Kit;
import me.orel.kits.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ShopManager {

    private final KitManager kitManager;

    public ShopManager() {
        this.kitManager = KitManager.getInstance();
    }

    public void openShop(Player player) {
        Inventory shop = Bukkit.createInventory(null, 9, "Kit Shop");

        for (Kit kit : kitManager.getKits()) {
            ItemStack item = new ItemStack(Material.DIAMOND_SWORD); // Placeholder icon
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(kit.getName());
            List<String> lore = new ArrayList<>();
            lore.add(kit.getDescription());
            lore.add("");
            lore.add("Price: " + kit.getPrice() + " coins");
            meta.setLore(lore);
            item.setItemMeta(meta);
            shop.addItem(item);
        }

        player.openInventory(shop);
    }
}
