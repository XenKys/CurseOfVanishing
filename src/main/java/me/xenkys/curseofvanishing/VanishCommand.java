package me.xenkys.curseofvanishing;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                VanishPlayer player = new VanishPlayer(p);

                player.setVanish(!player.isVanished());
                player.getInstance().sendMessage(player.isVanished() ? "§aVanish enabled" : "§aVanish disabled");
            } else if (args[0].equals("list")) {
                ArrayList<UUID> vanishedPlayers = VanishManager.players;

                if (vanishedPlayers.size() == 0) {
                    sender.sendMessage("§cThere are no vanished players!");

                    return true;
                }

                StringBuilder message = new StringBuilder();

                for (int i = 0; i < vanishedPlayers.size(); i++) {
                    if (i > 0) {
                        message.append(", ");
                    }

                    message.append(Bukkit.getPlayer(vanishedPlayers.get(i)).getName());
                }

                sender.sendMessage("§aCurrently vanished players: " + message);
            } else {
                VanishPlayer target = new VanishPlayer(Bukkit.getPlayer(args[0]));

                if (target.getInstance() == null) {
                    sender.sendMessage("§cThe player " + args[0] + " doesn't exists!");

                    return true;
                }

                if (!target.getInstance().isOnline()) {
                    sender.sendMessage("§cThe player " + target.getInstance().getName() + " isn't online!");

                    return true;
                }

                target.setVanish(!target.isVanished());
                target.getInstance().sendMessage(target.isVanished() ? "§aVanish enabled" : "§aVanish disabled");
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                sender.sendMessage("§cYou don't entered the nickname of a player!");

                return true;
            } else if (args[0].equals("list")) {
                ArrayList<UUID> vanishedPlayers = VanishManager.players;

                if (vanishedPlayers.size() == 0) {
                    sender.sendMessage("§cThere are no vanished players!");

                    return true;
                }

                StringBuilder message = new StringBuilder();

                for (int i = 0; i < vanishedPlayers.size(); i++) {
                    if (i > 0) {
                        message.append(", ");
                    }

                    message.append(Bukkit.getPlayer(vanishedPlayers.get(i)).getName());
                }

                sender.sendMessage("§aCurrently vanished players: " + message);
            } else {
                VanishPlayer target = new VanishPlayer(Bukkit.getPlayer(args[0]));

                if (target.getInstance() == null) {
                    sender.sendMessage("§cThe player " + args[0] + " doesn't exists!");

                    return true;
                }

                if (!target.getInstance().isOnline()) {
                    sender.sendMessage("§cThe player " + target.getInstance().getName() + " isn't online!");

                    return true;
                }

                target.setVanish(!target.isVanished());
                target.getInstance().sendMessage(target.isVanished() ? "§aVanish enabled" : "§aVanish disabled");
            }
        }

        return true;
    }
}
