package lubdan.dynamicstats;

import lubdan.dynamicstats.events.AbilityStarted;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dynamicstats extends JavaPlugin {
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
       this.getServer().getPluginManager().registerEvents(new AbilityStarted(this),this);
    }
}
