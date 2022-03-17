// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.javautils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.Potion;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import java.util.Random;
import java.util.ArrayList;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.text.DecimalFormat;
import java.util.Arrays;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class JavaUtils
{
    public static String progressobar(final Double value, final Double max) {
        final String icon = "\u275a";
        String var = "§a";
        if (value >= max) {
            return "§aEvolua seu rank";
        }
        final Double calc = value / max * 10.0;
        if (calc < 1.0) {
            String temp = "§7";
            for (int i = 1; i <= 10; ++i) {
                temp += icon;
            }
            return temp;
        }
        for (int j = 1; j <= 10; ++j) {
            if (j == calc.intValue()) {
                var = var + icon + "§7";
            }
            else {
                var += icon;
            }
        }
        return var;
    }
    
    public static String progressobarra(final Double value, final Double max) {
        final String icon = "\u275a";
        String var = "§a";
        if (value >= max) {
            return "";
        }
        final Double calc = value / max * 10.0;
        if (calc < 1.0) {
            String temp = "§7";
            for (int i = 1; i <= 10; ++i) {
                temp += icon;
            }
            return temp;
        }
        for (int j = 1; j <= 10; ++j) {
            if (j == calc.intValue()) {
                var = var + icon + "§7";
            }
            else {
                var += icon;
            }
        }
        return var;
    }
    
    public static ItemStack add(final Material mat, final int ammount, final int data) {
        final ItemStack item = new ItemStack(mat, ammount, (short)data);
        return item;
    }
    
    public static String convert(double value) {
        final List<String> suffixes = Arrays.asList("", "K", "M", "B", "T", "Q", "L");
        int index = 0;
        if (value >= 1000000.0) {
            double tmp;
            while ((tmp = value / 1000.0) >= 1.0) {
                value = tmp;
                ++index;
            }
        }
        final DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        return decimalFormat.format(value) + suffixes.get(index);
    }
    
    public static ItemStack add(final Material mat, final int ammount, final int data, final String name) {
        final ItemStack item = add(mat, ammount, data);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack add(final Material mat, final int ammount, final int data, final String name, final List<String> lore) {
        final ItemStack item = add(mat, ammount, data);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack skull(final String url, final String name, final List<String> lore) {
        final ItemStack item = SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/" + url);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
    
    public static void sendMessage(final Player p, final String message) {
        p.sendMessage(message.replace("&", "§"));
    }
    
    public static void sendMessageAll(final String message) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(message.replace("&", "§"));
        }
    }
    
    public static boolean hasPermission(final Player p, final String[] permission) {
        if (p.isOp()) {
            return true;
        }
        for (final String s : permission) {
            if (p.hasPermission(s)) {
                return true;
            }
        }
        return false;
    }
    
    public static void clearPlayer(final Player p) {
        p.setLevel(0);
        p.setTotalExperience(0);
        p.getInventory().clear();
        p.getInventory().setHelmet(new ItemStack((Material)null, 1));
        p.getInventory().setChestplate(new ItemStack((Material)null, 1));
        p.getInventory().setLeggings(new ItemStack((Material)null, 1));
        p.getInventory().setBoots(new ItemStack((Material)null, 1));
        for (final PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
    }
    
    public static String convertTime(final int tempo) {
        final int mins = tempo / 60;
        final int remainderSecs = tempo - mins * 60;
        if (mins < 60) {
            return ((mins < 10) ? "0" : "") + mins + ":" + ((remainderSecs < 10) ? "0" : "") + remainderSecs + "";
        }
        final int hours = mins / 60;
        final int remainderMins = mins - hours * 60;
        return ((hours < 10) ? "0" : "") + hours + ":" + ((remainderMins < 10) ? "0" : "") + remainderMins + ":" + ((remainderSecs < 10) ? "0" : "") + remainderSecs + "";
    }
    
    public static String convertString(final String l) {
        return l.replace("&", "§");
    }
    
    public static void randomItemSlot(final Inventory inv, final boolean similar, final List<ItemStack> itemList) {
        final List<Integer> slots = new ArrayList<Integer>();
        final List<ItemStack> nList = new ArrayList<ItemStack>();
        for (final ItemStack item : itemList) {
            nList.add(item);
        }
        inv.clear();
        for (int i = 0; i < inv.getSize() && !nList.isEmpty(); ++i) {
            final int item2 = new Random().nextInt(nList.size());
            final int slot = new Random().nextInt(inv.getSize());
            if (similar && !slots.contains(slot)) {
                inv.setItem(slot, (ItemStack)nList.get(item2));
                nList.remove(item2);
                slots.add(slot);
            }
            else if (!slots.contains(slot) && !isItemSimilar(nList.get(item2), inv)) {
                inv.setItem(slot, (ItemStack)nList.get(item2));
                nList.remove(item2);
                slots.add(slot);
            }
        }
    }
    
    public static void randomItemSlotByChance(final Inventory inv, final boolean similar, final int max, final int min, final List<ItemStack> itemList) {
        final List<Integer> slots = new ArrayList<Integer>();
        final List<ItemStack> nList = new ArrayList<ItemStack>();
        int ammountChest = new Random().nextInt(max);
        if (ammountChest < min) {
            ammountChest = min;
        }
        for (final ItemStack item : itemList) {
            nList.add(item);
            if (ammountChest == 0) {
                break;
            }
            --ammountChest;
        }
        inv.clear();
        for (int i = 0; i < inv.getSize() && !nList.isEmpty(); ++i) {
            final int item2 = new Random().nextInt(nList.size());
            final int slot = new Random().nextInt(inv.getSize());
            if (similar) {
                inv.setItem(slot, (ItemStack)nList.get(item2));
                nList.remove(item2);
                slots.add(slot);
            }
            else if (!slots.contains(slot) && !isItemSimilar(nList.get(item2), inv)) {
                inv.setItem(slot, (ItemStack)nList.get(item2));
                nList.remove(item2);
                slots.add(slot);
            }
        }
    }
    
    public static Vector rotateAroundAxisX(final Vector v, double angle) {
        angle = Math.toRadians(angle);
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double y = v.getY() * cos - v.getZ() * sin;
        final double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }
    
    public static Vector rotateAroundAxisY(final Vector v, double angle) {
        angle = -angle;
        angle = Math.toRadians(angle);
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double x = v.getX() * cos + v.getZ() * sin;
        final double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }
    
    public static Vector rotateAroundAxisZ(final Vector v, double angle) {
        angle = Math.toRadians(angle);
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double x = v.getX() * cos - v.getY() * sin;
        final double y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }
    
    Location getRandomLocation(final Location origin, final double radius, final boolean _3D) {
        final Random r = new Random();
        final double randomRadius = r.nextDouble() * radius;
        final double theta = Math.toRadians(r.nextDouble() * 360.0);
        final double phi = Math.toRadians(r.nextDouble() * 180.0 - 90.0);
        final double x = randomRadius * Math.cos(theta) * Math.sin(phi);
        final double y = randomRadius * Math.sin(theta) * Math.cos(phi);
        final double z = randomRadius * Math.cos(phi);
        final Location newLoc = origin.add(x, origin.getY(), z);
        if (_3D) {
            newLoc.add(0.0, y, 0.0);
        }
        return newLoc;
    }
    
    private static boolean isItemSimilar(final ItemStack item, final Inventory inv) {
        for (final ItemStack i : inv.getContents()) {
            if (i != null && i.getType().equals((Object)item.getType())) {
                return true;
            }
        }
        return false;
    }
    
    public static ItemStack getItemChance(final List<ItemStack> list) {
        final int chance = new Random().nextInt(100);
        if (chance < 10) {
            return list.get(2);
        }
        if (chance < 50) {
            return list.get(1);
        }
        if (chance < 100) {
            return list.get(0);
        }
        return null;
    }
    
    public void equipArmor(final Player p) {
        ItemStack helmet = new ItemStack(Material.AIR);
        ItemStack chestplate = new ItemStack(Material.AIR);
        ItemStack leggings = new ItemStack(Material.AIR);
        ItemStack boots = new ItemStack(Material.AIR);
        for (final ItemStack item : p.getInventory().getContents()) {
            if (item != null) {
                if (item.getType().equals((Object)Material.IRON_HELMET) || item.getType().equals((Object)Material.DIAMOND_HELMET) || item.getType().equals((Object)Material.GOLD_HELMET) || item.getType().equals((Object)Material.CHAINMAIL_HELMET)) {
                    helmet = item;
                    p.getInventory().remove(item);
                }
                if (item.getType().equals((Object)Material.IRON_CHESTPLATE) || item.getType().equals((Object)Material.DIAMOND_CHESTPLATE) || item.getType().equals((Object)Material.GOLD_CHESTPLATE) || item.getType().equals((Object)Material.CHAINMAIL_CHESTPLATE)) {
                    chestplate = item;
                    p.getInventory().remove(item);
                }
                if (item.getType().equals((Object)Material.IRON_LEGGINGS) || item.getType().equals((Object)Material.DIAMOND_LEGGINGS) || item.getType().equals((Object)Material.GOLD_LEGGINGS) || item.getType().equals((Object)Material.CHAINMAIL_LEGGINGS)) {
                    leggings = item;
                    p.getInventory().remove(item);
                }
                if (item.getType().equals((Object)Material.IRON_BOOTS) || item.getType().equals((Object)Material.DIAMOND_BOOTS) || item.getType().equals((Object)Material.GOLD_BOOTS) || item.getType().equals((Object)Material.CHAINMAIL_BOOTS)) {
                    boots = item;
                    p.getInventory().remove(item);
                }
            }
        }
        p.getInventory().setBoots(boots);
        p.getInventory().setLeggings(leggings);
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setHelmet(helmet);
    }
    
    public static List<ItemStack> loadItemEnchanted(final List<String> list) {
        final List<ItemStack> items = new ArrayList<ItemStack>();
        for (final String item : list) {
            if (Integer.parseInt(item.split(":")[0]) == 373) {
                final int id = Integer.parseInt(item.split(":")[0]);
                final int ammount = Integer.parseInt(item.split(":")[1]);
                final String potion = item.split(":")[2];
                final ItemStack item2 = new ItemStack(Material.getMaterial(id), ammount);
                final PotionMeta meta = (PotionMeta)item2.getItemMeta();
                final int duration = Integer.parseInt(item.split(":")[3]);
                final int amplifier = Integer.parseInt(item.split(":")[4]);
                final Potion pot = new Potion(1);
                pot.setType(PotionType.getByEffect(PotionEffectType.getByName(potion)));
                pot.setSplash(true);
                pot.apply(item2);
                meta.setDisplayName(item.split(":")[5].replace("&", "§"));
                meta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(potion), duration * 20, amplifier), true);
                item2.setItemMeta((ItemMeta)meta);
                items.add(item2);
            }
            else {
                final int iid = Integer.parseInt(item.split(":")[0]);
                final int idata = Integer.parseInt(item.split(":")[1]);
                final int iquant = Integer.parseInt(item.split(":")[2]);
                final ItemStack item2 = new ItemStack(Material.getMaterial(iid), iquant, (short)idata);
                final ItemMeta mitem = item2.getItemMeta();
                if (item.length() > 2) {
                    for (int i = 3; i < item.split(":").length; ++i, ++i) {
                        final int lvl = Integer.parseInt(item.split(":")[i + 1]);
                        mitem.addEnchant(getEnchant(item.split(":")[i]), lvl, true);
                    }
                }
                item2.setItemMeta(mitem);
                items.add(item2);
            }
        }
        return items;
    }
    
    private static Enchantment getEnchant(final Object object) {
        final String str = object.toString().replace("_", "").trim();
        for (final Enchantment enchant : Enchantment.values()) {
            if (str.equals("" + enchant.getId())) {
                return enchant;
            }
            if (str.equalsIgnoreCase(enchant.getName().replace("_", ""))) {
                return enchant;
            }
        }
        if (str.equalsIgnoreCase("PROTECTION")) {
            return Enchantment.PROTECTION_ENVIRONMENTAL;
        }
        if (str.equalsIgnoreCase("UNBREAKING")) {
            return Enchantment.DURABILITY;
        }
        if (str.equalsIgnoreCase("FIREPROTECTION")) {
            return Enchantment.PROTECTION_FIRE;
        }
        if (str.equalsIgnoreCase("FEATHERFALLING")) {
            return Enchantment.PROTECTION_FALL;
        }
        if (str.equalsIgnoreCase("BLASTPROTECTION")) {
            return Enchantment.PROTECTION_EXPLOSIONS;
        }
        if (str.equalsIgnoreCase("SHARPNESS")) {
            return Enchantment.DAMAGE_ALL;
        }
        if (str.equalsIgnoreCase("KNOCKBACK")) {
            return Enchantment.KNOCKBACK;
        }
        if (str.equalsIgnoreCase("FIREASPECT")) {
            return Enchantment.FIRE_ASPECT;
        }
        if (str.equalsIgnoreCase("LOOTING")) {
            return Enchantment.LOOT_BONUS_MOBS;
        }
        if (str.equalsIgnoreCase("FORTUNE")) {
            return Enchantment.LOOT_BONUS_BLOCKS;
        }
        if (str.equalsIgnoreCase("SILKTOUCH")) {
            return Enchantment.SILK_TOUCH;
        }
        if (str.equalsIgnoreCase("EFFICIENCY")) {
            return Enchantment.DIG_SPEED;
        }
        return null;
    }
    
    public static void removeItem(final Player p, final ItemStack item) {
        int slot = 0;
        for (final ItemStack slotItem : p.getInventory().getContents()) {
            if (slotItem != null) {
                final ItemStack clone = new ItemStack(item.getType(), slotItem.getAmount(), item.getDurability());
                clone.setItemMeta(item.getItemMeta());
                if (slotItem.equals((Object)clone) && clone.getAmount() > 1) {
                    clone.setAmount(clone.getAmount() - 1);
                    p.getInventory().setItem(slot, clone);
                    return;
                }
            }
            ++slot;
        }
    }
}
