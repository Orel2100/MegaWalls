package me.orel.kits;

import me.orel.kits.skill.Skill;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Kit {

    private final String name;
    private final String description;
    private final int price;
    private final List<ItemStack> items;
    private final List<Skill> skills;

    public Kit(String name, String description, int price, List<ItemStack> items, List<Skill> skills) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.items = items;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public List<Skill> getSkills() {
        return skills;
    }
}
