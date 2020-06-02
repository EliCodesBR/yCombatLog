package net.elicodes.combatlog.listeners;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.elicodes.combatlog.Main;
import net.elicodes.combatlog.utils.Title;

public class PlayerDamager implements Listener
{
    protected static HashMap<Player, BukkitTask> combat;
    
    static {
        PlayerDamager.combat = new HashMap<Player, BukkitTask>();
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onHit(final EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }
        if (e.getDamage() <= 0.1) {
            return;
        }
        final Player p = (Player)e.getEntity();
        final Player d = (Player)e.getDamager();
        if (p == d) {
            return;
        }
        if (PlayerDamager.combat.containsKey(p)) {
            PlayerDamager.combat.get(p).cancel();
            PlayerDamager.combat.remove(p);
        }
        else {
            p.sendMessage(Main.Entrou_em_comabate.replace("@player", d.getName()));
        }
        if (PlayerDamager.combat.containsKey(d)) {
            PlayerDamager.combat.get(d).cancel();
            PlayerDamager.combat.remove(d);
        }
        else {
            d.sendMessage(Main.Provocou_o_combate.replace("@player", p.getName()));
        }
        final BukkitTask p_t = new BukkitRunnable() {
            int time = Main.Combat_time;
            
            public void run() {
                if (this.time > 0) {
                    --this.time;
                    Title.ActionBar(Main.In_combat.replace("@tempo", new StringBuilder().append(this.time).toString()), p);
                }
                else {
                    if (PlayerDamager.combat.containsKey(p)) {
                        PlayerDamager.combat.remove(p);
                        p.sendMessage(Main.Batalha_finalizada);
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0L, 20L);
        PlayerDamager.combat.put(p, p_t);
        final BukkitTask d_t = new BukkitRunnable() {
            int time = Main.Combat_time;
            
            public void run() {
                if (this.time > 0) {
                    --this.time;
                    Title.ActionBar(Main.In_combat.replace("@tempo", new StringBuilder().append(this.time).toString()), d);
                }
                else {
                    if (PlayerDamager.combat.containsKey(d)) {
                        PlayerDamager.combat.remove(d);
                        d.sendMessage(Main.Batalha_finalizada);
                    }
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0L, 20L);
        PlayerDamager.combat.put(d, d_t);
    }
}
