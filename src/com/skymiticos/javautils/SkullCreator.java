// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.javautils;

import java.lang.reflect.InvocationTargetException;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.GameProfile;
import java.util.Base64;
import java.net.URISyntaxException;
import java.net.URI;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class SkullCreator
{
    private static boolean warningPosted;
    private static Field blockProfileField;
    private static Method metaSetProfileMethod;
    private static Field metaProfileField;
    
    private SkullCreator() {
    }
    
    public static ItemStack createSkull() {
        checkLegacy();
        try {
            return new ItemStack(Material.valueOf("PLAYER_HEAD"));
        }
        catch (IllegalArgumentException e) {
            return new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short)3);
        }
    }
    
    @Deprecated
    public static ItemStack itemFromName(final String name) {
        return itemWithName(createSkull(), name);
    }
    
    public static ItemStack itemFromUuid(final UUID id) {
        return itemWithUuid(createSkull(), id);
    }
    
    public static ItemStack itemFromUrl(final String url) {
        return itemWithUrl(createSkull(), url);
    }
    
    public static ItemStack itemFromBase64(final String base64) {
        return itemWithBase64(createSkull(), base64);
    }
    
    @Deprecated
    public static ItemStack itemWithName(final ItemStack item, final String name) {
        notNull(item, "item");
        notNull(name, "name");
        final SkullMeta meta = (SkullMeta)item.getItemMeta();
        meta.setOwner(name);
        item.setItemMeta((ItemMeta)meta);
        return item;
    }
    
    public static ItemStack itemWithUuid(final ItemStack item, final UUID id) {
        notNull(item, "item");
        notNull(id, "id");
        final SkullMeta meta = (SkullMeta)item.getItemMeta();
        meta.setOwner(Bukkit.getOfflinePlayer(id).getName());
        item.setItemMeta((ItemMeta)meta);
        return item;
    }
    
    public static ItemStack itemWithUrl(final ItemStack item, final String url) {
        notNull(item, "item");
        notNull(url, "url");
        return itemWithBase64(item, urlToBase64(url));
    }
    
    public static ItemStack itemWithBase64(final ItemStack item, final String base64) {
        notNull(item, "item");
        notNull(base64, "base64");
        if (!(item.getItemMeta() instanceof SkullMeta)) {
            return null;
        }
        final SkullMeta meta = (SkullMeta)item.getItemMeta();
        mutateItemMeta(meta, base64);
        item.setItemMeta((ItemMeta)meta);
        return item;
    }
    
    @Deprecated
    public static void blockWithName(final Block block, final String name) {
        notNull(block, "block");
        notNull(name, "name");
        final Skull state = (Skull)block.getState();
        state.setOwner(Bukkit.getOfflinePlayer(name).getName());
        state.update(false, false);
    }
    
    public static void blockWithUuid(final Block block, final UUID id) {
        notNull(block, "block");
        notNull(id, "id");
        setToSkull(block);
        final Skull state = (Skull)block.getState();
        state.setOwner(Bukkit.getOfflinePlayer(id).getName());
        state.update(false, false);
    }
    
    public static void blockWithUrl(final Block block, final String url) {
        notNull(block, "block");
        notNull(url, "url");
        blockWithBase64(block, urlToBase64(url));
    }
    
    public static void blockWithBase64(final Block block, final String base64) {
        notNull(block, "block");
        notNull(base64, "base64");
        setToSkull(block);
        final Skull state = (Skull)block.getState();
        mutateBlockState(state, base64);
        state.update(false, false);
    }
    
    private static void setToSkull(final Block block) {
        checkLegacy();
        try {
            block.setType(Material.valueOf("PLAYER_HEAD"), false);
        }
        catch (IllegalArgumentException e) {
            block.setType(Material.valueOf("SKULL"), false);
            final Skull state = (Skull)block.getState();
            state.setSkullType(SkullType.PLAYER);
            state.update(false, false);
        }
    }
    
    private static void notNull(final Object o, final String name) {
        if (o == null) {
            throw new NullPointerException(name + " should not be null!");
        }
    }
    
    private static String urlToBase64(final String url) {
        URI actualUrl;
        try {
            actualUrl = new URI(url);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        final String toEncode = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualUrl.toString() + "\"}}}";
        return Base64.getEncoder().encodeToString(toEncode.getBytes());
    }
    
    private static GameProfile makeProfile(final String b64) {
        final UUID id = new UUID(b64.substring(b64.length() - 20).hashCode(), b64.substring(b64.length() - 10).hashCode());
        final GameProfile profile = new GameProfile(id, "Player");
        profile.getProperties().put((Object)"textures", (Object)new Property("textures", b64));
        return profile;
    }
    
    private static void mutateBlockState(final Skull block, final String b64) {
        try {
            if (SkullCreator.blockProfileField == null) {
                (SkullCreator.blockProfileField = block.getClass().getDeclaredField("profile")).setAccessible(true);
            }
            SkullCreator.blockProfileField.set(block, makeProfile(b64));
        }
        catch (NoSuchFieldException | IllegalAccessException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            e.printStackTrace();
        }
    }
    
    private static void mutateItemMeta(final SkullMeta meta, final String b64) {
        try {
            if (SkullCreator.metaSetProfileMethod == null) {
                (SkullCreator.metaSetProfileMethod = meta.getClass().getDeclaredMethod("setProfile", GameProfile.class)).setAccessible(true);
            }
            SkullCreator.metaSetProfileMethod.invoke(meta, makeProfile(b64));
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex5) {
            final ReflectiveOperationException ex3;
            final ReflectiveOperationException ex = ex3;
            try {
                if (SkullCreator.metaProfileField == null) {
                    (SkullCreator.metaProfileField = meta.getClass().getDeclaredField("profile")).setAccessible(true);
                }
                SkullCreator.metaProfileField.set(meta, makeProfile(b64));
            }
            catch (NoSuchFieldException | IllegalAccessException ex6) {
                final ReflectiveOperationException ex4;
                final ReflectiveOperationException ex2 = ex4;
                ex2.printStackTrace();
            }
        }
    }
    
    private static void checkLegacy() {
        try {
            Material.class.getDeclaredField("PLAYER_HEAD");
            Material.valueOf("SKULL");
            if (!SkullCreator.warningPosted) {
                Bukkit.getLogger().warning("SKULLCREATOR API - Using the legacy bukkit API with 1.13+ bukkit versions is not supported!");
                SkullCreator.warningPosted = true;
            }
        }
        catch (NoSuchFieldException ex) {}
        catch (IllegalArgumentException ex2) {}
    }
    
    static {
        SkullCreator.warningPosted = false;
    }
}
