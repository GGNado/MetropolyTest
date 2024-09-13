package pkg.metropoly.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TutorialCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "\n---  Guida ai Comandi  ---\n");

            player.sendMessage(ChatColor.GREEN + "/tutorial" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Mostra questa guida ai comandi.");
            player.sendMessage(ChatColor.GREEN + "/town info" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Mostra le informazioni sulla tua città.");
            player.sendMessage(ChatColor.GREEN + "/town create <nome>" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Crea una nuova città con il nome specificato.");
            player.sendMessage(ChatColor.GREEN + "/town claim" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Claim un chunk per la tua città.");
            player.sendMessage(ChatColor.GREEN + "/town deposit <quantità>" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Deposita il gold dal wallet alla banca città.");
            player.sendMessage(ChatColor.GREEN + "/town list" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Mostra la lista di tutte le città esistenti.");
            player.sendMessage(ChatColor.GREEN + "/town infoc" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Mostra le informazioni sul chunk claimato.");
            //player.sendMessage(ChatColor.GREEN + "/town setc <tipologia>" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Imposta la tipologia di un territorio claimato.");
            player.sendMessage(ChatColor.GREEN + "/town set spawn" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Imposta lo spawn della tua città.");
            player.sendMessage(ChatColor.GREEN + "/town spawn" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Teletrasporta alla tua città.");
            player.sendMessage(ChatColor.GREEN + "/spawn" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Teletrasporta il giocatore allo spawn iniziale, al centro della mappa.");
            player.sendMessage(ChatColor.GREEN + "/hub" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Teletrasporta il giocatore alla lobby, dove può trovare informazioni principali.");
            player.sendMessage(ChatColor.GREEN + "/wallet" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Mostra o chiude il portafoglio.");
            player.sendMessage(ChatColor.GREEN + "/wallet deposit <quantità>" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Deposita la quantità specificata di lingotti d'oro nel portafoglio.");
            player.sendMessage(ChatColor.GREEN + "/wallet withdraw <quantità>" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Ritira la quantità specificata di lingotti d'oro dal portafoglio.");
            player.sendMessage(ChatColor.GREEN + "/mappa" + ChatColor.WHITE + " - " + ChatColor.YELLOW + "Mostra la mappa dinamica.");
            player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "\n---  Fine della Guida  ---\n");

        }
        return false;
    }
}
