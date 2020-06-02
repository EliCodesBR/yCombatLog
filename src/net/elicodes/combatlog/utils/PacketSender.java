package net.elicodes.combatlog.utils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.Packet;

@SuppressWarnings("rawtypes")
public class PacketSender {
	
	public static void sendPacket(final Packet pa, final Player p) {
        final CraftPlayer cp = (CraftPlayer)p;
        cp.getHandle().playerConnection.sendPacket(pa);
    }
}
