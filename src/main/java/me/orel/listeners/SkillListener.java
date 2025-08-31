package me.orel.listeners;

import me.orel.database.PlayerData;
import me.orel.database.PlayerDataManager;
import me.orel.kits.Kit;
import me.orel.kits.KitManager;
import me.orel.kits.skill.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SkillListener implements Listener {

    private final KitManager kitManager;
    private final PlayerDataManager playerDataManager;

    public SkillListener() {
        this.kitManager = KitManager.getInstance();
        this.playerDataManager = PlayerDataManager.getInstance();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Kit kit = kitManager.getPlayerKit(player);
            if (kit != null && !kit.getSkills().isEmpty()) {
                // For simplicity, assume the first skill is the active one
                Skill skill = kit.getSkills().get(0);
                PlayerData playerData = playerDataManager.getPlayerData(player);
                if (playerData.getXp() >= skill.getXpCost()) {
                    playerData.setXp(playerData.getXp() - skill.getXpCost());
                    skill.execute(player);
                    player.sendMessage("You used " + skill.getName() + "!");
                    // Update XP bar
                    player.setExp((float) playerData.getXp() / 1000);
                    player.setLevel(playerData.getXp());
                } else {
                    player.sendMessage("You don't have enough XP to use this skill.");
                }
            }
        }
    }
}
