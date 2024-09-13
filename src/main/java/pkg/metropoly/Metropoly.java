package pkg.metropoly;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.MarkerSet;
import pkg.metropoly.command.*;
import pkg.metropoly.command.tabs.PortafoglioTab;
import pkg.metropoly.command.tabs.TownTab;
import pkg.metropoly.event.*;
import pkg.metropoly.manager.CittadinoManager;
import pkg.metropoly.manager.ConfigManager;
import pkg.metropoly.manager.DynmapTerritoryManager;
import pkg.metropoly.manager.TownManager;
import pkg.metropoly.model.Cittadino;
import pkg.metropoly.model.Territorio;
import pkg.metropoly.model.Town;
import pkg.metropoly.utility.PlaceHolderAPI;

import java.util.*;

public final class Metropoly extends JavaPlugin {
    private TownManager townManager;
    private CittadinoManager cittadinoManager;
    private List<Town> towns;
    private Map<String, Territorio> territorioMap;
    private Map<UUID, Cittadino> cittadiniMap;
    //private Map<UUID, Territorio> cittadinoTerritorioMap;

    private DynmapAPI dynmapAPI;
    private MarkerSet markerSet;
    private DynmapTerritoryManager dynmapTerritoryManager;
    @Override
    public void onEnable() {
        ConfigManager.setupConfig(this);
        reloadConfig();
        saveResource("towns.yml", false);
        saveResource("claims.yml", false);
        saveResource("cittadini.yml", false);

        townManager = new TownManager(this);
        cittadinoManager = new CittadinoManager(this);


        getCommand("town").setExecutor(new TownCommand(this));
        getCommand("town").setTabCompleter(new TownTab());

        getCommand("wallet").setExecutor(new PortafoglioCommand(this));
        getCommand("wallet").setTabCompleter(new PortafoglioTab());

        getCommand("materiali").setExecutor(new MaterialiCommand());
        getCommand("mappa").setExecutor(new MappaCommand());
        getCommand("hub").setExecutor(new HubCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("tutorial").setExecutor(new TutorialCommand());
        getCommand("metropoly").setExecutor(new AdminCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerMovementListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerBreakListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerBuildListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMessageListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractEntity(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDropListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CreeperGriefListener(this), this);

        cittadiniMap = cittadinoManager.getAllCittadini();
        territorioMap = townManager.getAllTerritori();
        towns = townManager.getAllTowns(cittadiniMap);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceHolderAPI(this).register();
        }

        Plugin dynmap = Bukkit.getServer().getPluginManager().getPlugin("dynmap");
        if (dynmap instanceof DynmapAPI) {
            dynmapAPI = (DynmapAPI) dynmap;
            getLogger().info("Dynmap API trovata!");
            DynmapTerritoryManager dynmapTerritoryManager = new DynmapTerritoryManager(dynmapAPI, territorioMap, townManager);
            dynmapTerritoryManager.colorChunksOnDynmap();
            this.dynmapTerritoryManager = dynmapTerritoryManager;
            for (Town town : towns) {
                dynmapTerritoryManager.addTownSpawnMarker(town);
            }
        } else {
            getLogger().warning("Dynmap non trovato!");
        }
    }

    public List<Town> getTowns() {
        return towns;
    }
    public void addTown(Town town){
        this.towns.add(town);
    }

    public void addTerritorio(String string, Territorio territorio){
        this.territorioMap.put(string, territorio);
    }

    public TownManager getTownManager() {
        return townManager;
    }

    public CittadinoManager getCittadinoManager() {
        return cittadinoManager;
    }

    public Map<UUID, Cittadino> getCittadini() {
        return cittadiniMap;
    }

    public void addCittadino(Cittadino cittadino){
        this.cittadiniMap.put(cittadino.getPlayer().getUniqueId(), cittadino);
    }

    public Map<String, Territorio> getTerritorioMap() {
        return territorioMap;
    }

    public DynmapTerritoryManager getDynmapTerritoryManager() {
        return dynmapTerritoryManager;
    }

    @Override
    public void onDisable() {
    }
}
