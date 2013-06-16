package kr.kkiro.projects.bukkit.wgautoplanter;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.StateFlag;

public class WGAutoPlanter extends JavaPlugin {
	public static final StateFlag REPLANT_FLAG = new StateFlag("replant", false);
	
	private WorldGuardPlugin wgPlugin;
    private WGCustomFlagsPlugin custPlugin;
    
    private BlockListener listener;
	
	@Override
    public void onEnable() {
		wgPlugin = getWGPlugin();
        custPlugin = getCustPlugin();
        if(wgPlugin == null) {
            getLogger().log(Level.WARNING, "This plugin requires WorldGuard. Deactivating.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if(custPlugin == null) {
            getLogger().log(Level.WARNING, "This plugin requires WG Custom Flags. Deactivating.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        listener = new BlockListener(this, wgPlugin);
        
        getServer().getPluginManager().registerEvents(listener, this);
        
        custPlugin.addCustomFlag(REPLANT_FLAG);
	}
	
	private WorldGuardPlugin getWGPlugin() {
        org.bukkit.plugin.Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        if(plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        } else {
            return (WorldGuardPlugin)plugin;
        }
    }

    private WGCustomFlagsPlugin getCustPlugin() {
        org.bukkit.plugin.Plugin plugin = getServer().getPluginManager().getPlugin("WGCustomFlags");
        if(plugin == null || !(plugin instanceof WGCustomFlagsPlugin)) {
            return null;
        } else {
            return (WGCustomFlagsPlugin)plugin;
        }
    }
}
