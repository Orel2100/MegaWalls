package me.orel.kits.skill;

import org.bukkit.entity.Player;

public interface Skill {

    String getName();
    String getDescription();
    int getXpCost();
    void execute(Player player);

}
