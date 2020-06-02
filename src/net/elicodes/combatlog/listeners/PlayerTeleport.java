package net.elicodes.combatlog.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import net.elicodes.combatlog.Main;

public class PlayerTeleport implements Listener
{
    @EventHandler
    public void onTeleport(final PlayerTeleportEvent e) {
        final Player p = e.getPlayer();
        if (!Main.Block_EnderPearl) {
            return;
        }
        if (e.getCause().equals((Object)PlayerTeleportEvent.TeleportCause.ENDER_PEARL) && PlayerDamager.combat.containsKey(p)) {
            e.setCancelled(true);
            p.sendMessage(Main.Deny_EnderPearl);
        }
    }
}
