package pkg.metropoly.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import pkg.metropoly.Metropoly;
import pkg.metropoly.enums.Permesso;
import pkg.metropoly.model.Territorio;

public class PlayerInteractListener implements Listener {
    private Metropoly metropoly;
    public PlayerInteractListener(Metropoly metropoly) {
        this.metropoly = metropoly;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) {
            return;
        }

        Player player = event.getPlayer();
        if (player.isOp()){
            return;
        }
        if (player.getWorld().getName().equalsIgnoreCase("world_spawn")){
            event.setCancelled(false);
            return;
        }

        if (player.getWorld().getName().equalsIgnoreCase("minerali")){
            return;
        }
        Territorio territorio = metropoly.getTownManager().getTerritorioByCords(event.getClickedBlock().getChunk());
        if (territorio == null) {
            return;
        }
        if (metropoly.getTownManager().getTownByPlayer(player) != null) {
            if (!territorio.getNomeCitta().equalsIgnoreCase(metropoly.getTownManager().getTownByPlayer(player).getNomeCitta())){
                if (!territorio.getPermessi().get(Permesso.INTERACT)){
                    player.sendMessage(ChatColor.RED + "Non puoi interagire in questa zona!");
                    event.setCancelled(true);
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "Non puoi interagire in questa zona!");
            event.setCancelled(true);
        }
    }
}
