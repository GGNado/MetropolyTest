package pkg.metropoly.command;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import pkg.metropoly.Metropoly;
import pkg.metropoly.model.Cittadino;
import pkg.metropoly.model.Town;
import pkg.metropoly.utility.PlaceHolderAPI;

public class PortafoglioCommand implements CommandExecutor {
    private Metropoly metropoly;

    public PortafoglioCommand(Metropoly metropoly) {
        this.metropoly = metropoly;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (strings.length == 0){
                openCloseWallet(player);
                return true;
            } else if (strings.length == 2 && strings[0].equals("deposit")){
                try {
                    int numero = Integer.parseInt(strings[1]);
                    deposit(player, numero);
                }catch (NumberFormatException e){
                    player.sendMessage(ChatColor.RED + "Devi inserire un numero");
                }
            } else if (strings.length == 2 && strings[0].equals("withdraw")){
                try {
                    int numero = Integer.parseInt(strings[1]);
                    withdraw(player, numero);
                }catch (NumberFormatException e){
                    player.sendMessage(ChatColor.RED + "Devi inserire un numero");
                }
            }
        }
        return false;
    }

    public void withdraw(Player player, int numero) {
        Inventory inventory = player.getInventory();

        Cittadino cittadino = metropoly.getCittadini().get(player.getUniqueId());
        if (cittadino.getSoldi() >= numero) {
            if (hasInventorySpace(player, numero)) {
                addGoldToInventory(player, numero);
                cittadino.removeSoldi(numero);
                metropoly.getCittadinoManager().removeSoldi(cittadino);
                player.sendMessage(ChatColor.GOLD + "Hai prelevato " + numero + " lingotti d'oro dal tuo portafoglio.");
            } else {
                player.sendMessage(ChatColor.RED + "Non hai abbastanza spazio nell'inventario per i lingotti d'oro.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Non hai abbastanza soldi per il prelievo.");
        }
    }

    public void openCloseWallet(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        if (scoreboard == null || scoreboard == Bukkit.getScoreboardManager().getMainScoreboard()) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("Wallet", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Wallet");

            int score = 15;

            Score sectionPersonal = objective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Info Personali");
            sectionPersonal.setScore(score--);

            Score nome = objective.getScore(ChatColor.GREEN + "Nome: " + ChatColor.YELLOW + player.getName());
            nome.setScore(score--);

            String message = ChatColor.YELLOW + "%Metropoly_playerWallet%";
            String parsedMessage = PlaceholderAPI.setPlaceholders(player, message);

            Score soldi = objective.getScore(ChatColor.GREEN + "Soldi: " + parsedMessage);
            soldi.setScore(score--);

            Score ruolo = objective.getScore(ChatColor.GREEN + "Ruolo: " + ChatColor.YELLOW + metropoly.getCittadini().get(player.getUniqueId()).getRuolo().toString());
            ruolo.setScore(score--);

            // Divider
            Score divider = objective.getScore(ChatColor.GRAY + "----------------");
            divider.setScore(score--);

            // Sezione Info Città
            Score sectionTown = objective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Info Città");
            sectionTown.setScore(score--);

            Town town = metropoly.getTownManager().getTownByPlayer(player);

            if (town != null) {
                Score citta = objective.getScore(ChatColor.GREEN + "Città: " + ChatColor.YELLOW + town.getNomeCitta());
                citta.setScore(score--);

                message = ChatColor.YELLOW + "%Metropoly_townBank%";
                parsedMessage = PlaceholderAPI.setPlaceholders(player, message);
                Score bancaTown = objective.getScore(ChatColor.GREEN + "Banca: " + parsedMessage);
                bancaTown.setScore(score--);
            } else {
                Score noTown = objective.getScore(ChatColor.RED + "Non appartieni a nessuna città");
                noTown.setScore(score--);
            }

            player.setScoreboard(scoreboard);
            player.sendMessage(ChatColor.GREEN + "Apro il portafoglio...");

        } else {
            player.sendMessage(ChatColor.GREEN + "Chiudo il portafoglio...");
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }

    }

    public void deposit(Player player, int numero) {
        Inventory inventory = player.getInventory();
        int goldCount = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == Material.GOLD_INGOT) {
                goldCount += item.getAmount();
            }
        }

        if (goldCount >= numero) {
            removeGoldFromInventory(player, numero);
            player.sendMessage(ChatColor.GOLD + "Deposito completato nel tuo portafoglio!");
            Cittadino cittadino = metropoly.getCittadini().get(player.getUniqueId());
            cittadino.addSoldi(numero);
            metropoly.getCittadinoManager().addSoldi(cittadino);
        } else {
            player.sendMessage(ChatColor.RED + "Non hai abbastanza lingotti d'oro per il deposito.");
        }
    }

    private void removeGoldFromInventory(Player player, int amount) {
        Inventory inventory = player.getInventory();
        int remaining = amount;

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == Material.GOLD_INGOT) {
                int itemAmount = item.getAmount();

                if (itemAmount > remaining) {
                    item.setAmount(itemAmount - remaining);
                    break;
                } else {
                    inventory.remove(item);
                    remaining -= itemAmount;
                    if (remaining <= 0) break;
                }
            }
        }
    }
    private boolean hasInventorySpace(Player player, int numero) {
        Inventory inventory = player.getInventory();
        int freeSpace = 0;

        // Conta gli spazi vuoti nell'inventario o gli slot parzialmente pieni con lingotti d'oro
        for (ItemStack item : inventory.getContents()) {
            if (item == null) {
                freeSpace += 64; // Uno slot vuoto può contenere fino a 64 lingotti d'oro
            } else if (item.getType() == Material.GOLD_INGOT) {
                freeSpace += (64 - item.getAmount()); // Slot parzialmente pieni
            }

            if (freeSpace >= numero) {
                return true; // Se c'è spazio sufficiente, ritorna vero
            }
        }

        return freeSpace >= numero; // Ritorna vero se c'è abbastanza spazio
    }
    private void addGoldToInventory(Player player, int amount) {
        Inventory inventory = player.getInventory();
        int remaining = amount;

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == Material.GOLD_INGOT) {
                int freeSpaceInStack = 64 - item.getAmount(); // Spazio libero nello stack esistente
                if (freeSpaceInStack > 0) {
                    int amountToAdd = Math.min(remaining, freeSpaceInStack);
                    item.setAmount(item.getAmount() + amountToAdd);
                    remaining -= amountToAdd;
                    if (remaining <= 0) return;
                }
            }
        }

        // Aggiungi nuovi stack di lingotti d'oro per il resto del prelievo
        while (remaining > 0) {
            int amountToAdd = Math.min(remaining, 64); // Crea stack di massimo 64 lingotti
            inventory.addItem(new ItemStack(Material.GOLD_INGOT, amountToAdd));
            remaining -= amountToAdd;
        }
    }
}
