package me.xenkys.curseofvanishing;

import java.util.ArrayList;
import java.util.UUID;

public class VanishManager {
    public static ArrayList<UUID> players = new ArrayList<UUID>();

    public static boolean has(UUID playerUniqueId) {
        return players.contains(playerUniqueId);
    }

    public static void add(UUID playerUniqueId) {
        players.add(playerUniqueId);
    }

    public static void remove(UUID playerUniqueId) {
        players.remove(playerUniqueId);
    }
}
