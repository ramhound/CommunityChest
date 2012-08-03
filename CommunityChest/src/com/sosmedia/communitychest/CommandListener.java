package com.sosmedia.communitychest;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandListener implements CommandExecutor {

	private CommunityChestMain plugin;
	private ChestListener chestListener = new ChestListener(plugin);

	public CommandListener(CommunityChestMain plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
		}
		if(player != null) {
			if(label.equalsIgnoreCase("cc") && args.length > 0) {
				HashMap<String, CardboardBox[]> chests = plugin.loadFile1();
				String[] keys = (String[])( chests.keySet().toArray( new String[chests.size()] ) );
				if(args[0].equalsIgnoreCase("list")) {
					player.sendMessage(ChatColor.GREEN + "Community Chest list:");
					player.sendMessage(keys);
					return true;
				} 
				if(args[0].equalsIgnoreCase("delete") && args.length > 1) {
					String name = "";
					
						name += args[1];
						
					if(chests.containsKey(name)) {
						HashMap<String, DeconBlock> signs = plugin.loadFile2();
						DeconBlock deconBlock = signs.get(name);
						Block block = deconBlock.getBlock();
						chestListener.removeSign(block);
						chests.remove(name);
						plugin.saveFile(chests);
						signs.remove(name);
						plugin.saveFile(signs);
						
						player.sendMessage(ChatColor.GREEN + name + ChatColor.YELLOW + " has been removed");
						return true;
					} else {
						player.sendMessage(ChatColor.RED + "The community chest name " + ChatColor.GREEN + name + ChatColor.RED + " was not found");
						return true;
					}
				} 
			} 
		}else {
			sender.sendMessage("This command is for players only");
			return true;
		}
		return false;
	}
}
