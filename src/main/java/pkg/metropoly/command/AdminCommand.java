package pkg.metropoly.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;
import pkg.metropoly.model.PicconeMobSpawner;

import java.util.ArrayList;
import java.util.List;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if (!player.isOp()) return false;

        if (strings.length == 1 && strings[0].equalsIgnoreCase("piccone")){
            PicconeMobSpawner picconeMobSpawner = new PicconeMobSpawner();
            player.getInventory().addItem(picconeMobSpawner.getItem());
            return true;
        } else if (strings.length == 1 && strings[0].equalsIgnoreCase("shop")){
            spawnCustomVillager(player.getWorld(), player.getX(), player.getY(), player.getZ(), "Mercante Magico");
            return true;
        } else if (strings.length == 1 && strings[0].equalsIgnoreCase("shopB")) {
            spawnCustomVillager(player.getWorld(), player.getX(), player.getY(), player.getZ(), "Concrete Master");
            return true;
        } else if (strings.length == 1 && strings[0].equalsIgnoreCase("shopC")) {
            spawnCustomVillager(player.getWorld(), player.getX(), player.getY(), player.getZ(), "Massimo Bossetti");
        } else if (strings.length == 1 && strings[0].equalsIgnoreCase("shopD")) {
            spawnCustomVillager(player.getWorld(), player.getX(), player.getY(), player.getZ(), "Wood Master");
        }

        return false;
    }


    public void spawnCustomVillager(World world, double x, double y, double z, String nome) {
        Villager villager = (Villager) world.spawnEntity(new Location(world, x, y, z), EntityType.VILLAGER);

        villager.setCustomName(ChatColor.GOLD + nome);
        villager.setCustomNameVisible(true);

    }
}
