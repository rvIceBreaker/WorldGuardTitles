package com.glacialrush.plugin;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.Packet;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R1.PlayerConnection;

public class PacketUtil19
{
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
	{
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
		connection.sendPacket((Packet<?>) packetPlayOutTimes);
		if(subtitle != null)
		{
			subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
			subtitle = ChatColor.translateAlternateColorCodes((char) '&', (String) subtitle);
			IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + subtitle + "\"}"));
			PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
			connection.sendPacket((Packet<?>) packetPlayOutSubTitle);
		}
		if(title != null)
		{
			title = title.replaceAll("%player%", player.getDisplayName());
			title = ChatColor.translateAlternateColorCodes((char) '&', (String) title);
			IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + title + "\"}"));
			PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
			connection.sendPacket((Packet<?>) packetPlayOutTitle);
		}
	}
	
	public static void sendActionBar(Player player, String message)
	{
		CraftPlayer p = (CraftPlayer) player;
		IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String) ("{\"text\": \"" + message + "\"}"));
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
		p.getHandle().playerConnection.sendPacket((Packet<?>) ppoc);
	}
}
