package me.xenkys.curseofvanishing;

import org.bukkit.plugin.java.JavaPlugin;

public final class CurseOfVanishing extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("vanish").setExecutor(new VanishCommand());

        getServer().getPluginManager().registerEvents(new VanishEvents(), this);

        getLogger().info("Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }
}
