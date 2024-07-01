package me.xenkys.curseofvanishing;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                VanishPlayer vanishPlayer = VanishManager.get(player);

                vanishPlayer.setVanish(!vanishPlayer.isVanished());

                Broadcast.announcePlayerVanish(player);
            } else if (args[0].equals("list")) {
                ArrayList<UUID> vanishedPlayersUniqueIds = VanishManager.getVanishedPlayersUniqueIds();

                if (vanishedPlayersUniqueIds.isEmpty()) {
                    player.sendMessage("§cThere are no vanished players!");

                    return true;
                }

                StringBuilder message = new StringBuilder();

                for (int i = 0; i < vanishedPlayersUniqueIds.size(); i++) {
                    if (i > 0) {
                        message.append(", ");
                    }

                    message.append(Bukkit.getOfflinePlayer(vanishedPlayersUniqueIds.get(i)).getName());
                }

                player.sendMessage("§aCurrently vanished players: " + message);
            } else {
                VanishPlayer target = VanishManager.get(Bukkit.getPlayer(args[0]));

                if (target.getPlayer() == null) {
                    player.sendMessage("§cThe player " + args[0] + " doesn't exists!");

                    return true;
                }

                if (!target.getPlayer().isOnline()) {
                    player.sendMessage("§c" + target.getPlayer().getName() + " isn't online!");

                    return true;
                }

                target.setVanish(!target.isVanished());

                Broadcast.announcePlayerVanish(target.getPlayer());
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                sender.sendMessage("§cYou don't entered the nickname of a player!");

                return true;
            } else if (args[0].equals("list")) {
                ArrayList<UUID> vanishedPlayersUniqueIds = VanishManager.getVanishedPlayersUniqueIds();

                if (vanishedPlayersUniqueIds.isEmpty()) {
                    sender.sendMessage("§cThere are no vanished players!");

                    return true;
                }

                StringBuilder message = new StringBuilder();

                for (int i = 0; i < vanishedPlayersUniqueIds.size(); i++) {
                    if (i > 0) {
                        message.append(", ");
                    }

                    message.append(Bukkit.getOfflinePlayer(vanishedPlayersUniqueIds.get(i)).getName());
                }


                sender.sendMessage("§aCurrently vanished players: " + message);
            } else {
                VanishPlayer target = VanishManager.get(Bukkit.getPlayer(args[0]));

                if (target.getPlayer() == null) {
                    sender.sendMessage("§cThe player " + args[0] + " doesn't exists!");

                    return true;
                }

                if (!target.getPlayer().isOnline()) {
                    sender.sendMessage("§c" + target.getPlayer().getName() + " isn't online!");

                    return true;
                }

                target.setVanish(!target.isVanished());

                Broadcast.announcePlayerVanish(target.getPlayer());
            }
        }

        return true;
    }
}