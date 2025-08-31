package me.orel.kits.implementation;

import me.orel.kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Archer extends Kit {

    public Archer() {
        super("Archer", "A skilled marksman.", 100, new ArrayList<>(), new ArrayList<>());
        getItems().add(new ItemStack(Material.BOW));
        getItems().add(new ItemStack(Material.ARROW, 16));
    }
}
