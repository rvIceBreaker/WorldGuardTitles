package com.glacialrush.plugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PacketUtil
{
	public enum V
	{
		R18, R19;
	}
	
	public static V getVersion()
	{
		if(Bukkit.getBukkitVersion().startsWith("1.9"))
		{
			return V.R19;
		}
		
		return V.R18;
	}
	
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
	{
		if(getVersion().equals(V.R18))
		{
			PacketUtil18.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		}
		
		if(getVersion().equals(V.R19))
		{
			PacketUtil19.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
		}
	}
	
	public static void sendActionBar(Player player, String message)
	{
		if(getVersion().equals(V.R18))
		{
			PacketUtil18.sendActionBar(player, message);
		}
		
		if(getVersion().equals(V.R19))
		{
			PacketUtil19.sendActionBar(player, message);
		}
	}
}
