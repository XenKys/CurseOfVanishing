package me.xenkys.curseofvanishing;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class VanishPlayer {
    private final CurseOfVanishing plugin = CurseOfVanishing.getPlugin(CurseOfVanishing.class);
    private final Player player;

    private VanishPlayer(Player player) {
        this.player = player;
    }

    public static VanishPlayer from(Player player) {
        return new VanishPlayer(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void setVanishSuperPowers(boolean vanished) {
        player.setCollidable(!vanished);
        player.setCanPickupItems(!vanished);
        player.setInvulnerable(vanished);
        player.setAllowFlight(vanished || (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR));
    }

    public void vanish() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("cov.vanish.see")) {
                onlinePlayer.hidePlayer(plugin, player);
            }
        }
    }

    public void appear() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("cov.vanish.see")) {
                onlinePlayer.showPlayer(plugin, player);
            }
        }
    }

    public boolean isVanished() {
        return VanishManager.has(player);
    }

    public void setVanish(boolean enabled) {
        if (enabled) {
            vanish();

            setVanishSuperPowers(true);

            VanishManager.add(player);
        } else {
            appear();

            setVanishSuperPowers(false);

            VanishManager.remove(player);
        }
    }
}
