package com.glacialrush.plugin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.md_5.bungee.api.ChatColor;

public class WorldGuardTitles extends JavaPlugin implements Listener
{
	private HashMap<Player, String> regions;
	private Configuration cfg;
	
	public void onEnable()
	{
		regions = new HashMap<Player, String>();
		cfg = new Configuration(this);
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable()
	{
		
	}
	
	public String colorize(String msg)
	{
		String coloredMsg = "";
		
		for(int i = 0; i < msg.length(); i++)
		{
			if(msg.charAt(i) == '&')
			{
				coloredMsg += '§';
			}
			else
			{
				coloredMsg += msg.charAt(i);
			}
		}
		
		return coloredMsg;
	}
	
	public String worldString(World world, String name)
	{
		return world.getName() + ":" + name;
	}
	
	public String removeWorld(World world, String name)
	{
		return StringUtils.remove(name, world.getName() + ":");
	}
	
	public void titleEntry(Player p, String i)
	{
		if(cfg.getCapitalize())
		{
			i = StringUtils.capitalize(i);
		}
		
		String title = StringUtils.replace(ChatColor.translateAlternateColorCodes('&', cfg.getTitleEntryText()), "{RG}", i);
		sendTitle(p, cfg.getFadeIn(), cfg.getStay(), cfg.getFadeOut(), title, "");
	}
	
	public void titleExit(Player p, String i)
	{
		if(cfg.getCapitalize())
		{
			i = StringUtils.capitalize(i);
		}
		
		String title = StringUtils.replace(ChatColor.translateAlternateColorCodes('&', cfg.getTitleExitText()), "{RG}", i);
		sendTitle(p, cfg.getFadeIn(), cfg.getStay(), cfg.getFadeOut(), title, "");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(command.getName().equalsIgnoreCase("worldguardtitles"))
		{
			if(!sender.hasPermission("worldguardtitles.reload"))
			{
				sender.sendMessage(ChatColor.RED + "No Permission!");
				return true;
			}
			
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("reload"))
				{
					cfg.load();
					sender.sendMessage(ChatColor.GREEN + "Reloaded the Configuration.");
					return true;
				}
			}
			
			sender.sendMessage(ChatColor.GREEN + "World Guard Titles: /wgt reload - Reloads the config.");
			return true;
		}
		
		return false;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Location to = e.getTo();
		World world = to.getWorld();
		Map<String, ProtectedRegion> rgs = WorldGuardPlugin.inst().getRegionManager(world).getRegions();
		Boolean outsider = true;
		
		for(String i : rgs.keySet())
		{
			if(rgs.get(i).contains(to.getBlockX(), to.getBlockY(), to.getBlockZ()))
			{
				outsider = false;
				
				if(!regions.containsKey(e.getPlayer()))
				{
					regions.put(e.getPlayer(), "null");
				}
				
				if(!regions.get(e.getPlayer()).equals(worldString(world, i)))
				{
					regions.put(e.getPlayer(), worldString(world, i));
					titleEntry(e.getPlayer(), i);
				}
			}
		}
		
		if(outsider && regions.containsKey(e.getPlayer()))
		{
			titleExit(e.getPlayer(), removeWorld(world, regions.get(e.getPlayer())));
			regions.remove(e.getPlayer());
		}
	}
	
	public void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
	{
		PacketUtil.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
	}
}
