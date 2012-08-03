package com.sosmedia.communitychest;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.DoubleChestInventory;
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
					HashMap<String, CardboardBox[]> chests = plugin.loadFile1();
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



					HashMap<String, CardboardBox[]> chests = plugin.loadFile1();
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
					HashMap<String, CardboardBox[]> chests = plugin.loadFile1();
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
	public void signChangeEvent(SignChangeEvent event) {
		Block block = event.getBlock();
		String[] lines = event.getLines();
		if(lines[0].equalsIgnoreCase("community") && lines[1].equalsIgnoreCase("chest")) { 
			HashMap<String, DeconBlock> signs = plugin.loadFile2();
			if(!signs.containsKey(lines[2])) {
				DeconBlock deconBlock = new DeconBlock(block);
				signs.put(lines[2], deconBlock);
				plugin.saveFile(signs);
			}
		}
	}

	@EventHandler
	public void explodeEvent(EntityExplodeEvent event) {
		List<Block> blockList = event.blockList();
		for(int i = 0; i < blockList.size(); i++) {
			Block block = blockList.get(i);
			emptyChest(block);	
		}
	}

	@EventHandler
	public void blockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		emptyChest(block);
	}
	public void removeSign(Block block) {
		block.breakNaturally();

	}

	public void emptyChest(Block block) {
		Material blockType = block.getType();
		if(blockType == Material.WALL_SIGN) {
			Sign sign = (Sign) block.getState();
			String[] lines = sign.getLines();
			if(lines[0].equalsIgnoreCase("community") && lines[1].equalsIgnoreCase("chest")) { 
				Block blockUnder = block.getRelative(0, -1, 0);
				if(blockUnder.getTypeId() == 54) {
					Location loc = blockUnder.getLocation();
					Chest chest = (Chest)loc.getBlock().getState();
					HashMap<String, DeconBlock> signs = plugin.loadFile2();
					if(signs.containsKey(lines[2])) {
						signs.remove(lines[2]);
						plugin.saveFile(signs);
						removeSign(block);
					}
					chest.getInventory().clear();
				}
			}
		} else if(blockType == Material.CHEST) {
			Chest chest = (Chest) block.getState();
			Block blockAbove = block.getRelative(0, 1, 0);
			blockType = blockAbove.getType();
			if(blockType == Material.WALL_SIGN) {
				Sign sign = (Sign) blockAbove.getState();
				String[] lines = sign.getLines();
				if(lines[0].equalsIgnoreCase("community") && lines[1].equalsIgnoreCase("chest")) {
					HashMap<String, DeconBlock> signs = plugin.loadFile2();
					if(signs.containsKey(lines[2])) {
						signs.remove(lines[2]);
						plugin.saveFile(signs);
						removeSign(block);
					}
					chest.getInventory().clear();
				}
			}
			blockAbove = block.getRelative(1, 1, 0);
			blockType = blockAbove.getType();
			if(blockType == Material.WALL_SIGN) {
				Sign sign = (Sign) blockAbove.getState();
				String[] lines = sign.getLines();
				if(lines[0].equalsIgnoreCase("community") && lines[1].equalsIgnoreCase("chest")) { 
					HashMap<String, DeconBlock> signs = plugin.loadFile2();
					if(signs.containsKey(lines[2])) {
						signs.remove(lines[2]);
						plugin.saveFile(signs);
						removeSign(block);
					}
					chest.getInventory().clear();
				}
			}
			blockAbove = block.getRelative(-1, 1, 0);
			blockType = blockAbove.getType();
			if(blockType == Material.WALL_SIGN) {
				Sign sign = (Sign) blockAbove.getState();
				String[] lines = sign.getLines();
				if(lines[0].equalsIgnoreCase("community") && lines[1].equalsIgnoreCase("chest")) { 
					HashMap<String, DeconBlock> signs = plugin.loadFile2();
					if(signs.containsKey(lines[2])) {
						signs.remove(lines[2]);
						plugin.saveFile(signs);
						removeSign(block);
					}
					chest.getInventory().clear();
				}
			}
			blockAbove = block.getRelative(0, 1, 1);
			blockType = blockAbove.getType();
			if(blockType == Material.WALL_SIGN) {
				Sign sign = (Sign) blockAbove.getState();
				String[] lines = sign.getLines();
				if(lines[0].equalsIgnoreCase("community") && lines[1].equalsIgnoreCase("chest")) { 
					HashMap<String, DeconBlock> signs = plugin.loadFile2();
					if(signs.containsKey(lines[2])) {
						signs.remove(lines[2]);
						plugin.saveFile(signs);
						removeSign(block);
					}
					chest.getInventory().clear();
				}
			}
			blockAbove = block.getRelative(0, 1, -1);
			blockType = blockAbove.getType();
			if(blockType == Material.WALL_SIGN) {
				Sign sign = (Sign) blockAbove.getState();
				String[] lines = sign.getLines();
				if(lines[0].equalsIgnoreCase("community") && lines[1].equalsIgnoreCase("chest")) { 
					HashMap<String, DeconBlock> signs = plugin.loadFile2();
					if(signs.containsKey(lines[2])) {
						signs.remove(lines[2]);
						plugin.saveFile(signs);
						removeSign(block);
					}
					chest.getInventory().clear();
				}
			}
		}
	}
}