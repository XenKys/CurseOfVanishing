package me.xenkys.curseofvanishing;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Configuration
        getConfig().options().copyDefaults(true);
        saveConfig();

        // Commands
        getCommand("vanish").setExecutor(new VanishCommand());

        // Events
        getServer().getPluginManager().registerEvents(new VanishEvents(), this);

        getServer().getLogger().info("[CurseOfVanishing] Enabled");
    }

    @Override
    public void onDisable() {
        getServer().getLogger().info("[CurseOfVanishing] Disabled");
    }
}
