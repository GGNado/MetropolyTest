package pkg.metropoly.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import pkg.metropoly.Metropoly;
import pkg.metropoly.enums.Permesso;
import pkg.metropoly.model.Territorio;

import java.util.ArrayList;

public class PlayerBreakListener implements Listener {
    private Metropoly metropoly;

    public PlayerBreakListener(Metropoly metropoly) {
        this.metropoly = metropoly;
    }
    @EventHandler
    public void onPlayerBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (player.isOp()){
            return;
        }
        if (player.getWorld().getName().equalsIgnoreCase("world_spawn")){
            event.setCancelled(true);
            return;
        }

        if (player.getWorld().getName().equalsIgnoreCase("minerali")){
            checkMobSpawner(player, event);
            return;
        }

        if (player.getChunk().getX() == 96 && player.getChunk().getZ() == -96){
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Non puoi rompere in questa zona!");
            return;
        }

        Territorio territorio = metropoly.getTownManager().getTerritorioByCords(event.getBlock().getChunk());
        if (territorio == null) {
            checkMobSpawner(player, event);
            return;
        }
        if (metropoly.getTownManager().getTownByPlayer(player) == null) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Non puoi rompere in questa zona");
            return;
        }
        if (!territorio.getNomeCitta().equalsIgnoreCase(metropoly.getTownManager().getTownByPlayer(player).getNomeCitta())){
            if (!territorio.getPermessi().get(Permesso.DESTROY)){
                player.sendMessage(ChatColor.RED + "Non puoi rompere un questa zona!");
                event.setCancelled(true);
                return;
            }
        }

        //STA IN TOWN
        checkMobSpawner(player, event);
    }

    public void checkMobSpawner(Player player, BlockBreakEvent event){
        if (event.getBlock().getType().equals(Material.SPAWNER)) {
            if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Piccone divino")){
                ItemStack mobspawner = new ItemStack(Material.SPAWNER, 1);
                ItemMeta meta = mobspawner.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "Mob Spawner");
                ArrayList<String> lore = new ArrayList<>();
                lore.add(ChatColor.GOLD + "Fai /hub per comprare un uovo!");
                meta.setLore(lore);

                mobspawner.setItemMeta(meta);
                Bukkit.getWorld(player.getWorld().getName()).dropItemNaturally(player.getLocation(), mobspawner);
            }
        }
    }
}
