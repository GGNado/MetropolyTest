package pkg.metropoly.utility;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pkg.metropoly.Metropoly;
import pkg.metropoly.model.Cittadino;
import pkg.metropoly.model.Town;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceHolderAPI extends PlaceholderExpansion {

    private Metropoly metropoly;

    public PlaceHolderAPI(Metropoly metropoly) {
        this.metropoly = metropoly;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "Metropoly";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Nadoo";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        List<Cittadino> topCittadini = metropoly.getCittadini().values().stream()
                .sorted(Comparator.comparing(Cittadino::getSoldi).reversed())
                .limit(3)
                .toList();

        List<Town> topTown = metropoly.getTowns().stream()
                .sorted(Comparator.comparing(Town::getBanca).reversed())
                .limit(3)
                .toList();

        List<Town> topClaim = metropoly.getTowns().stream()
                .sorted(Comparator.comparing(Town::getNumeroClaim).reversed())
                .limit(3)
                .toList();


        if (identifier.equals("town")) {
            Town town = metropoly.getTownManager().getTownByPlayer(player);
            if (town == null) {
                return "Africano";
            } else {
                return town.getNomeCitta();
            }
        } else if (identifier.equals("firstWallet")) {
            Cittadino cit = topCittadini.getFirst();
            return ChatColor.GREEN + cit.getPlayer().getName() + ": " + ChatColor.GOLD + cit.getSoldi() + " Gold";
        } else if (identifier.equals("secondWallet")) {
            Cittadino cit = topCittadini.get(1);
            return ChatColor.GREEN + cit.getPlayer().getName() + ": " + ChatColor.GOLD + cit.getSoldi() + " Gold";
        } else if (identifier.equals("thirdWallet")) {
            Cittadino cit = topCittadini.get(2);
            return ChatColor.GREEN + cit.getPlayer().getName() + ": " + ChatColor.GOLD + cit.getSoldi() + " Gold";
        } else if (identifier.equals("firstBank")) {
            Town town = topTown.getFirst();
            return ChatColor.GREEN + town.getNomeCitta() + ": " + ChatColor.GOLD + town.getBanca().intValue() + " Bank Gold";
        } else if (identifier.equals("secondBank")) {
            Town town = topTown.get(1);
            return ChatColor.GREEN + town.getNomeCitta() + ": " + ChatColor.GOLD + town.getBanca().intValue() + " Bank Gold";
        } else if (identifier.equals("thirdBank")) {
            Town town = topTown.get(2);
            return ChatColor.GREEN + town.getNomeCitta() + ": " + ChatColor.GOLD + town.getBanca().intValue() + " Bank Gold";
        } else if (identifier.equals("firstClaim")) {
            Town town = topClaim.getFirst();
            return ChatColor.GREEN + town.getNomeCitta() + ": " + ChatColor.GOLD + town.getNumeroClaim() + " claim";
        } else if (identifier.equals("secondClaim")) {
            Town town = topClaim.get(1);
            return ChatColor.GREEN + town.getNomeCitta() + ": " + ChatColor.GOLD + town.getNumeroClaim() + " claim";
        } else if (identifier.equals("thirdClaim")) {
            Town town = topClaim.get(2);
            return ChatColor.GREEN + town.getNomeCitta() + ": " + ChatColor.GOLD + town.getNumeroClaim() + " claim";
        } else if (identifier.equals("playerWallet")) {
            return String.valueOf(metropoly.getCittadini().get(player.getUniqueId()).getSoldi());
        } else if (identifier.equals("townBank")) {
            Town town = metropoly.getTownManager().getTownByPlayer(player);
            if (town == null) {
                return "Non appartieni a nessuna town!";
            }
            return String.valueOf(town.getBanca().intValue());
        }

        return "Placeholder errore";
    }
}
