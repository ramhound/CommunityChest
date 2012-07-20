package com.sosmedia.communitychest;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;



public class ChestListener implements Listener {
	public CommunityChestMain plugin;
	public ItemStack[] items;

	public ChestListener(CommunityChestMain in) {
		plugin = in;
	}

	@EventHandler
	public void inventoryOpen(InventoryOpenEvent event) {
		if(event.getInventory() instanceof DoubleChestInventory) {
			DoubleChestInventory dci = (DoubleChestInventory) event.getInventory(); 
			Chest leftChest = (Chest) dci.getLeftSide().getHolder();
			Block leftBlock = leftChest.getBlock().getRelative(0, 1, 0);
			Material leftBlockType = leftBlock.getType();
			Chest rightChest = (Chest) dci.getRightSide().getHolder();
			Block rightBlock = rightChest.getBlock().getRelative(0, 1, 0);
			Material rightBlockType = rightBlock.getType();
			if(leftBlockType == Material.WALL_SIGN || rightBlockType == Material.WALL_SIGN) {
				Sign sign = leftBlockType == Material.WALL_SIGN ? (Sign) leftBlock.getState() : (Sign) rightBlock.getState(); 
				String[] lines = sign.getLines();
				if(lines[0].equalsIgnoreCase("community") && lines[1].equalsIgnoreCase("chest")) { 
					HashMap<String, CardboardBox[]> chests = plugin.loadFile();
					String chestName = lines[2].toLowerCase();
					if(chests.containsKey(chestName)) {
						CardboardBox[] items = chests.get(chestName);
						ItemStack[] tempItems =  new ItemStack[items.length];
						for(int i = 0; i < items.length; i++) {
							if(items[i] == null) { 
								continue; 
							} 
							tempItems[i] = items[i].unbox(); 
						} 
						dci.setContents(tempItems);
					} else {
						chests.put(chestName, new CardboardBox[54]);
						plugin.saveFile(chests);
					}
				} 
			}
		}
	}

	@EventHandler
	public void inventoryClose(InventoryCloseEvent event) {
		if(event.getInventory() instanceof DoubleChestInventory) {
			DoubleChestInventory dci = (DoubleChestInventory) event.getInventory(); 
			Chest leftChest = (Chest) dci.getLeftSide().getHolder();
			Block leftBlock = leftChest.getBlock().getRelative(0, 1, 0);
			Material leftBlockType = leftBlock.getType();
			Chest rightChest = (Chest) dci.getRightSide().getHolder();
			Block rightBlock = rightChest.getBlock().getRelative(0, 1, 0);
			Material rightBlockType = rightBlock.getType();
			if(leftBlockType == Material.WALL_SIGN || rightBlockType == Material.WALL_SIGN) {
				Sign sign = leftBlockType == Material.WALL_SIGN ? (Sign) leftBlock.getState() : (Sign) rightBlock.getState(); 
				String[] lines = sign.getLines();
				if(lines[0].equalsIgnoreCase("community") && lines[1].equalsIgnoreCase("chest")) {   
					HashMap<String, CardboardBox[]> chests = plugin.loadFile();
					String chestName = lines[2].toLowerCase();
					if(chests.containsKey(chestName)) {
						ItemStack[] tempItems = dci.getContents();
						CardboardBox[] items = new CardboardBox[tempItems.length];
						for(int i = 0; i < items.length; i++) {
							if(tempItems[i] == null) {
								continue;
							}
							items[i] = new CardboardBox(tempItems[i]);
						}
						chests.put(chestName, items);
						plugin.saveFile(chests);
					}
				}
			}
		}
	}

	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		if(event.getInventory() instanceof DoubleChestInventory) {
			DoubleChestInventory dci = (DoubleChestInventory) event.getInventory(); 
			Chest leftChest = (Chest) dci.getLeftSide().getHolder();
			Block leftBlock = leftChest.getBlock().getRelative(0, 1, 0);
			Material leftBlockType = leftBlock.getType();
			Chest rightChest = (Chest) dci.getRightSide().getHolder();
			Block rightBlock = rightChest.getBlock().getRelative(0, 1, 0);
			Material rightBlockType = rightBlock.getType();
			if(leftBlockType == Material.WALL_SIGN || rightBlockType == Material.WALL_SIGN) {
				Sign sign = leftBlockType == Material.WALL_SIGN ? (Sign) leftBlock.getState() : (Sign) rightBlock.getState(); 
				String[] lines = sign.getLines();
				if(lines[0].equalsIgnoreCase("community") && lines[1].equalsIgnoreCase("chest")) {   
					HashMap<String, CardboardBox[]> chests = plugin.loadFile();
					String chestName = lines[2].toLowerCase();
					if(chests.containsKey(chestName)) {
						ItemStack[] tempItems = dci.getContents();
						CardboardBox[] items = new CardboardBox[tempItems.length];
						for(int i = 0; i < tempItems.length; i++) {
							if(tempItems[i] == null) {
								continue;
							}
							items[i] = new CardboardBox(tempItems[i]);
						}
						chests.put(chestName, items);
						plugin.saveFile(chests);
					}
				}
			}
		}
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Material blockType = block.getType();
		if(blockType == Material.WALL_SIGN) {
			Sign sign = (Sign) block.getState();
			String[] lines = sign.getLines();
			if(lines[0].equalsIgnoreCase("community") && lines[1].equalsIgnoreCase("chest")) { 
				Block blockUnder = block.getRelative(0, -1, 0);
				if(blockUnder.getTypeId() == 54) {
					Location loc = blockUnder.getLocation();
					Chest chest = (Chest)loc.getBlock().getState();
					chest.getInventory().clear();
				}
			}
		}
	}
}