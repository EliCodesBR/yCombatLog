package net.elicodes.combatlog.utils;

import org.bukkit.entity.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;

@SuppressWarnings("rawtypes")
public class Title {
	
	public static void sendTitle(final Player p, final String title, final String subtitle, final int fadeIn, final int stay, final int fadeOut) {
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, (IChatBaseComponent)null, fadeIn, stay, fadeOut);
        PacketSender.sendPacket((Packet)packet, p);
        if (title != null) {
            packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}"));
            PacketSender.sendPacket((Packet)packet, p);
        }
        if (subtitle != null) {
            packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}"));
            PacketSender.sendPacket((Packet)packet, p);
        }
    }
    
    public static void sendTitle(final Player p, final String title, final int fadeIn, final int stay, final int fadeOut) {
        PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, (IChatBaseComponent)null, fadeIn, stay, fadeOut);
        PacketSender.sendPacket((Packet)packet, p);
        if (title != null) {
            packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}"));
            PacketSender.sendPacket((Packet)packet, p);
        }
    }
    
    public static void sendTitle(final Player p, final String title, final String subtitle) {
        if (title != null) {
            PacketSender.sendPacket((Packet)new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}")), p);
        }
        if (subtitle != null) {
            PacketSender.sendPacket((Packet)new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}")), p);
        }
    }
    
    public static void sendTitleTime(final Player p, final int fadeIn, final int stay, final int fadeOut) {
        PacketSender.sendPacket((Packet)new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, (IChatBaseComponent)null, fadeIn, stay, fadeOut), p);
    }
    
    public static void resetTitle(final Player p) {
        PacketSender.sendPacket((Packet)new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.RESET, (IChatBaseComponent)null), p);
    }
    
    public static void ActionBar(final String text, final Player p) {
        final PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte)2);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packet);
    }
}
