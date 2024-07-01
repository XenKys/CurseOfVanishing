package me.xenkys.curseofvanishing;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class VanishEvents implements Listener {
    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() instanceof Player player) {
            VanishPlayer target = VanishManager.get(player);

            if (!target.isVanished()) return;

            event.setCancelled(true);
            event.setTarget(null);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            VanishPlayer vanishPlayer = VanishManager.get(player);

            if (!vanishPlayer.isVanished()) return;

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        VanishPlayer vanishPlayer = VanishManager.get(player);

        if (!vanishPlayer.isVanished()) return;

        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) return;

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.getGameMode() == GameMode.SPECTATOR) return;

            if (player.isSneaking() && !player.getActiveItem().isEmpty() && player.getActiveItem().getType() != Material.AIR) return;

            if (clickedBlock.getType() == Material.ENDER_CHEST) {
                event.setCancelled(true);

                player.openInventory(player.getEnderChest());
            }

            if (clickedBlock.getType() == Material.CHEST || clickedBlock.getType() == Material.TRAPPED_CHEST || clickedBlock.getType() == Material.ENDER_CHEST || clickedBlock.getType().toString().endsWith("SHULKER_BOX") || clickedBlock.getType() == Material.BARREL) {
                if (clickedBlock.getState() instanceof InventoryHolder inventoryHolder) {
                    event.setCancelled(true);

                    Inventory inventory = inventoryHolder.getInventory();
                    Inventory clonedInventory = Bukkit.createInventory(inventoryHolder, inventory.getSize(), inventory.getType().defaultTitle());

                    clonedInventory.setContents(inventory.getContents());
                    player.openInventory(clonedInventory);
                }
            }
        } else if (event.getAction() == Action.PHYSICAL) {
            if (clickedBlock.getType().toString().endsWith("PRESSURE_PLATE") || clickedBlock.getType().toString().startsWith("TRIPWIRE") || clickedBlock.getType() == Material.FARMLAND || clickedBlock.getType() == Material.TURTLE_EGG || clickedBlock.getType() == Material.BIG_DRIPLEAF || clickedBlock.getType() == Material.SCULK_SENSOR || clickedBlock.getType() == Material.SCULK_SHRIEKER || clickedBlock.getType() == Material.CALIBRATED_SCULK_SENSOR) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (player.getGameMode() == GameMode.SPECTATOR) return;

            VanishPlayer vanishPlayer = VanishManager.get(player);

            if (!vanishPlayer.isVanished()) return;

            Inventory inventory = event.getInventory();

            if (inventory.getType() == InventoryType.CHEST || inventory.getType() == InventoryType.SHULKER_BOX || inventory.getType() == InventoryType.BARREL) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockReceiveGame(BlockReceiveGameEvent event) {
        if (event.getEntity() instanceof Player player) {
            VanishPlayer vanishPlayer = VanishManager.get(player);

            if (!vanishPlayer.isVanished()) return;

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRaidTrigger(RaidTriggerEvent event) {
        VanishPlayer vanishPlayer = VanishManager.get(event.getPlayer());

        if (!vanishPlayer.isVanished()) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        VanishPlayer vanishPlayer = VanishManager.get(event.getPlayer());

        if (!vanishPlayer.isVanished()) return;

        Component message = event.joinMessage();

        event.joinMessage(null);

        vanishPlayer.vanish();

        vanishPlayer.setVanishSuperPowers(true);

        Broadcast.announceSilentMessage(message);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        VanishPlayer vanishPlayer = VanishManager.get(event.getPlayer());

        if (!vanishPlayer.isVanished()) return;

        Component message = event.quitMessage();

        event.quitMessage(null);

        vanishPlayer.appear();

        Broadcast.announceSilentMessage(message);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        VanishPlayer vanishPlayer = VanishManager.get(event.getEntity());

        if (!vanishPlayer.isVanished()) return;

        Component message = event.deathMessage();

        event.deathMessage(null);

        Broadcast.announceSilentMessage(message);
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        VanishPlayer vanishPlayer = VanishManager.get(event.getPlayer());

        if (!vanishPlayer.isVanished()) return;

        Component message = event.message();

        if (message == null) return;

        event.message(null);

        Broadcast.announceSilentMessage(message);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        VanishPlayer vanishPlayer = VanishManager.get(event.getPlayer());

        if (!vanishPlayer.isVanished()) return;

        vanishPlayer.setVanishSuperPowers(true);
    }
}