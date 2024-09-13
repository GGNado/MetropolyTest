package pkg.metropoly.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerMessageListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String playerName = event.getPlayer().getName();
        String message = event.getMessage();
        String modifiedMessage = ChatColor.GOLD + playerName + ": " + ChatColor.WHITE + message;
        event.setFormat(modifiedMessage);
    }


}
