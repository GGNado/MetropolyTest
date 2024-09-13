package pkg.metropoly.model;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PicconeMobSpawner {

    private ItemStack item;

    public PicconeMobSpawner(){
        this.item = new ItemStack(Material.WOODEN_PICKAXE, 1);
        ItemMeta meta = this.item.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "Piccone divino");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + "Permette di recuperare mobspawner");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if (meta instanceof Damageable) {
            Damageable damageable = (Damageable) meta;
            damageable.setDamage(Material.WOODEN_PICKAXE.getMaxDurability() - 4);
        }

        this.item.setItemMeta(meta);
    }

    public ItemStack getItem(){
        return this.item;
    }

}
