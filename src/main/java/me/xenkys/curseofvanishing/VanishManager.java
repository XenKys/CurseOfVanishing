package me.xenkys.curseofvanishing;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class VanishManager {
    private static final ArrayList<UUID> vanishedPlayersUniqueIds = new ArrayList<>();

    public static ArrayList<UUID> getVanishedPlayersUniqueIds() {
        return vanishedPlayersUniqueIds;
    }

    public static VanishPlayer get(Player player) {
        return VanishPlayer.from(player);
    }

    public static boolean has(Player player) {
        return vanishedPlayersUniqueIds.contains(player.getUniqueId());
    }

    public static void add(Player player) {
        vanishedPlayersUniqueIds.add(player.getUniqueId());
    }

    public static void remove(Player player) {
        vanishedPlayersUniqueIds.remove(player.getUniqueId());
    }
}
