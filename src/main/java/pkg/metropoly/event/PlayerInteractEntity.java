package pkg.metropoly.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import pkg.metropoly.model.PicconeMobSpawner;

import java.util.ArrayList;
import java.util.List;

public class PlayerInteractEntity implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.VILLAGER){
            Villager villager = (Villager) event.getRightClicked();
            Player player = event.getPlayer();
            if (villager.getName().equals(ChatColor.GOLD + "Mercante Magico")){
                event.setCancelled(true);
                List<MerchantRecipe> recipes = new ArrayList<>();

                PicconeMobSpawner picconeMobSpawner = new PicconeMobSpawner();
                MerchantRecipe piccone = new MerchantRecipe(picconeMobSpawner.getItem(), Integer.MAX_VALUE);
                piccone.addIngredient(new ItemStack(Material.GOLD_INGOT, 58));
                piccone.addIngredient(new ItemStack(Material.DIAMOND, 6));
                recipes.add(piccone);

                MerchantRecipe uovoZombie = new MerchantRecipe(new ItemStack(Material.ZOMBIE_SPAWN_EGG), Integer.MAX_VALUE);
                uovoZombie.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoZombie);

                // Uovo di Scheletro
                MerchantRecipe uovoScheletro = new MerchantRecipe(new ItemStack(Material.SKELETON_SPAWN_EGG), Integer.MAX_VALUE);
                uovoScheletro.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoScheletro);

                // Uovo di Enderman
                MerchantRecipe uovoEnderman = new MerchantRecipe(new ItemStack(Material.ENDERMAN_SPAWN_EGG), Integer.MAX_VALUE);
                uovoEnderman.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoEnderman);

                // Uovo di Ragno
                MerchantRecipe uovoRagno = new MerchantRecipe(new ItemStack(Material.SPIDER_SPAWN_EGG), Integer.MAX_VALUE);
                uovoRagno.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoRagno);

                // Uovo di Ragno Velenoso (Cave Spider)
                MerchantRecipe uovoRagnoVelenoso = new MerchantRecipe(new ItemStack(Material.CAVE_SPIDER_SPAWN_EGG), Integer.MAX_VALUE);
                uovoRagnoVelenoso.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoRagnoVelenoso);

                // Uovo di Creeper
                MerchantRecipe uovoCreeper = new MerchantRecipe(new ItemStack(Material.CREEPER_SPAWN_EGG), Integer.MAX_VALUE);
                uovoCreeper.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoCreeper);

                // Uovo di Blaze
                MerchantRecipe uovoBlaze = new MerchantRecipe(new ItemStack(Material.BLAZE_SPAWN_EGG), Integer.MAX_VALUE);
                uovoBlaze.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoBlaze);

                // Uovo di Strega (Witch)
                MerchantRecipe uovoStrega = new MerchantRecipe(new ItemStack(Material.WITCH_SPAWN_EGG), Integer.MAX_VALUE);
                uovoStrega.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoStrega);

                // Uovo di Slime
                MerchantRecipe uovoSlime = new MerchantRecipe(new ItemStack(Material.SLIME_SPAWN_EGG), Integer.MAX_VALUE);
                uovoSlime.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoSlime);

                // Uovo di Mucca
                MerchantRecipe uovoMucca = new MerchantRecipe(new ItemStack(Material.COW_SPAWN_EGG), Integer.MAX_VALUE);
                uovoMucca.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoMucca);

                // Uovo di Pollo
                MerchantRecipe uovoPollo = new MerchantRecipe(new ItemStack(Material.CHICKEN_SPAWN_EGG), Integer.MAX_VALUE);
                uovoPollo.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoPollo);

                // Uovo di Maiale
                MerchantRecipe uovoMaiale = new MerchantRecipe(new ItemStack(Material.PIG_SPAWN_EGG), Integer.MAX_VALUE);
                uovoMaiale.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoMaiale);

                // Uovo di Villager
                MerchantRecipe uovoVillager = new MerchantRecipe(new ItemStack(Material.VILLAGER_SPAWN_EGG), Integer.MAX_VALUE);
                uovoVillager.addIngredient(new ItemStack(Material.GOLD_INGOT, 30));
                recipes.add(uovoVillager);


                Merchant merchant = Bukkit.createMerchant("Egg Villager Master");
                merchant.setRecipes(recipes);
                player.openMerchant(merchant, true);

            } else if (villager.getName().equals(ChatColor.GOLD + "Concrete Master")){
                event.setCancelled(true);

                List<MerchantRecipe> recipes = new ArrayList<>();

                Material[] concretePowders = {
                        Material.WHITE_CONCRETE_POWDER,
                        Material.BLACK_CONCRETE_POWDER,
                        Material.BLUE_CONCRETE_POWDER,
                        Material.BROWN_CONCRETE_POWDER,
                        Material.CYAN_CONCRETE_POWDER,
                        Material.GRAY_CONCRETE_POWDER,
                        Material.GREEN_CONCRETE_POWDER,
                        Material.LIGHT_BLUE_CONCRETE_POWDER,
                        Material.LIGHT_GRAY_CONCRETE_POWDER,
                        Material.LIME_CONCRETE_POWDER,
                        Material.MAGENTA_CONCRETE_POWDER,
                        Material.ORANGE_CONCRETE_POWDER,
                        Material.PINK_CONCRETE_POWDER,
                        Material.PURPLE_CONCRETE_POWDER,
                        Material.RED_CONCRETE_POWDER,
                        Material.YELLOW_CONCRETE_POWDER
                };

                Material[] concrete = {
                        Material.BRICKS,
                        Material.WHITE_CONCRETE,
                        Material.BLACK_CONCRETE,
                        Material.BLUE_CONCRETE,
                        Material.BROWN_CONCRETE,
                        Material.CYAN_CONCRETE,
                        Material.GRAY_CONCRETE,
                        Material.GREEN_CONCRETE,
                        Material.LIGHT_BLUE_CONCRETE,
                        Material.LIGHT_GRAY_CONCRETE,
                        Material.LIME_CONCRETE,
                        Material.MAGENTA_CONCRETE,
                        Material.ORANGE_CONCRETE,
                        Material.PINK_CONCRETE,
                        Material.PURPLE_CONCRETE,
                        Material.RED_CONCRETE,
                        Material.YELLOW_CONCRETE,
                };

                // Crea una ricetta per ogni tipo di Concrete Powder
                for (Material concretePowder : concretePowders) {
                    MerchantRecipe recipe = new MerchantRecipe(new ItemStack(concretePowder, 64), Integer.MAX_VALUE);
                    recipe.addIngredient(new ItemStack(Material.GOLD_INGOT, 4)); // Prezzo di 4 lingotti d'oro
                    recipes.add(recipe);
                }

                for (Material conc : concrete) {
                    MerchantRecipe recipe = new MerchantRecipe(new ItemStack(conc, 64), Integer.MAX_VALUE);
                    recipe.addIngredient(new ItemStack(Material.GOLD_INGOT, 6)); // Prezzo di 4 lingotti d'oro
                    recipes.add(recipe);
                }

                Merchant merchant = Bukkit.createMerchant("Concrete Villager Master");
                merchant.setRecipes(recipes);
                player.openMerchant(merchant, true);
            } else if (villager.getName().equals(ChatColor.GOLD + "Massimo Bossetti")){
                event.setCancelled(true);

                List<MerchantRecipe> recipes = new ArrayList<>();

                Material[] concretePowders = {
                        Material.STONE,
                        Material.TUFF,
                        Material.DEEPSLATE,
                        Material.AMETHYST_BLOCK,
                        Material.AMETHYST_CLUSTER,
                        Material.BUDDING_AMETHYST,
                        Material.LARGE_AMETHYST_BUD,
                        Material.MEDIUM_AMETHYST_BUD,
                        Material.SMALL_AMETHYST_BUD,
                        Material.GRANITE,
                        Material.DIORITE,
                        Material.ANDESITE,
                        Material.CALCITE,
                        Material.DRIPSTONE_BLOCK,
                };

                // Crea una ricetta per ogni tipo di Concrete Powder
                for (Material concretePowder : concretePowders) {
                    MerchantRecipe recipe = new MerchantRecipe(new ItemStack(concretePowder, 64), Integer.MAX_VALUE);
                    recipe.addIngredient(new ItemStack(Material.GOLD_INGOT, 5));
                    recipes.add(recipe);
                }

                Merchant merchant = Bukkit.createMerchant("Non ho ucciso la bambina");
                merchant.setRecipes(recipes);
                player.openMerchant(merchant, true);

            } else if (villager.getName().equals(ChatColor.GOLD + "Wood Master")) {
                event.setCancelled(true);

                List<MerchantRecipe> recipes = new ArrayList<>();

                Material[] concretePowders = {
                        Material.OAK_LOG,
                        Material.SPRUCE_LOG,
                        Material.BIRCH_LOG,
                        Material.JUNGLE_LOG,
                        Material.ACACIA_LOG,
                        Material.DARK_OAK_LOG,
                        Material.MANGROVE_LOG,
                        Material.CHERRY_LOG,
                };

                Material[] alberelli = {
                        Material.OAK_SAPLING,       // Alberello di Quercia
                        Material.SPRUCE_SAPLING,    // Alberello di Abete
                        Material.BIRCH_SAPLING,     // Alberello di Betulla
                        Material.JUNGLE_SAPLING,    // Alberello di Giungla
                        Material.ACACIA_SAPLING,    // Alberello di Acacia
                        Material.DARK_OAK_SAPLING,  // Alberello di Quercia Scura
                        Material.MANGROVE_PROPAGULE, // Propagulo di Mangrovia
                        Material.CHERRY_SAPLING     // Alberello di Ciliegio
                };


                // Crea una ricetta per ogni tipo di Concrete Powder
                for (Material concretePowder : concretePowders) {
                    MerchantRecipe recipe = new MerchantRecipe(new ItemStack(concretePowder, 32), Integer.MAX_VALUE);
                    recipe.addIngredient(new ItemStack(Material.GOLD_INGOT, 4));
                    recipes.add(recipe);
                }

                for (Material albe : alberelli) {
                    MerchantRecipe recipe = new MerchantRecipe(new ItemStack(albe, 1), Integer.MAX_VALUE);
                    recipe.addIngredient(new ItemStack(Material.GOLD_INGOT, 2));
                    recipes.add(recipe);
                }

                Merchant merchant = Bukkit.createMerchant("Wood Master");
                merchant.setRecipes(recipes);
                player.openMerchant(merchant, true);
            }
        }
    }
}
