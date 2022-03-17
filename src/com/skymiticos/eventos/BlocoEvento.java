// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import com.skymiticos.javautils.RexAPI;
import com.skymiticos.javautils.JavaUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.Material;
import com.skymiticos.Main;
import com.skymiticos.rankup.PlayerRank;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.Listener;

public class BlocoEvento implements Listener
{
    @EventHandler
    public void Event(final BlockBreakEvent e) {
        final Player p = e.getPlayer();
        final PlayerRank rank = Main.getPlugin().rankManeger.getPlayerrank().get(p);
        if (e.getBlock().getType().equals((Object)Material.REDSTONE_ORE)) {
            Main.getPlugin().rankManeger.sendprogresso(p, 0.02);
            rank.setPontos(rank.getPontos() + 0.02);
            Main.getPlugin().vaultAPI.getEconomy().depositPlayer((OfflinePlayer)p, 0.5);
            RexAPI.sendActionBar(p, "§a+0.2 " + JavaUtils.progressobarra(rank.getPontos(), rank.getNextrank().getPontos()) + " §a| Minerio +0.5 coins \u26c1");
        }
        if (e.getBlock().getType().equals((Object)Material.COAL_ORE)) {
            rank.setPontos(rank.getPontos() + 0.01);
            Main.getPlugin().rankManeger.sendprogresso(p, 0.01);
            Main.getPlugin().vaultAPI.getEconomy().depositPlayer((OfflinePlayer)p, 0.5);
            RexAPI.sendActionBar(p, "§a+0.1 " + JavaUtils.progressobarra(rank.getPontos(), rank.getNextrank().getPontos()) + " §a| Minerio +0.5 coins \u26c1");
        }
        if (e.getBlock().getType().equals((Object)Material.IRON_ORE)) {
            rank.setPontos(rank.getPontos() + 0.02);
            Main.getPlugin().rankManeger.sendprogresso(p, 0.02);
            Main.getPlugin().vaultAPI.getEconomy().depositPlayer((OfflinePlayer)p, 0.5);
            RexAPI.sendActionBar(p, "§a+0.2 " + JavaUtils.progressobarra(rank.getPontos(), rank.getNextrank().getPontos()) + " §a| Minerio +0.5 coins \u26c1");
        }
        if (e.getBlock().getType().equals((Object)Material.GOLD_ORE)) {
            rank.setPontos(rank.getPontos() + 0.03);
            Main.getPlugin().rankManeger.sendprogresso(p, 0.03);
            Main.getPlugin().vaultAPI.getEconomy().depositPlayer((OfflinePlayer)p, 0.5);
            RexAPI.sendActionBar(p, "§a+0.3 " + JavaUtils.progressobarra(rank.getPontos(), rank.getNextrank().getPontos()) + " §a| Minerio +0.5 coins \u26c1");
        }
        if (e.getBlock().getType().equals((Object)Material.LAPIS_ORE)) {
            rank.setPontos(rank.getPontos() + 0.03);
            Main.getPlugin().rankManeger.sendprogresso(p, 0.03);
            Main.getPlugin().vaultAPI.getEconomy().depositPlayer((OfflinePlayer)p, 0.8);
            RexAPI.sendActionBar(p, "§a+0.3 " + JavaUtils.progressobarra(rank.getPontos(), rank.getNextrank().getPontos()) + " §a| Minerio +0.8 coins \u26c1");
        }
        if (e.getBlock().getType().equals((Object)Material.DIAMOND_ORE)) {
            rank.setPontos(rank.getPontos() + 0.05);
            Main.getPlugin().rankManeger.sendprogresso(p, 0.05);
            Main.getPlugin().vaultAPI.getEconomy().depositPlayer((OfflinePlayer)p, 0.15);
            RexAPI.sendActionBar(p, "§a+0.5 " + JavaUtils.progressobarra(rank.getPontos(), rank.getNextrank().getPontos()) + " §a| Minerio +0.15 coins \u26c1");
        }
        if (e.getBlock().getType().equals((Object)Material.EMERALD_ORE)) {
            rank.setPontos(rank.getPontos() + 0.07);
            Main.getPlugin().rankManeger.sendprogresso(p, 0.07);
            Main.getPlugin().vaultAPI.getEconomy().depositPlayer((OfflinePlayer)p, 0.3);
            RexAPI.sendActionBar(p, "§a+0.7 " + JavaUtils.progressobarra(rank.getPontos(), rank.getNextrank().getPontos()) + " §a| Minerio +0.30 coins \u26c1");
        }
        if (e.getBlock().getType().equals((Object)Material.REDSTONE_ORE)) {
            rank.setPontos(rank.getPontos() + 0.01);
            Main.getPlugin().rankManeger.sendprogresso(p, 0.01);
            Main.getPlugin().vaultAPI.getEconomy().depositPlayer((OfflinePlayer)p, 0.5);
            RexAPI.sendActionBar(p, "§a+0.1 " + JavaUtils.progressobarra(rank.getPontos(), rank.getNextrank().getPontos()) + " §a| Minerio +0.5 coins \u26c1");
        }
    }
}
