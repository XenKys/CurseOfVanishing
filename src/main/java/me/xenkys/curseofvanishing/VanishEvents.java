package me.xenkys.curseofvanishing;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

import java.util.Iterator;
import java.util.UUID;

public class VanishEvents implements Listener {
    public final Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player p)) return;

        VanishPlayer player = new VanishPlayer(p);

        if (!player.isVanished()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (!(event.getTarget() instanceof Player p)) return;

        VanishPlayer target = new VanishPlayer(p);

        if (!target.isVanished()) return;

        event.setCancelled(true);
        event.setTarget(null);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player p)) return;

        VanishPlayer player = new VanishPlayer(p);

        if (!player.isVanished()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player p)) return;

        VanishPlayer player = new VanishPlayer(p);

        if (!player.isVanished()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        VanishPlayer player = new VanishPlayer(event.getPlayer());

        if (!player.isVanished()) return;

        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) return;

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            if (player.getInstance().isSneaking() && player.getInstance().getItemInHand() != null && player.getInstance().getItemInHand().getType() != Material.AIR) return;
            if (clickedBlock.getType() == Material.ENDER_CHEST) {
                event.setCancelled(true);
                player.getInstance().openInventory(player.getInstance().getEnderChest());
            }
            if (clickedBlock.getType() != Material.CHEST || clickedBlock.getType() != Material.TRAPPED_CHEST || clickedBlock.getType() != Material.ENDER_CHEST || clickedBlock.getType() != Material.BARREL || !clickedBlock.getType().toString().endsWith(Material.SHULKER_BOX.toString())) return;
            if (!(clickedBlock.getState() instanceof InventoryHolder)) return;

            InventoryHolder inventory = (InventoryHolder) clickedBlock.getState();

            event.setCancelled(true);

            Inventory clonedInventory = Bukkit.createInventory(null, inventory.getInventory().getSize(), clickedBlock.getType().toString());
            clonedInventory.setContents(inventory.getInventory().getContents());

            player.getInstance().openInventory(clonedInventory);
        } else if (event.getAction() == Action.PHYSICAL) {
            if (!clickedBlock.getType().toString().endsWith("PRESSURE_PLATE") || !clickedBlock.getType().toString().startsWith("TRIPWIRE") || clickedBlock.getType() != Material.SCULK_SENSOR || clickedBlock.getType() != Material.SCULK_SHRIEKER || clickedBlock.getType() != Material.TURTLE_EGG || clickedBlock.getType() != Material.BIG_DRIPLEAF) return;

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockReceiveGame(BlockReceiveGameEvent event) {
        if (!(event.getEntity() instanceof Player p)) return;

        VanishPlayer player = new VanishPlayer(p);

        if (!player.isVanished()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onRaidTrigger(RaidTriggerEvent event) {
        VanishPlayer player = new VanishPlayer(event.getPlayer());

        if (!player.isVanished()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        VanishPlayer player = new VanishPlayer(event.getPlayer());

        if (!player.isVanished()) return;

        event.setJoinMessage(null);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("curseofvanishing.vanish.see")) {
                p.showPlayer(plugin, player.getInstance());
            } else if (p.hasPermission("curseofvanishing.vanish.read")) {
                p.sendMessage(plugin.getConfig().getString("inVanishJoinMessage").replace("{player}", player.getInstance().getName()));
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        VanishPlayer player = new VanishPlayer(event.getPlayer());

        if (!player.isVanished()) return;

        event.setQuitMessage(null);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("curseofvanishing.vanish.see")) {
                p.showPlayer(plugin, player.getInstance());
            } else if (p.hasPermission("curseofvanishing.vanish.see.read")) {
                p.sendMessage(plugin.getConfig().getString("inVanishLeftMessage").replace("{player}", player.getInstance().getName()));
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player pl)) return;

        VanishPlayer player = new VanishPlayer(pl);

        if (!player.isVanished()) return;

        event.setCancelled(true);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("curseofvanishing.vanish.read")) {
                p.sendMessage(plugin.getConfig().getString("inVanishDeadMessage").replace("{player}", player.getInstance().getName()));
            }
        }
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        Iterator<String> iterator = event.getCompletions().iterator();

        while (iterator.hasNext()) {
            String completion = iterator.next();

            VanishPlayer player = new VanishPlayer(Bukkit.getPlayer(completion));

            if (!player.isVanished()) return;

            iterator.remove();
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        VanishPlayer player = new VanishPlayer(event.getPlayer());

        String command = event.getMessage();
        String[] args = event.getMessage().split(" ");

        if (!command.toLowerCase().startsWith("/msg") || !command.toLowerCase().startsWith("/tell")) return;

        for (UUID uniqueId : VanishManager.players) {
            if (!args[1].equals(Bukkit.getPlayer(uniqueId))) return;

            event.getPlayer().sendMessage("§cNo player was found");

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player p)) return;

        VanishPlayer player = new VanishPlayer(p);

        if (!player.isVanished()) return;

        InventoryView view = event.getView();

        if (!view.getType().equals(Material.CHEST.toString()) || !view.getTitle().equals(Material.TRAPPED_CHEST.toString()) || !view.getTitle().equals(Material.ENDER_CHEST.toString()) || !view.getTitle().equals(Material.BARREL.toString()) || !view.getTitle().endsWith(Material.SHULKER_BOX.toString())) return;

        event.setCancelled(true);
    }
}
