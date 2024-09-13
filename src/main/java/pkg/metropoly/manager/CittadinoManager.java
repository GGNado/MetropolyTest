package pkg.metropoly.manager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pkg.metropoly.Metropoly;
import pkg.metropoly.enums.Ruolo;
import pkg.metropoly.model.Cittadino;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CittadinoManager {
    private YamlConfiguration yamlConfiguration;
    private File file;
    private Metropoly metropoly;

    public CittadinoManager(Metropoly metropoly) {
        this.metropoly = metropoly;
        file = new File(metropoly.getDataFolder(), "cittadini.yml");
        if (file.exists()){
            yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        } else {
            System.out.println("cittadini.yml");
        }
    }

    public Cittadino addCittadino(Player player){
        String path = "cittadini." + player.getUniqueId().toString();
        yamlConfiguration.set(path + ".nome", player.getName());
        yamlConfiguration.set(path + ".soldi", 0);
        yamlConfiguration.set(path + ".ruolo", "cittadino");

        saveConfig(yamlConfiguration, file);

        Cittadino cittadino = new Cittadino(player, 0, Ruolo.CITTADINO);
        return cittadino;
    }

    private void saveConfig(YamlConfiguration yamlConfiguration, File file) {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio del file", e);
        }
    }

    public Map<UUID, Cittadino> getAllCittadini() {
        Map<UUID, Cittadino> cittadiniMap = new HashMap<>();

        for (String str : yamlConfiguration.getConfigurationSection("cittadini").getKeys(false)) {
            System.out.println(yamlConfiguration.getString("cittadini." + str));
            UUID uuid = UUID.fromString(str);

            Player player = Bukkit.getPlayer(uuid);
            Cittadino cittadino;

            if (player != null) {
                cittadino = new Cittadino(player, 0, Ruolo.CITTADINO);
            } else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                cittadino = new Cittadino(offlinePlayer, 0, Ruolo.CITTADINO);
            }

            cittadino.setSoldi(yamlConfiguration.getInt("cittadini." + str + ".soldi"));
            cittadino.setRuolo(yamlConfiguration.getString("cittadini." + str + ".ruolo"));

            // Aggiungi il cittadino alla mappa
            cittadiniMap.put(uuid, cittadino);
        }

        return cittadiniMap;
    }

    public void addSoldi(Cittadino cittadino) {
        yamlConfiguration.set("cittadini." + cittadino.getPlayer().getUniqueId().toString() + ".soldi", cittadino.getSoldi());
        saveConfig(yamlConfiguration, file);
    }


    public boolean isCittadinoInFile(UUID uuid) {
        String path = "cittadini." + uuid.toString();
        return yamlConfiguration.contains(path);
    }

    public void removeSoldi(Cittadino cittadino, Integer soldi) {
        yamlConfiguration.set("cittadini." + cittadino.getPlayer().getUniqueId().toString() + ".soldi", cittadino.getSoldi() - soldi);
        saveConfig(yamlConfiguration, file);
    }

    public void removeSoldi(Cittadino cittadino) {
        yamlConfiguration.set("cittadini." + cittadino.getPlayer().getUniqueId().toString() + ".soldi", cittadino.getSoldi());
        saveConfig(yamlConfiguration, file);
    }
}
