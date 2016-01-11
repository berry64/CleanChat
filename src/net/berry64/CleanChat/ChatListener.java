package net.berry64.CleanChat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.confuser.barapi.BarAPI;

public class ChatListener implements Listener {
	ENT pl;
	public ChatListener(ENT eskxasjda){
		pl = eskxasjda;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent evt){
		Player p = evt.getPlayer();
		if(!p.hasPermission("cc.pass")){
			for(String s: pl.swearwords){
				if(evt.getMessage().contains(s)){
					try {
						evt.setMessage(
								evt.getMessage().replaceAll(s, pl.word.getString(s)));
						trigger(p, s);
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void trigger(Player p, String msg){
		if(pl.usebar){
			String bm = pl.getConfig().getString("BarMessage");
			BarAPI.setMessage(bm.replaceAll("&", "§")
					.replaceAll("%player", p.getName())
					.replaceAll("%displayname", p.getDisplayName())
					.replaceAll("%message", msg),
					pl.getConfig().getInt("BarTime"));
		}
		if(pl.useEcon){
			if(pl.econ.has(p, pl.getConfig().getInt("takemoney"))){
				pl.econ.withdrawPlayer(p, pl.getConfig().getInt("takemoney"));
			} else {
				String s = pl.getConfig().getString("todowhenoutofmoney");
				String r = pl.getConfig().getString("reason");
				if(s.contains("kick")){
					p.kickPlayer(r);
				}
				if(s.contains("ban")){
					p.setBanned(true);
				}
				if(s.contains("clearinv")){
					p.getInventory().clear();
				}
				if(s.contains("clearmoney")){
					pl.econ.withdrawPlayer(p, pl.econ.getBalance(p));
				}
			}
		}
		if(pl.getConfig().getBoolean("runcommand")){
			String type = pl.getConfig().getString("type");
			String cmd = pl.getConfig().getString("command").replaceAll("&", "§")
					.replaceAll("%player", p.getName())
					.replaceAll("%displayname", p.getDisplayName())
					.replaceAll("%message", msg);
			if(type.equalsIgnoreCase("player")){
				pl.getServer().dispatchCommand(p, cmd);
			}
			if(type.equalsIgnoreCase("console")){
				pl.getServer().dispatchCommand(pl.getServer().getConsoleSender(), cmd);
			}
		}
		if(pl.getConfig().getBoolean("notifysender")){
			String m = pl.getConfig().getString("notification").replaceAll("&", "§")
					.replaceAll("%player", p.getName())
					.replaceAll("%displayname", p.getDisplayName())
					.replaceAll("%message", msg);
			p.sendMessage("§a[ClearChat]§7 "+m);
		}
	}
}
