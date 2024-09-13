package pkg.metropoly.event;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.ChatPaginator;
import pkg.metropoly.Metropoly;
import pkg.metropoly.model.Territorio;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerMovementListener implements Listener {

    private Metropoly metropoly;
    private final Map<Player, Boolean> playerTerritoryStatus;

    public PlayerMovementListener(Metropoly metropoly) {
        this.metropoly = metropoly;
        this.playerTerritoryStatus = new HashMap<>();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        String cords = player.getChunk().getX() + ":" + player.getChunk().getZ();
        boolean isInTerritory = metropoly.getTerritorioMap().containsKey(cords);

        Boolean wasInTerritory = playerTerritoryStatus.get(player);

        if (wasInTerritory == null) {
            playerTerritoryStatus.put(player, isInTerritory);
            return;
        }

        if (isInTerritory && !wasInTerritory) {
            //player.sendTitle(ChatColor.BOLD + "" + ChatColor.GREEN + "Benvenuto in Città!", ChatColor.YELLOW + metropoly.getTerritorioMap().get(cords).getNomeCitta(), 15, 50, 15);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "~" + ChatColor.AQUA + " Città " + ChatColor.RED + "~"));

        } else if (!isInTerritory && wasInTerritory) {
            //player.sendTitle(ChatColor.BOLD + "" + ChatColor.GREEN + "Sei uscito dalla città!", ChatColor.YELLOW + "Stai attento!", 15, 50, 15);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "~" + ChatColor.AQUA + " Wilderness " + ChatColor.RED + "~"));
        }

        playerTerritoryStatus.put(player, isInTerritory);
    }

    //Implementa i soldi con scoreboard
}
