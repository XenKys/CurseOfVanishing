package me.xenkys.curseofvanishing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishPlayer {
    public final Player player;
    public final Main plugin = Main.getPlugin(Main.class);

    public VanishPlayer(Player player) {
        this.player = player;
    }

    public Player getInstance() {
        return player;
    }

    public boolean isVanished() {
        return VanishManager.has(getInstance().getUniqueId());
    }

    public void setVanish(Boolean enabled) {
        if (enabled) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission("curseofvanishing.vanish.see")) {
                    p.hidePlayer(plugin, player);
                }

                if (p.hasPermission("curseofvanishing.vanish.read")) {
                    p.sendMessage("@a" + player.getName() + " enabled the vanish");
                }
            }

            player.setCollidable(false);
            player.setInvulnerable(true);
            player.setAllowFlight(true);

            Bukkit.broadcastMessage(plugin.getConfig().getString("fakeLeftMessage").replace("{player}", player.getName()));

            VanishManager.add(player.getUniqueId());
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.hasPermission("curseofvanishing.vanish.see")) {
                    p.showPlayer(plugin, player);
                }

                if (p.hasPermission("curseofvanishing.vanish.read")) {
                    p.sendMessage("@a" + player.getName() + " disabled the vanish");
                }
            }

            player.setCollidable(true);
            player.setInvulnerable(false);
            player.setAllowFlight(false);

            Bukkit.broadcastMessage(plugin.getConfig().getString("fakeJoinMessage").replace("{player}", player.getName()));

            VanishManager.remove(player.getUniqueId());
        }
    }
}