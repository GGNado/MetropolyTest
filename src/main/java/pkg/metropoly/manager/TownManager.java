package pkg.metropoly.manager;

import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pkg.metropoly.Metropoly;
import pkg.metropoly.enums.Permesso;
import pkg.metropoly.enums.Tipologia;
import pkg.metropoly.model.Cittadino;
import pkg.metropoly.model.Cuboid;
import pkg.metropoly.model.Territorio;
import pkg.metropoly.model.Town;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TownManager {
    private YamlConfiguration yamlConfigurationTown;
    private YamlConfiguration yamlConfigurationClaim;
    private File configFileTown;
    private File configFileClaim;
    private Metropoly metropoly;

    public TownManager(Metropoly metropoly) {
        this.metropoly = metropoly;
        configFileTown = new File(metropoly.getDataFolder(), "towns.yml");
        configFileClaim = new File(metropoly.getDataFolder(), "claims.yml");
        if (configFileTown.exists()){
            yamlConfigurationTown = YamlConfiguration.loadConfiguration(configFileTown);
        } else {
            System.out.println("towns.yml");
        }
        if (configFileClaim.exists()){
            yamlConfigurationClaim = YamlConfiguration.loadConfiguration(configFileClaim);
        } else {
            System.out.println("claims.yml");
        }
    }

   /* public Town setTown(Player player, String nomeCitta){
        Town town = new Town();
        List<String> cittadini = new ArrayList<>();
        cittadini.add(player.getName());
        yamlConfigurationTown.set("Citta." + nomeCitta + ".nome", nomeCitta);
        yamlConfigurationTown.set("Citta." + nomeCitta + ".sindaco", player.getName());
        yamlConfigurationTown.set("Citta." + nomeCitta + ".banca", 100);
        yamlConfigurationTown.set("Citta." + nomeCitta + ".cittadini", cittadini);
        yamlConfigurationTown.set("Citta." + nomeCitta + ".numeroclaim", 1);

        saveConfig(yamlConfigurationTown, configFileTown);

        town.setNomeCitta(nomeCitta);
        town.setSindaco(player.getName());
        town.setBanca(100.0);
        town.setCittadini(cittadini);
        town.setNumeroClaim(1);
        return town;
    } */

    public Town setTown(Player player, String nomeCitta) {
        Town town = new Town();

        yamlConfigurationTown.set("Citta." + nomeCitta.toLowerCase() + ".nome", nomeCitta);
        yamlConfigurationTown.set("Citta." + nomeCitta.toLowerCase() + ".sindaco", player.getUniqueId().toString());
        yamlConfigurationTown.set("Citta." + nomeCitta.toLowerCase() + ".banca", 0);

        List<String> cittadiniList = new ArrayList<>();
        cittadiniList.add(player.getUniqueId().toString());

        yamlConfigurationTown.set("Citta." + nomeCitta.toLowerCase() + ".cittadini", cittadiniList);
        yamlConfigurationTown.set("Citta." + nomeCitta.toLowerCase() + ".numeroClaim", 1);

        saveConfig(yamlConfigurationTown, configFileTown);

        setTownSpawn(player, nomeCitta.toLowerCase());

        Cittadino cittadino = metropoly.getCittadini().get(player.getUniqueId());
        town.setNomeCitta(nomeCitta);
        town.setSindaco(cittadino);
        town.setBanca(0.0);
        town.addCittadino(cittadino);
        town.setNumeroClaim(1);

        return town;
    }


    public List<Town> getAllTowns(Map<UUID, Cittadino> cittadiniMap) {
        List<Town> città = new ArrayList<>();

        for (String str : yamlConfigurationTown.getConfigurationSection("Citta.").getKeys(false)) {
            Town town = new Town();

            // Recupera il sindaco
            UUID sindacoUUID = UUID.fromString(yamlConfigurationTown.getString("Citta." + str + ".sindaco"));
            Cittadino sindaco = cittadiniMap.get(sindacoUUID);
            if (sindaco == null) {
                // Gestisci il caso in cui il sindaco non sia trovato nella mappa (opzionale)
                continue;
            }
            town.setSindaco(sindaco);

            // Imposta altri attributi della città
            town.setBanca(yamlConfigurationTown.getDouble("Citta." + str + ".banca"));
            town.setNomeCitta(yamlConfigurationTown.getString("Citta." + str + ".nome"));
            town.setNumeroClaim(yamlConfigurationTown.getInt("Citta." + str + ".numeroClaim"));

            // Aggiungi i cittadini alla città
            for (String uuid : yamlConfigurationTown.getStringList("Citta." + str + ".cittadini")) {
                UUID cittadinoUUID = UUID.fromString(uuid);
                Cittadino cittadino = cittadiniMap.get(cittadinoUUID);
                if (cittadino != null) {
                    town.addCittadino(cittadino);
                }
            }

            città.add(town);
        }

        return città;
    }


    public Town getTownByPlayer(Player player){
        for (Town town : metropoly.getTowns()){
            for (Cittadino cittadino : town.getCittadini()){
                if (cittadino.getPlayer().getUniqueId().equals(player.getUniqueId()))
                    return town;
            }
        }
        return null;
    }

    public void addNumeroClaim(Integer numero, Town town){
        yamlConfigurationTown.set("Citta." + town.getNomeCitta().toLowerCase() + ".numeroClaim", numero);
        saveConfig(yamlConfigurationTown, configFileTown);
        town.addNumeroClaim(numero);
    }

    public void setTownSpawn(Player player, String nomeTown) {
        Location location = player.getLocation();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        String world = location.getWorld().getName();

        yamlConfigurationTown.set("Citta." + nomeTown.toLowerCase() + ".spawn.x", x);
        yamlConfigurationTown.set("Citta." + nomeTown.toLowerCase() + ".spawn.y", y);
        yamlConfigurationTown.set("Citta." + nomeTown.toLowerCase() + ".spawn.z", z);
        yamlConfigurationTown.set("Citta." + nomeTown.toLowerCase() + ".spawn.yaw", yaw);
        yamlConfigurationTown.set("Citta." + nomeTown.toLowerCase() + ".spawn.pitch", pitch);
        yamlConfigurationTown.set("Citta." + nomeTown.toLowerCase() + ".spawn.world", world);

        saveConfig(yamlConfigurationTown, configFileTown);
    }


    public Territorio setClaim(String nomeCitta, String nomePlayer, Chunk chunk, String town){
        String path = "claims." +  town + "." + chunk.getWorld().getName() + "." + chunk.getX() + ":" + chunk.getZ();
        yamlConfigurationClaim.set(path + ".citta", nomeCitta);
        yamlConfigurationClaim.set(path + ".owner", nomePlayer);
        yamlConfigurationClaim.set(path + ".nome", "");
        yamlConfigurationClaim.set(path + ".x", chunk.getX());
        yamlConfigurationClaim.set(path + ".z", chunk.getZ());
        yamlConfigurationClaim.set(path + ".tipologia", Tipologia.DEFAULT.toString());
        yamlConfigurationClaim.set(path + ".permessi.build", false);
        yamlConfigurationClaim.set(path + ".permessi.destroy", false);
        yamlConfigurationClaim.set(path + ".permessi.interact", false);
        saveConfig(yamlConfigurationClaim, configFileClaim);
        Territorio territorio = new Territorio();
        territorio.setNome("");
        territorio.setNomeCitta(nomeCitta);
        territorio.setTipologia(Tipologia.DEFAULT);
        Map<Permesso, Boolean> permessoBooleanMap = new HashMap<>();
        permessoBooleanMap.put(Permesso.BUILD, false);
        permessoBooleanMap.put(Permesso.DESTROY, false);
        permessoBooleanMap.put(Permesso.INTERACT, false);
        territorio.setPermessi(permessoBooleanMap);
        territorio.setX(chunk.getX());
        territorio.setZ(chunk.getZ());
        return territorio;
    }

    public boolean isChunkClaimed(Chunk chunk) {
        String worldName = chunk.getWorld().getName().toLowerCase();
        String chunkCoords = chunk.getX() + ":" + chunk.getZ();

        for (String town : yamlConfigurationClaim.getConfigurationSection("claims").getKeys(false)) {
            String path = "claims." + town.toLowerCase() + "." + worldName + "." + chunkCoords;
            if (yamlConfigurationClaim.contains(path)) {
                return true;
            }
        }

        return false;
    }

    public Territorio getTerritorioByCords(Chunk chunk) {
        String worldName = chunk.getWorld().getName().toLowerCase();
        String chunkCoords = chunk.getX() + ":" + chunk.getZ();

        for (String town : yamlConfigurationClaim.getConfigurationSection("claims").getKeys(false)) {
            String path = "claims." + town.toLowerCase() + "." + worldName + "." + chunkCoords;
            if (yamlConfigurationClaim.contains(path)) {
                Territorio territorio = new Territorio();
                territorio.setNomeCitta(town);
                territorio.setNome(yamlConfigurationClaim.getString(path + ".nome"));
                String str = yamlConfigurationClaim.getString(path + ".tipologia").toUpperCase();
                Tipologia tipologia = Tipologia.valueOf(str);
                territorio.setTipologia(tipologia);
                Map<Permesso, Boolean> permessoMap = new HashMap<>();
                permessoMap.put(Permesso.BUILD, yamlConfigurationClaim.getBoolean(path + ".permessi.build"));
                permessoMap.put(Permesso.INTERACT, yamlConfigurationClaim.getBoolean(path + ".permessi.interact"));
                permessoMap.put(Permesso.DESTROY, yamlConfigurationClaim.getBoolean(path + ".permessi.destroy"));
                territorio.setPermessi(permessoMap);
                return territorio;
            }
        }

        return null;
    }

    public Map<String, Territorio> getAllTerritori() {
        Map<String, Territorio> territorioMap = new HashMap<>();

        for (String town : yamlConfigurationClaim.getConfigurationSection("claims").getKeys(false)) {
            String basePath = "claims." + town.toLowerCase() + ".world";

            for (String chunkCoords : yamlConfigurationClaim.getConfigurationSection(basePath).getKeys(false)) {
                String path = basePath + "." + chunkCoords;

                // Crea un nuovo oggetto Territorio
                Territorio territorio = new Territorio();
                territorio.setNome(yamlConfigurationClaim.getString(path + ".nome"));
                String str = yamlConfigurationClaim.getString(path + ".tipologia").toUpperCase();
                Tipologia tipologia = Tipologia.valueOf(str);
                territorio.setTipologia(tipologia);
                territorio.setNomeCitta(town);
                Map<Permesso, Boolean> permessoMap = new HashMap<>();
                permessoMap.put(Permesso.BUILD, yamlConfigurationClaim.getBoolean(path + ".permessi.build"));
                permessoMap.put(Permesso.INTERACT, yamlConfigurationClaim.getBoolean(path + ".permessi.interact"));
                permessoMap.put(Permesso.DESTROY, yamlConfigurationClaim.getBoolean(path + ".permessi.destroy"));
                territorio.setPermessi(permessoMap);
                territorio.setX(yamlConfigurationClaim.getInt(path + ".x"));
                territorio.setZ(yamlConfigurationClaim.getInt(path + ".z"));

                territorioMap.put(chunkCoords, territorio);
            }
        }
        return territorioMap;
    }


    public boolean isChunkAdjacent(Chunk chunk, Town town) {
        String worldName = chunk.getWorld().getName();
        int x = chunk.getX();
        int z = chunk.getZ();

        String[] adjacentPaths = {
                "claims." + town.getNomeCitta().toLowerCase() + "." + worldName + "." + (x + 1) + ":" + z,
                "claims." + town.getNomeCitta().toLowerCase() + "." + worldName + "." + (x - 1) + ":" + z,
                "claims." + town.getNomeCitta().toLowerCase() + "." + worldName + "." + x + ":" + (z + 1),
                "claims." + town.getNomeCitta().toLowerCase() + "." + worldName + "." + x + ":" + (z - 1)
        };

        for (String path : adjacentPaths) {
            if (yamlConfigurationClaim.contains(path)) {
                // ADIACENTE
                return true;
            }
        }
        return false;
    }
    private void saveConfig(YamlConfiguration yamlConfiguration, File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio del file", e);
        }
    }

    public Location getTownSpawn(Town town) {
        String nomeTown = town.getNomeCitta().toLowerCase();
        if (!yamlConfigurationTown.contains("Citta." + nomeTown + ".spawn.world")){
            System.out.println("La town " + town.getNomeCitta() + " non ha settato lo spawn!");
            return null;
        }
        double x = yamlConfigurationTown.getDouble("Citta." + nomeTown + ".spawn.x");
        double y = yamlConfigurationTown.getDouble("Citta." + nomeTown + ".spawn.y");
        double z = yamlConfigurationTown.getDouble("Citta." + nomeTown + ".spawn.z");
        float yaw = (float) yamlConfigurationTown.getDouble("Citta." + nomeTown + ".spawn.yaw");
        float pitch = (float) yamlConfigurationTown.getDouble("Citta." + nomeTown + ".spawn.pitch");
        String worldName = yamlConfigurationTown.getString("Citta." + nomeTown + ".spawn.world");

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;  // Se il mondo non esiste, ritorna null
        }

        return new Location(world, x, y, z, yaw, pitch);
    }

    public void addBanca(Town town, int numero) {
        yamlConfigurationTown.set("Citta." + town.getNomeCitta().toLowerCase() + ".banca", numero);
        saveConfig(yamlConfigurationTown, configFileTown);
    }
}
