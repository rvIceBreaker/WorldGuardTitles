package com.glacialrush.plugin;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configuration
{
	private WorldGuardTitles pl;
	private File file;
	
	private String titleEntryText;
	private String titleExitText;
	private Integer fadeIn;
	private Integer fadeOut;
	private Integer stay;
	private Boolean capitalize;
	
	public Configuration(WorldGuardTitles pl)
	{
		file = new File(pl.getDataFolder(), "config.yml");
		
		if(!file.exists())
		{
			file.getParentFile().mkdirs();
			
			try
			{
				file.createNewFile();
			}
			
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			generate();
		}
		
		load();
	}
	
	public void load()
	{
		FileConfiguration fc = new YamlConfiguration();
		
		try
		{
			fc.load(file);
			
			titleEntryText = fc.getString("title.entry");
			titleExitText = fc.getString("title.exit");
			fadeIn = fc.getInt("title.timings.fade-in-ticks");
			fadeOut = fc.getInt("title.timings.fade-out-ticks");
			stay = fc.getInt("title.timings.stay-ticks");
			capitalize = fc.getBoolean("title.format.capitalize-region-name");
		}
		
		catch(IOException | InvalidConfigurationException e)
		{
			e.printStackTrace();
		}
	}
	
	public void generate()
	{
		FileConfiguration fc = new YamlConfiguration();
		
		fc.set("title.entry", "&aEntered &b{RG}");
		fc.set("title.exit", "&cExited &e{RG}");
		fc.set("title.timings.fade-in-ticks", 10);
		fc.set("title.timings.fade-out-ticks", 20);
		fc.set("title.timings.stay-ticks", 30);
		fc.set("title.format.capitalize-region-name", true);
		
		try
		{
			fc.save(file);
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public WorldGuardTitles getPl()
	{
		return pl;
	}

	public void setPl(WorldGuardTitles pl)
	{
		this.pl = pl;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}

	public String getTitleEntryText()
	{
		return titleEntryText;
	}

	public void setTitleEntryText(String titleEntryText)
	{
		this.titleEntryText = titleEntryText;
	}

	public String getTitleExitText()
	{
		return titleExitText;
	}

	public void setTitleExitText(String titleExitText)
	{
		this.titleExitText = titleExitText;
	}

	public Integer getFadeIn()
	{
		return fadeIn;
	}

	public void setFadeIn(Integer fadeIn)
	{
		this.fadeIn = fadeIn;
	}

	public Integer getFadeOut()
	{
		return fadeOut;
	}

	public void setFadeOut(Integer fadeOut)
	{
		this.fadeOut = fadeOut;
	}

	public Integer getStay()
	{
		return stay;
	}

	public void setStay(Integer stay)
	{
		this.stay = stay;
	}

	public Boolean getCapitalize()
	{
		return capitalize;
	}

	public void setCapitalize(Boolean capitalize)
	{
		this.capitalize = capitalize;
	}
}
