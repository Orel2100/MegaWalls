package me.orel.kits.skill.implementation;

import me.orel.kits.skill.Skill;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class DashSkill implements Skill {

    @Override
    public String getName() {
        return "Dash";
    }

    @Override
    public String getDescription() {
        return "Launch yourself forward.";
    }

    @Override
    public int getXpCost() {
        return 50;
    }

    @Override
    public void execute(Player player) {
        Vector direction = player.getLocation().getDirection();
        player.setVelocity(direction.multiply(2));
    }
}
