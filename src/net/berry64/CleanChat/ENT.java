package net.berry64.CleanChat;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class ENT extends JavaPlugin{
	YamlConfiguration word;
	Set<String> swearwords = new HashSet<String>();
	Boolean usebar = false;
	Boolean useEcon = false;
    Economy econ = null;
	@Override
	public void onEnable(){
		getCommand("cc").setExecutor(new COM(this));
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		checkPlugins();
		reloadFiles();
	}
	
	private void checkPlugins(){
		if(getServer().getPluginManager().getPlugin("BarAPI") != null){
			if(!getConfig().getBoolean("BarAPI")){
				usebar = false;
				getLogger().info("找到BarAPI, 配置文件内未启用BarAPI属性");
			} else {
				usebar = true;
				getLogger().info("成功挂钩BarAPI!");
			}
		} else {
			getLogger().info("无法找到BarAPI, 已禁用BarAPI属性");
			usebar = false;
		}
		if(getServer().getPluginManager().getPlugin("Vault") != null){
			setupEconomy();
			getLogger().info("成功挂钩Vault!");
			useEcon = true;
			return;
		} else {
			getLogger().info("无法找到Vault, 已禁用金钱属性");
			useEcon = false;
			return;
		}
	}
	
	private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }
	
	public void reloadFiles(){
		File f = new File(getDataFolder(), "words.yml");
		if(f.exists()){
			word = YamlConfiguration.loadConfiguration(f);
		} else {
			saveResource("words.yml", true);
			word = YamlConfiguration.loadConfiguration(f);
		}
		File cf = new File(getDataFolder(), "config.yml");
		if(cf.exists()){
			this.reloadConfig();
		} else {
			this.saveDefaultConfig();
		}
		swearwords = word.getKeys(false);
	}
	public void saveFile(){
		this.saveConfig();
		File f = new File(getDataFolder(), "words.yml");
		try {
			word.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
