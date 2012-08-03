package com.sosmedia.communitychest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import org.bukkit.plugin.java.JavaPlugin;

public class CommunityChestMain extends JavaPlugin {
	public File file;
	private HashMap<String, CardboardBox[]> chests = new HashMap<String, CardboardBox[]>();
	private HashMap<String, DeconBlock> signs = new HashMap<String, DeconBlock>();
	private final ChestListener cl = new ChestListener(this);
	private DeconBlock deconBlock;
	CommandListener commandListener = new CommandListener(this);
	@Override
	public void onEnable() {
		loadFile1();
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {}	
		getServer().getPluginManager().registerEvents(cl, this);
		getCommand("cc").setExecutor(commandListener);
	}

	@SuppressWarnings("unchecked")
	//loads the String, CardboardBox[] map
	public HashMap<String, CardboardBox[]> loadFile1() {
		File fileDir = new File("plugins" + File.separator + getDescription().getName() + File.separator);
		file = new File("plugins" + File.separator + getDescription().getName() + File.separator + "chestinventory.ser");
		if(!file.exists()) {
			if(!fileDir.exists()) {
				fileDir.mkdir();
			}
			try {
				file.createNewFile();
				chests.put("", new CardboardBox[54]);
				saveFile(chests);
			} catch (IOException e) {
				// let them know
				e.printStackTrace();
			}
		} else {
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				chests = (HashMap<String, CardboardBox[]>) ois.readObject();
				ois.close();
				return chests;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	//loads the String, DeconBlock map
	public HashMap<String, DeconBlock> loadFile2() {

		File fileDir = new File("plugins" + File.separator + getDescription().getName() + File.separator);
		file = new File("plugins" + File.separator + getDescription().getName() + File.separator + "chestnames.ser");
		if(!file.exists()) {
			if(!fileDir.exists()) {
				fileDir.mkdir();
			}
			try {
				file.createNewFile();
				signs.put("", deconBlock);
				saveFile(signs);
			} catch (IOException e) {
				// let them know
				e.printStackTrace();
			}
		} else {
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				signs = (HashMap<String, DeconBlock>) ois.readObject();
				ois.close();
				return signs;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public boolean saveFile(Object obj) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public void onDisable() {

	}
}
