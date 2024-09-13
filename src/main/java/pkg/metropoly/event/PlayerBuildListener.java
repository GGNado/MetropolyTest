package pkg.metropoly.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pkg.metropoly.Metropoly;
import pkg.metropoly.enums.Permesso;
import pkg.metropoly.model.Territorio;

public class PlayerBuildListener implements Listener {
    private Metropoly metropoly;

    public PlayerBuildListener(Metropoly metropoly) {
        this.metropoly = metropoly;
    }
    @EventHandler
    public void onPlayerBuild(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()){
            return;
        }
        if (player.getWorld().getName().equalsIgnoreCase("world_spawn")){
            event.setCancelled(true);
            return;
        }

        if (player.getWorld().getName().equalsIgnoreCase("minerali")){
            return;
        }

        if (player.getChunk().getX() == 96 && player.getChunk().getZ() == -96){
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Non puoi costruire in questa zona!");
            return;
        }

        Territorio territorio = metropoly.getTownManager().getTerritorioByCords(event.getBlock().getChunk());
        if (territorio == null) {
            return;
        }
        if (!territorio.getNomeCitta().equalsIgnoreCase(metropoly.getTownManager().getTownByPlayer(player).getNomeCitta())){
            if (!territorio.getPermessi().get(Permesso.BUILD)){
                player.sendMessage(ChatColor.RED + "Non puoi costruire in questa zona!");
                event.setCancelled(true);
            }
        }
    }
}
