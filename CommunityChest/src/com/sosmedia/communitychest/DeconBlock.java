package com.sosmedia.communitychest;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
 
public class DeconBlock implements Serializable
{
    private static final long serialVersionUID = 9L;
   
    private int x, y, z;
    private String world;
   
    public DeconBlock(Block block)
    {
        x = block.getX();
        y = block.getY();
        z = block.getZ();
        world = block.getWorld().getName();
    }
   
    public Block getBlock()
    {
        World w = Bukkit.getWorld(world);
        if (w == null)
            return null;
        Block block = w.getBlockAt(x, y, z);
        return block;
    }
   
}