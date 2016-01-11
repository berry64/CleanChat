package net.berry64.CleanChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class COM implements CommandExecutor {
	ENT pl;
	public COM(ENT aksdjaaa){
		pl = aksdjaaa;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length >= 1){
			if(args[0].equalsIgnoreCase("check")){
				if(sender.hasPermission("cc.check")){
					if(args.length >= 2){
						String s = pl.word.getString(args[1]);
						if(s != null){
							sender.sendMessage(
									"§e[CleanChat]§e "+args[1]+" §7被屏蔽成了 §e"+s+"§7 !");
						} else {
							sender.sendMessage("§e[CleanChat]§e "+args[1].toLowerCase()+"§7 未被屏蔽!");
						}
					} else {
						sender.sendMessage("§e[CleanChat]§c 使用方法: /cc check [查询的词]");
					}
				} else {
					sender.sendMessage("§e[CleanChat]§c 你没有权限 §ecc.check");
				}
			} else if (args[0].equalsIgnoreCase("add")){
				if(sender.hasPermission("cc.add")){
					if(args.length >= 3){
						if(pl.word.getString(args[1]) == null){
							pl.word.set(args[1], args[2]);
							sender.sendMessage("§e[CleanChat]§7 成功屏蔽 §e"+args[1]);
							pl.saveFile();
							pl.reloadFiles();
						} else {
							sender.sendMessage("§e[CleanChat]§e "+args[1]+" §7已经存在! 请先使用/cc remove 删掉它");
						}
					} else {
						sender.sendMessage("§e[CleanChat]§c 使用方法: /cc add [原词] [替换的词]");
					}
				} else {
					sender.sendMessage("§e[CleanChat]§c 你没有权限 §ecc.add");
				}
			} else if(args[0].equalsIgnoreCase("remove")){
				if(sender.hasPermission("cc.remove")){
					if(args.length >= 2){
						String s = pl.word.getString(args[1]);
						if(s != null){
							pl.word.set(args[1], null);
							pl.saveFile();
							pl.reloadFiles();
							sender.sendMessage("§e[CleanChat]§7 成功移除 §e"+args[1]);
						} else {
							sender.sendMessage("§e[CleanChat]§e "+args[1]+" §7本身就没有被屏蔽");
						}
					} else {
						sender.sendMessage("§e[CleanChat]§c 使用方法: /cc remove [删除的词]");
					}
				} else {
					sender.sendMessage("§e[CleanChat]§c 你没有权限 §ecc.remove");
				}
			} else if(args[0].equalsIgnoreCase("reload")){
				if(sender.hasPermission("cc.reload")){
					pl.saveFile();
					pl.reloadFiles();
					sender.sendMessage("§e[CleanChat]§7 成功重载插件!");
				} else {
					sender.sendMessage("§e[CleanChat]§a 你没有权限 §ecc.reload");
				}
			} else if(args[0].equalsIgnoreCase("help")){
				if(sender.hasPermission("cc.help")){
					sender.sendMessage(new String[]{
							"§a§l-----===§b§lCleanChat§a§l===-----",
							"§c插件由berry64制作",
							"§e- /cc add         §7添加一个屏蔽的词语",
							"§e- /cc remove      §7移除一个屏蔽的词语",
							"§e- /cc check       §7查询一个词语是否被屏蔽",
							"§e- /cc reload      §7重新加载插件",
							"§e- /cc help        §7查看此帮助"
							});
				} else {
					sender.sendMessage("§e[CleanChat]§c 你没有权限 §ecc.help");
				}
			} else {
				sender.sendMessage("§e[CleanChat]§7 请使用/cc help");
			}
		} else {
			sender.sendMessage("§e[CleanChat]§7 请使用/cc help");
		}
		return true;
	}
}
