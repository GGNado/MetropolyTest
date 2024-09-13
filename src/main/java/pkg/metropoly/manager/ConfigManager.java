package pkg.metropoly.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import pkg.metropoly.Metropoly;


import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig(Metropoly regionTest){
        ConfigManager.config = regionTest.getConfig();
        regionTest.saveDefaultConfig();
    }

    public static int getRequiredPlayer(){return config.getInt("required-players");}

    public static int getCountdownSeconds(){return config.getInt("countdown-seconds");}

    public static Location getLobbySpawn(){
        return new Location(
                Bukkit.getWorld(config.getString("lobby-spawn.world")),
                config.getDouble("lobby-spawn.x"),
                config.getDouble("lobby-spawn.y"),
                config.getDouble("lobby-spawn.z"),
                (float) config.getDouble("lobby-spawn.yaw"),
                (float) config.getDouble("lobby-spawn.pitch"));
    }
}