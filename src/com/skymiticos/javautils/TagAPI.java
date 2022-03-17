// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.javautils;

import java.util.Iterator;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.lang.reflect.Field;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class TagAPI
{
    private static Class<?> getNMSClass(final String name) {
        final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static void sendPacket(final Player player, final Object packet) {
        try {
            final Object handle = player.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(player, new Object[0]);
            final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void setField(final Object packet, final Field field, final Object value) {
        field.setAccessible(true);
        try {
            field.set(packet, value);
        }
        catch (IllegalArgumentException | IllegalAccessException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        field.setAccessible(!field.isAccessible());
    }
    
    private static Field getField(final Class<?> classs, final String fieldname) {
        try {
            return classs.getDeclaredField(fieldname);
        }
        catch (NoSuchFieldException | SecurityException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
            return null;
        }
    }
    
    public static void sendNameTag(final Player player, final String prefix, final String suffix, final String order) {
        if (RexAPI.isAbove1_9()) {
            final String name = UUID.randomUUID().toString().substring(0, 15);
            final Class<?> classe = getNMSClass("PacketPlayOutScoreboardTeam");
            try {
                final Object packet = Objects.requireNonNull(classe).newInstance();
                final Class<?> clas = packet.getClass();
                final Field team_name = getField(clas, "a");
                final Field display_name = getField(clas, "b");
                final Field prefix2 = getField(clas, "c");
                final Field suffix2 = getField(clas, "d");
                final Field members = getField(clas, "g");
                final Field param_int = getField(clas, "h");
                final Field pack_option = getField(clas, "i");
                setField(packet, Objects.requireNonNull(team_name), order + name);
                setField(packet, Objects.requireNonNull(display_name), player.getName());
                setField(packet, Objects.requireNonNull(prefix2), prefix);
                setField(packet, Objects.requireNonNull(suffix2), suffix);
                setField(packet, Objects.requireNonNull(members), Collections.singletonList(player.getName()));
                setField(packet, Objects.requireNonNull(param_int), 0);
                setField(packet, Objects.requireNonNull(pack_option), 1);
                player.setDisplayName(prefix + player.getName() + suffix);
                for (final Player ps : Bukkit.getOnlinePlayers()) {
                    sendPacket(ps, packet);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
