package com.sosmedia.communitychest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.bukkit.plugin.java.JavaPlugin;


public class CommunityChestMain extends JavaPlugin {
	public File file;
	public CardboardBox[] items = new CardboardBox[6 * 9];

	ChestListener cl = new ChestListener(this);
	@Override
	public void onEnable() {
		loadFile();
		getServer().getPluginManager().registerEvents(cl, this);
	}

	public CardboardBox[] loadFile() {
		File fileDir = new File("plugins" + File.separator + getDescription().getName() + File.separator);
		file = new File("plugins" + File.separator + getDescription().getName() + File.separator + "chestinventory.ser");
		if(!file.exists()) {
			if(!fileDir.exists()) {
				fileDir.mkdir();
			}
			try {
				file.createNewFile();
				saveFile(items);
			} catch (IOException e) {
				// let them know
				e.printStackTrace();
			}
		} else {
			try {
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				items = (CardboardBox[]) ois.readObject();
				ois.close();
				return items;
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
