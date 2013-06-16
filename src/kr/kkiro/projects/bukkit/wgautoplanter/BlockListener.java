package kr.kkiro.projects.bukkit.wgautoplanter;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class BlockListener implements Listener {
	
    private WorldGuardPlugin wgPlugin;
    
    public BlockListener(WGAutoPlanter plugin, WorldGuardPlugin wgPlugin)
    {
        this.wgPlugin = wgPlugin;
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        final Block block = e.getBlock();
        final Location loc = block.getLocation();
        if(!e.getPlayer().hasPermission("wgautoplanter.ignore") &&
        		isAutoPlanter(loc) &&
        		isPlant(block.getType())) {
        	e.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockFromTo(BlockFromToEvent e) {
        final Block block = e.getToBlock();
        final Material material = block.getType();
        if(isAutoPlanter(block.getLocation())) {
	        if(isPlant(material)) {
	        	final byte data = block.getData();
	        	if((data >= 7 && !material.equals(Material.COCOA)) ||
	        			((data / 4) == 2 && material.equals(Material.COCOA))) {
	        		block.breakNaturally();
	        		block.setTypeId(material.getId());
	        		if(material.equals(Material.COCOA)) {
	        			block.setData((byte)(data & 0x03));
	        		} else {
	        			block.setData((byte)0);
	        		}
	        		e.setCancelled(true);
	        	} else {
	            	e.setCancelled(true);
	        	}
	        }
        }
    }
    
    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e) {
        final Block block = e.getBlock();
        final Material material = block.getType();
        if(isAutoPlanter(block.getLocation())) {
	        if(isPlant(material)) {
	        	final byte data = block.getData();
	        	if(material.equals(e.getChangedType()) || e.getChangedType().equals(Material.AIR)) {
	        		e.setCancelled(true);
	        		return;
	        	}
	        	if((data >= 7 && !material.equals(Material.COCOA)) ||
	        			((data / 4) == 2 && material.equals(Material.COCOA))) {
	        		block.breakNaturally();
	        		block.setTypeId(material.getId());
	        		if(material.equals(Material.COCOA)) {
	        			block.setData((byte)(data & 0x03));
	        		} else {
	        			block.setData((byte)0);
	        		}
	        		e.setCancelled(true);
	        	} else {
	            	e.setCancelled(true);
	        	}
	        }
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        final Block block = e.getBlock();
        final Material material = block.getType();
        if(isAutoPlanter(block.getLocation())) {
	        if(isPlant(material)) {
	        	final byte data = block.getData();
	        	if((data >= 7 && !material.equals(Material.COCOA)) ||
	        			((data / 4) == 2 && material.equals(Material.COCOA))) {
	        		block.breakNaturally();
	        		block.setTypeId(material.getId());
	        		if(material.equals(Material.COCOA)) {
	        			block.setData((byte)(data & 0x03));
	        		} else {
	        			block.setData((byte)0);
	        		}
	        		e.setCancelled(true);
	        	} else {
	            	e.setCancelled(true);
	        	}
	        }
        }
    }
    
    private boolean isAutoPlanter(Location loc) {
    	RegionManager rm = wgPlugin.getRegionManager(loc.getWorld());
    	if (rm == null) {
    		return true;
    	}
    	ApplicableRegionSet regions = rm.getApplicableRegions(loc);
    	return regions.allows(WGAutoPlanter.REPLANT_FLAG);
    }

    private boolean isPlant(Material mat) {
    	if(mat.equals(Material.CARROT)) return true;
    	if(mat.equals(Material.CARROT_ITEM)) return true;
    	if(mat.equals(Material.POTATO)) return true;
    	if(mat.equals(Material.POTATO_ITEM)) return true;
    	if(mat.equals(Material.WHEAT)) return true;
    	if(mat.equals(Material.CROPS)) return true;
    	if(mat.equals(Material.COCOA)) return true;
    	if(mat.equals(Material.INK_SACK)) return true;
    	return false;
    }
}
