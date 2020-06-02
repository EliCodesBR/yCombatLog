package net.elicodes.combatlog.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.elicodes.combatlog.Main;

public class PlayerChat implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        if (!Main.Block_Chat) {
            return;
        }
        String msg = e.getMessage();
        if (PlayerDamager.combat.containsKey(p)) {
            for (final String list : Main.allow) {
                if (msg.startsWith("/") && msg.contains(list)) {
                	e.setCancelled(true);
                	p.sendMessage(Main.Deny_Cmd);
                }
            }
        }
    }
}
