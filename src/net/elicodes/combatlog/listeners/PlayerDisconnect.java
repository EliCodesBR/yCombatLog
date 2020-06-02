package net.elicodes.combatlog.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import net.elicodes.combatlog.Main;

public class PlayerDisconnect implements Listener
{
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (PlayerDamager.combat.containsKey(p)) {
            PlayerDamager.combat.get(p).cancel();
            PlayerDamager.combat.remove(p);
            Bukkit.broadcastMessage(Main.Broad_cast.replace("@player", p.getName()));
            ItemStack[] contents;
            for (int length = (contents = p.getInventory().getContents()).length, i = 0; i < length; ++i) {
                final ItemStack ik = contents[i];
                if (ik != null && ik.getType() != Material.AIR) {
                    p.getWorld().dropItemNaturally(p.getLocation(), ik);
                }
            }
            p.getInventory().clear();
            ItemStack[] armorContents;
            for (int length2 = (armorContents = p.getInventory().getArmorContents()).length, j = 0; j < length2; ++j) {
                final ItemStack ik = armorContents[j];
                if (ik != null && ik.getType() != Material.AIR) {
                    p.getWorld().dropItemNaturally(p.getLocation(), ik);
                }
            }
            p.getInventory().setArmorContents((ItemStack[])null);
            p.updateInventory();
        }
    }
}
