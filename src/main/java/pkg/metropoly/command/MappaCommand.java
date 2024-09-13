package pkg.metropoly.command;

import io.papermc.paper.event.player.AsyncChatCommandDecorateEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MappaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            TextComponent message = new TextComponent("§aClicca qui per visitare la mappa: ");

            // Crea il link cliccabile con colore e sottolineatura usando §
            TextComponent link = new TextComponent("§9§nhttp://mc2.gamehosting.it:5239");

            // Imposta l'evento di clic per aprire il link
            link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://mc2.gamehosting.it:5239"));

            // Aggiungi il link al messaggio
            message.addExtra(link);

            // Invia il messaggio al giocatore
            player.spigot().sendMessage(message);
        }
        return false;
    }
}
