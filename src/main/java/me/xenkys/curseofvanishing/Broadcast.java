package me.xenkys.curseofvanishing;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Broadcast {
    public static void announceSilentMessage(Component message) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("cov.vanish.read")) {
                onlinePlayer.sendMessage(message.color(TextColor.color(85, 255, 85)));
            }
        }
    }

    public static void announcePlayerVanish(Player player) {
        VanishPlayer vanishPlayer = VanishManager.get(player);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("cov.vanish.read")) {
                onlinePlayer.sendMessage("§a" + player.getName() + (vanishPlayer.isVanished() ? " vanished" : " appeared"));
            }
        }
    }
}
