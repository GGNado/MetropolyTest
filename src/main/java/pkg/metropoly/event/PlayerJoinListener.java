package pkg.metropoly.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import pkg.metropoly.Metropoly;
import pkg.metropoly.model.Cittadino;

public class PlayerJoinListener implements Listener {
    private Metropoly metropoly;

    public PlayerJoinListener(Metropoly metropoly){
        this.metropoly = metropoly;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (player.hasPlayedBefore()) {
            e.setJoinMessage(ChatColor.GREEN + "Il " + ChatColor.LIGHT_PURPLE + metropoly.getCittadini().get(player.getUniqueId()).getRuolo().toString() + " " + player.getName() + ChatColor.GREEN + " si è svegliato...");
            player.sendTitle(ChatColor.GREEN + "Bentornato", "", 10, 50, 10);
            setPlayerPrefix(player, "[Dux]");
        } else {
            e.setJoinMessage(ChatColor.GREEN + "A lampedusa è arrivato " + ChatColor.LIGHT_PURPLE + player.getName());
            Cittadino cittadino = metropoly.getCittadinoManager().addCittadino(player);
            metropoly.addCittadino(cittadino);
            setPlayerPrefix(player, "[Negro]");
            player.teleport(new Location(Bukkit.getWorld("world_spawn"), 0, 100, 0, 90, 0));
        }
    }

    public void setPlayerPrefix(Player player, String prefix) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(player.getName());

        if (team == null) {
            team = scoreboard.registerNewTeam(player.getName());
        }

        // Imposta il prefisso e il suffisso
        team.setPrefix(ChatColor.GREEN + prefix + ChatColor.RESET);
        team.addEntry(player.getName()); // Aggiunge il giocatore al team
    }


}
