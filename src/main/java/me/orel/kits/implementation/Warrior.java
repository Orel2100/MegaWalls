package me.orel.kits.implementation;

import me.orel.kits.Kit;
import me.orel.kits.skill.implementation.DashSkill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class Warrior extends Kit {

    public Warrior() {
        super("Warrior", "A fierce melee fighter.", 0, new ArrayList<>(), new ArrayList<>());
        getItems().add(new ItemStack(Material.STONE_SWORD));
        getSkills().add(new DashSkill());
    }
}
