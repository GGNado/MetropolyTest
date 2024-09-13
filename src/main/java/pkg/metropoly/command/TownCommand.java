package pkg.metropoly.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;
import org.jetbrains.annotations.NotNull;
import pkg.metropoly.Metropoly;
import pkg.metropoly.enums.Permesso;
import pkg.metropoly.enums.Ruolo;
import pkg.metropoly.enums.Tipologia;
import pkg.metropoly.manager.DynmapTerritoryManager;
import pkg.metropoly.model.Cittadino;
import pkg.metropoly.model.Territorio;
import pkg.metropoly.model.Town;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TownCommand implements CommandExecutor {
    private Metropoly metropoly;
    private final Integer prezzoTown = 50;
    private final Integer prezzoClaim = 5;
    public TownCommand(Metropoly metropoly) {
        this.metropoly = metropoly;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length == 0) {
                listaComandi(player);
                return false;
            }

            if (strings.length == 1 && strings[0].equalsIgnoreCase("info")){
                infoTown(player);
                return true;

            } else if (strings.length == 2 && strings[0].equalsIgnoreCase("create")){
                createTown(player, strings[1]);
                return true;

            } else if (strings.length == 1 && strings[0].equalsIgnoreCase("claim")){
                claimChunk(player);
                return true;

            } else if (strings.length == 1 && strings[0].equalsIgnoreCase("list")) {
                townList(player);
                return true;
            } else if (strings.length == 1 && strings[0].equalsIgnoreCase("infoc")) {
                claimInfo(player);
                return true;
            } else if (strings.length == 2 && strings[0].equalsIgnoreCase("setc")){
                setTipoTerritorio(strings[1], player);
                return true;
                // town set spawn
            } else if (strings.length == 2 && strings[0].equalsIgnoreCase("set") && strings[1].equalsIgnoreCase("spawn")){
                setSpawnTown(player);
                return true;
            } else if (strings.length == 1 && strings[0].equalsIgnoreCase("spawn")){
                tpPlayerTown(player);
                return true;
            } else if (strings.length == 2 && strings[0].equalsIgnoreCase("deposit")){
                try {
                    int numero = Integer.parseInt(strings[1]);
                    depositTown(player, numero);
                }catch (NumberFormatException e){
                    player.sendMessage(ChatColor.RED + "Devi inserire un numero!");
                    return false;
                }
            }
        }
        return false;
    }

    private void depositTown(Player player, int numero) {
        Town town = metropoly.getTownManager().getTownByPlayer(player);
        if (town == null) {
            player.sendMessage(ChatColor.RED + "Devi far parte di una town!");
            return;
        }
        Cittadino cittadino = metropoly.getCittadini().get(player.getUniqueId());
        if (cittadino.getSoldi() < numero) {
            player.sendMessage(ChatColor.RED + "Non hai abbastanza soldi!");
            return;
        }

        metropoly.getCittadini().get(player.getUniqueId()).removeSoldi(numero);
        metropoly.getCittadinoManager().removeSoldi(cittadino);

        metropoly.getTowns().remove(town);
        town.setBanca(town.getBanca() + numero);
        metropoly.addTown(town);
        metropoly.getTownManager().addBanca(town, numero);
        player.sendMessage(ChatColor.GREEN + "Hai depositato " + ChatColor.GOLD + numero + " gold" + ChatColor.GREEN + " nella banca della tua città!");

    }

    private void tpPlayerTown(Player player) {
        Town town = metropoly.getTownManager().getTownByPlayer(player);
        if (town == null) {
            player.sendMessage(ChatColor.RED + "Devi far parte di una town!");
            return;
        }
        Location location = metropoly.getTownManager().getTownSpawn(town);
        // Fai runnable 5 sec
        player.teleport(location);
        player.spawnParticle(Particle.FLAME, location, 15);
    }

    private void setSpawnTown(Player player) {
        Town town = metropoly.getTownManager().getTownByPlayer(player);
        if (town == null){
            player.sendMessage(ChatColor.RED + "Devi prima far parte di una town!");
            return;
        }
        Territorio territorio = metropoly.getTownManager().getTerritorioByCords(player.getChunk());
        if (territorio == null) {
            player.sendMessage(ChatColor.RED + "Questa zona non fa parte della tua town!");
            return;
        }
        if (!territorio.getNomeCitta().equalsIgnoreCase(town.getNomeCitta())){
            player.sendMessage(ChatColor.RED + "Questa zona non fa parte della tua town!");
            return;
        }
        metropoly.getTownManager().setTownSpawn(player, town.getNomeCitta().toLowerCase());
        player.sendMessage(ChatColor.GREEN + "Spawn della town inserito!");
    }

    private void setTipoTerritorio(String tipo, Player player) {
        Territorio territorio = metropoly.getTownManager().getTerritorioByCords(player.getChunk());
        if (territorio == null){
            player.sendMessage(ChatColor.RED + "Il territorio non fa parte di una town!");
            return;
        }

        if (!territorio.getNomeCitta().equalsIgnoreCase(metropoly.getTownManager().getTownByPlayer(player).getNomeCitta())){
            player.sendMessage(ChatColor.RED + "Il territorio non fa parte della tua town!");
        }

        switch (tipo.toLowerCase()){
            case "falegnameria" -> territorio.setTipologia(Tipologia.FALEGNAMERIA);
            case "erba" -> territorio.setTipologia(Tipologia.DRUG);
            case "miniera" -> territorio.setTipologia(Tipologia.MINIERA);
            default -> territorio.setTipologia(Tipologia.DEFAULT);
        }


    }

    private void claimInfo(Player player) {
        Territorio territorio = metropoly.getTownManager().getTerritorioByCords(player.getChunk());
        if (territorio == null){
            player.sendMessage(ChatColor.RED + "Il territorio non fa parte di una town!");
            return;
        }

        player.sendMessage(ChatColor.GREEN + "- - - INFO TERRITORIO - - - \n\n");
        player.sendMessage(ChatColor.YELLOW + "Nome Territorio: " + ChatColor.YELLOW + territorio.getNome());
        player.sendMessage(ChatColor.YELLOW + "Città: " + ChatColor.YELLOW + territorio.getNomeCitta());
        player.sendMessage(ChatColor.YELLOW + "Tipologia: " + ChatColor.YELLOW + territorio.getTipologia().toString());
        player.sendMessage(ChatColor.YELLOW + "Build: " + ChatColor.GREEN + territorio.getPermessi().get(Permesso.BUILD).toString());
        player.sendMessage(ChatColor.YELLOW + "Destroy: " + ChatColor.GREEN + territorio.getPermessi().get(Permesso.DESTROY).toString());
        player.sendMessage(ChatColor.YELLOW + "Interact: " + ChatColor.GREEN + territorio.getPermessi().get(Permesso.INTERACT).toString());
    }

    private void townList(Player player) {
        StringBuilder messaggio = new StringBuilder(ChatColor.GOLD + "- - - Lista delle città - - - \n\n" + ChatColor.RESET);

        for (Town town : metropoly.getTowns()) {
            messaggio.append(ChatColor.GREEN).append(town.getNomeCitta()).append(ChatColor.RESET).append("\n");
        }
        player.sendMessage(messaggio.toString());
        //FAI HOVER

    }

    public void listaComandi(Player player){
        player.sendMessage(ChatColor.RED + "- /town info");
        player.sendMessage(ChatColor.RED + "- /town infoc");
        player.sendMessage(ChatColor.RED + "- /town create <town name>");
        player.sendMessage(ChatColor.RED + "- /town claim");
    }

    public void infoTown(Player player){
        Town townPlayer = metropoly.getTownManager().getTownByPlayer(player);
        if (townPlayer == null){
            player.sendMessage(ChatColor.RED + "Non fai parte di nessuna town!");
            return;
        }
        // Costruzione della stringa dei nomi dei cittadini
        StringBuilder cittadiniNomi = new StringBuilder();
        for (Cittadino cittadino : townPlayer.getCittadini()) {
            cittadiniNomi.append(cittadino.getPlayer().getName()).append(", ");
        }

        // Rimozione dell'ultima virgola e spazio
        if (cittadiniNomi.length() > 2) {
            cittadiniNomi.setLength(cittadiniNomi.length() - 2);
        }

        // Invio del messaggio al player
        player.sendMessage(ChatColor.GREEN +
                "\n\n- - - - - - INFO CITTA - - - - - -\n\n" +
                ChatColor.YELLOW + "Nome: " + ChatColor.GREEN + townPlayer.getNomeCitta() + "\n" +
                ChatColor.YELLOW + "Banca: " + ChatColor.GREEN + townPlayer.getBanca().intValue() + "\n" +
                ChatColor.YELLOW + "Sindaco: " + ChatColor.GREEN + townPlayer.getSindaco().getPlayer().getName() + "\n" +
                ChatColor.YELLOW + "Cittadini: " + ChatColor.GREEN + cittadiniNomi + "\n" +
                ChatColor.YELLOW + "Numero Claim: " + ChatColor.GREEN + townPlayer.getNumeroClaim());
    }

    public void createTown(Player player, String nomeCitta){
        if (metropoly.getCittadini().get(player.getUniqueId()).getSoldi() < prezzoTown){
            player.sendMessage(ChatColor.RED + "Ti servono " + ChatColor.GOLD +  prezzoTown + " gold" + ChatColor.RED + " per creare la town!");
            return;
        }
        Town townPlayer = metropoly.getTownManager().getTownByPlayer(player);
        if (townPlayer != null){
            player.sendMessage(ChatColor.RED + "Devi prima abbandonare la tua città!");
            return;
        }
        if (metropoly.getTownManager().isChunkClaimed(player.getChunk())){
            player.sendMessage(ChatColor.RED + "Il chunk è gia stato claimato!");
            return;
        }

        Territorio territorio = metropoly.getTownManager().setClaim(nomeCitta, player.getName(), player.getChunk(), nomeCitta.toLowerCase());
        String chiave = territorio.getX() + ":" +territorio.getZ();
        metropoly.addTerritorio(chiave, territorio);

        Town town = metropoly.getTownManager().setTown(player, nomeCitta);
        metropoly.addTown(town);
        player.sendTitle(ChatColor.BOLD + "" + ChatColor.GREEN + "Fondazione con Successo!", ChatColor.YELLOW + nomeCitta, 20, 100, 20);
        Bukkit.broadcastMessage(ChatColor.GREEN + "Il sindaco " + ChatColor.YELLOW + player.getName() + " ha fondato la città " + ChatColor.AQUA + nomeCitta);
        metropoly.getCittadini().get(player.getUniqueId()).removeSoldi(prezzoTown);
        metropoly.getCittadinoManager().removeSoldi(metropoly.getCittadini().get(player.getUniqueId()), prezzoTown);

    }

    public void claimChunk(Player player){
        Town townPlayer = metropoly.getTownManager().getTownByPlayer(player);
        if (townPlayer == null){
            player.sendMessage(ChatColor.RED + "Non fai parte di nessuna town!");
            return;
        }
        if (metropoly.getTownManager().isChunkClaimed(player.getChunk())){
            player.sendMessage(ChatColor.RED + "Il chunk è gia stato claimato!");
            return;
        }
        if (!metropoly.getTownManager().isChunkAdjacent(player.getChunk(), townPlayer)){
            player.sendMessage(ChatColor.RED + "Il chunk non è adiacente al tuo territorio");
            return;
        }

        Town town = metropoly.getTownManager().getTownByPlayer(player);

        if (town.getBanca() < prezzoClaim){
            player.sendMessage(ChatColor.RED + "Hai bisogno di " + ChatColor.GOLD + prezzoClaim + " Gold" + ChatColor.RED + " nella banca per claimare!");
            return;
        }

        /*if (cittadino.getSoldi() < prezzoClaim){
            player.sendMessage(ChatColor.RED + "Hai bisogno di " + ChatColor.GOLD + prezzoClaim + "  Gold " + ChatColor.RED + " per claimare!");
            return;
        }*/

        Territorio territorio = metropoly.getTownManager().setClaim(townPlayer.getNomeCitta(), player.getName(), player.getChunk(), townPlayer.getNomeCitta().toLowerCase());
        String chiave = territorio.getX() + ":" +territorio.getZ();
        metropoly.addTerritorio(chiave, territorio);
        player.sendMessage(ChatColor.GREEN + "Claim Salvato!");

        metropoly.getTownManager().addNumeroClaim(townPlayer.getNumeroClaim() + 1, townPlayer);


        //metropoly.getCittadini().get(player.getUniqueId()).removeSoldi(prezzoClaim);
        //metropoly.getCittadinoManager().removeSoldi(cittadino, prezzoClaim);

        metropoly.getTowns().remove(town);
        town.setBanca(town.getBanca() - prezzoClaim);
        metropoly.addTown(town);
        metropoly.getTownManager().addBanca(town, town.getBanca().intValue());
        DynmapTerritoryManager dynmapTerritoryManager = metropoly.getDynmapTerritoryManager();
        if (dynmapTerritoryManager != null){
            dynmapTerritoryManager.colorChunkLive(territorio);
        }

    }
}

