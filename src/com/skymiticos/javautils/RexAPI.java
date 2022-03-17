// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.javautils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;

public final class RexAPI
{
    public static String mEntityPlayer;
    public static String cCraftPlayer;
    public static String sPacketTitle;
    public static String sAction;
    public static String sPacketTabHeader;
    public static String pPlayOutChat;
    public static String pPlayOutTitle;
    public static String pPlayOutWorldParticles;
    public static String pPlayOutPlayerListHeaderFooter;
    public static String pPlayOutNamedEntitySpawn;
    public static String pPlayInClientCommand;
    public static String cEnumTitleAction;
    public static String pEnumTitleAction2;
    public static String mEnumClientCommand;
    public static String mEnumClientCommand2;
    public static String mChatSerializer;
    public static String mIChatBaseComponent;
    public static String mEntityHuman;
    public static String mNBTTagCompound;
    public static String mEntityInsentient;
    public static String mNBTBase;
    public static String mNBTTagList;
    public static String pPacket;
    public static String cItemStack;
    public static String mItemStack;
    public static String bItemStack;
    public static String bBukkit;
    public static String mChatComponentText;
    public static String mMinecraftServer;
    
    public static void sendPacket(final Object packet, final Player player) throws Exception {
        RefAPI.getResult(getConnection(player), "sendPacket", RefAPI.getParameters(RexAPI.pPacket), packet);
    }
    
    public static Plugin getPlugin(final String plugin) {
        return Bukkit.getPluginManager().getPlugin(plugin);
    }
    
    public static int getCurrentTick() throws Exception {
        return (int)RefAPI.getValue(RexAPI.mMinecraftServer, "currentTick");
    }
    
    public static Double getTPS() {
        try {
            return Math.min(20.0, Math.round((float)(getCurrentTick() * 10)) / 10.0);
        }
        catch (Exception ex) {
            return 0.0;
        }
    }
    
    public static void sendPacket(final Player player, final Object packet) throws Exception {
        sendPacket(packet, player);
    }
    
    public static void sendPackets(final Object packet, final Player target) {
        for (final Player player : getPlayers()) {
            if (target == player) {
                continue;
            }
            try {
                sendPacket(packet, target);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void sendPackets(final Object packet) {
        for (final Player p : getPlayers()) {
            try {
                sendPacket(packet, p);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static String getIComponentText(final String text) {
        return "{\"text\":\"" + text + "\"}";
    }
    
    public static Object getIChatText2(final String text) throws Exception {
        return RefAPI.getNew((Object)RexAPI.mChatComponentText, new Object[] { text });
    }
    
    public static Object getIChatText(final String text) throws Exception {
        return getIChatBaseComponent(getIComponentText(text));
    }
    
    public static Object getIChatBaseComponent(final String component) throws Exception {
        return RefAPI.getResult((Object)RexAPI.mChatSerializer, "a", new Object[] { component });
    }
    
    public static Object getHandle(final Player player) throws Exception {
        return RefAPI.getResult((Object)player, "getHandle", new Object[0]);
    }
    
    public static Object getConnection(final Player player) throws Exception {
        return RefAPI.getValue(getHandle(player), "playerConnection");
    }
    
    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace('.', ',').split(",")[3];
    }
    
    public static void setTabList(final Player player, final String header, final String footer) {
        try {
            if (isAbove1_8(player)) {
                final Object packet = RefAPI.getNew(RexAPI.sPacketTabHeader, RefAPI.getParameters(RexAPI.mIChatBaseComponent, RexAPI.mIChatBaseComponent), getIChatText(header), getIChatText(footer));
                sendPacket(packet, player);
                return;
            }
        }
        catch (Exception ex2) {}
        try {
            final Object packet = RefAPI.getNew(RexAPI.pPlayOutPlayerListHeaderFooter, RefAPI.getParameters(RexAPI.mIChatBaseComponent), getIChatText(header));
            RefAPI.setValue(packet, "b", getIChatText(footer));
            sendPacket(packet, player);
        }
        catch (Exception ex3) {}
        try {
            final Object packet = RefAPI.getNew(RexAPI.pPlayOutPlayerListHeaderFooter, RefAPI.getParameters(RexAPI.mIChatBaseComponent), getIChatText2(header));
            RefAPI.setValue(packet, "b", getIChatText2(footer));
            sendPacket(packet, player);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean isAbove1_9() {
        return getVersion().equals("v1_8_R3") || getVersion().equals("v1_8_R2") || getVersion().equals("v1_8_R1");
    }
    
    public static boolean isAbove1_8(final Player player) {
        try {
            return (int)RefAPI.getResult(RefAPI.getValue(getConnection(player), "networkManager"), "getVersion", new Object[0]) == 47;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    private static Class<?> getNMSClass(final String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
    }
    
    public static void sendActionBar(final Player player, final String text) {
        try {
            final Object component = getIChatText(text);
            final Object packet = RefAPI.getNew(RexAPI.pPlayOutChat, RefAPI.getParameters(RexAPI.mIChatBaseComponent, Byte.TYPE), component, 2);
            sendPacket(player, packet);
        }
        catch (Exception ex) {
            try {
                final Object component = getIChatText2(text);
                final Object packet = RefAPI.getNew(RexAPI.pPlayOutChat, RefAPI.getParameters(RexAPI.mIChatBaseComponent, Byte.TYPE), component, 2);
                sendPacket(player, packet);
            }
            catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("§bRexAPI §aNao foi possivel usar o 'setActionBar' pois o servidor esta na versao anterior a 1.8");
            }
        }
    }
    
    public static String getPing(final Player player) {
        try {
            return RefAPI.getValue(getHandle(player), "ping").toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
    
    public static List<Player> getPlayers() {
        final List<Player> list = new ArrayList<Player>();
        try {
            final Object object = RefAPI.getResult((Object)RexAPI.bBukkit, "getOnlinePlayers", new Object[0]);
            if (object instanceof Collection) {
                final Collection<?> players = (Collection<?>)object;
                for (final Object obj : players) {
                    if (obj instanceof Player) {
                        final Player p = (Player)obj;
                        list.add(p);
                    }
                }
            }
            else if (object instanceof Player[]) {
                final Player[] array;
                final Player[] players2 = array = (Player[])object;
                for (final Player p2 : array) {
                    list.add(p2);
                }
            }
        }
        catch (Exception ex) {}
        return list;
    }
    
    public static void makeRespawn(final Player player) {
        try {
            final Object packet = RefAPI.getNew((Object)RexAPI.pPlayInClientCommand, new Object[] { RefAPI.getValue(RexAPI.mEnumClientCommand, "PERFORM_RESPAWN") });
            RefAPI.getResult(getConnection(player), "a", new Object[] { packet });
        }
        catch (Exception ex) {
            try {
                final Object packet2 = RefAPI.getNew((Object)RexAPI.pPlayInClientCommand, new Object[] { RefAPI.getValue(RexAPI.mEnumClientCommand2, "PERFORM_RESPAWN") });
                RefAPI.getResult(getConnection(player), "a", new Object[] { packet2 });
            }
            catch (Exception ex2) {}
        }
    }
    
    public static void disableAI(final Entity entity) {
        try {
            final Object compound = RefAPI.getNew((Object)RexAPI.mNBTTagCompound, new Object[0]);
            final Object getHandle = RefAPI.getResult((Object)entity, "getHandle", new Object[0]);
            RefAPI.getResult(getHandle, "c", new Object[] { compound });
            RefAPI.getResult(compound, "setByte", new Object[] { "NoAI", 1 });
            RefAPI.getResult(getHandle, "f", new Object[] { compound });
        }
        catch (Exception ex) {}
    }
    
    public static void silent(final Entity entity) {
        try {
            final Object compound = RefAPI.getNew((Object)RexAPI.mNBTTagCompound, new Object[0]);
            final Object getHandle = RefAPI.getResult((Object)entity, "getHandle", new Object[0]);
            RefAPI.getResult(getHandle, "c", new Object[] { compound });
            RefAPI.getResult(compound, "setInt", new Object[] { "Silent", 1 });
            RefAPI.getResult(compound, "setInt", new Object[] { "Invulnerable", 1 });
            RefAPI.getResult(getHandle, "f", new Object[] { compound });
        }
        catch (Exception ex) {}
    }
    
    public static void navigation(final Entity entity, final Location loc, final float speed) {
        try {
            final Object obj = RefAPI.getResult((Object)entity, "getHandle", new Object[0]);
            final Object nav = obj.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("getNavigation", (Class<?>[])new Class[0]).invoke(obj, new Object[0]);
            nav.getClass().getSuperclass().getDeclaredMethod("a", Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE).invoke(nav, loc.getX(), loc.getY(), loc.getZ(), speed);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        RexAPI.mEntityPlayer = "#mEntityPlayer";
        RexAPI.cCraftPlayer = "#cCraftPlayer";
        RexAPI.sPacketTitle = "#sProtocolInjector$PacketTitle";
        RexAPI.sAction = "#sProtocolInjector$PacketTitle$Action";
        RexAPI.sPacketTabHeader = "#sProtocolInjector$PacketTabHeader";
        RexAPI.pPlayOutChat = "#pPlayOutChat";
        RexAPI.pPlayOutTitle = "#pPlayOutTitle";
        RexAPI.pPlayOutWorldParticles = "#pPlayOutWorldParticles";
        RexAPI.pPlayOutPlayerListHeaderFooter = "#pPlayOutPlayerListHeaderFooter";
        RexAPI.pPlayOutNamedEntitySpawn = "#pPlayOutNamedEntitySpawn";
        RexAPI.pPlayInClientCommand = "#pPlayInClientCommand";
        RexAPI.cEnumTitleAction = "#cEnumTitleAction";
        RexAPI.pEnumTitleAction2 = "#pPlayOutTitle$EnumTitleAction";
        RexAPI.mEnumClientCommand = "#mEnumClientCommand";
        RexAPI.mEnumClientCommand2 = "#pPlayInClientCommand$EnumClientCommand";
        RexAPI.mChatSerializer = "#mChatSerializer";
        RexAPI.mIChatBaseComponent = "#mIChatBaseComponent";
        RexAPI.mEntityHuman = "#mEntityHuman";
        RexAPI.mNBTTagCompound = "#mNBTTagCompound";
        RexAPI.mEntityInsentient = "EntityInsentient";
        RexAPI.mNBTBase = "#mNBTBase";
        RexAPI.mNBTTagList = "#mNBTTagList";
        RexAPI.pPacket = "#p";
        RexAPI.cItemStack = "#cinventory.CraftItemStack";
        RexAPI.mItemStack = "#mItemStack";
        RexAPI.bItemStack = "#bItemStack";
        RexAPI.bBukkit = "#bBukkit";
        RexAPI.mChatComponentText = "#mChatComponentText";
        RexAPI.mMinecraftServer = "#mMinecraftServer";
        RefAPI.newReplacer("#v", getVersion());
    }
}
