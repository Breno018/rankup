// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.comandos;

import org.bukkit.inventory.Inventory;
import java.util.ArrayList;
import java.util.List;
import com.skymiticos.javautils.JavaUtils;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import com.skymiticos.Main;
import com.skymiticos.rankup.PlayerRank;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class RankUP implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String s, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            final PlayerRank rank = Main.getPlugin().rankManeger.getPlayerrank().get(p);
            final Inventory inv = Bukkit.createInventory((InventoryHolder)p, 27, "§7Deseja subir de rank?");
            inv.setItem(11, JavaUtils.skull("22d145c93e5eac48a661c6f27fdaff5922cf433dd627bf23eec378b9956197", "§aSim", null));
            inv.setItem(15, JavaUtils.skull("5fde3bfce2d8cb724de8556e5ec21b7f15f584684ab785214add164be7624b", "§cN\u00e3o", null));
            final List<String> lore = new ArrayList<String>();
            lore.add("§7Rank Atual: " + rank.getRank().getDisplay());
            lore.add("§7Proximo Rank: " + rank.getNextrank().getDisplay());
            lore.add("§fProgresso: " + JavaUtils.progressobar(rank.getPontos(), rank.getNextrank().getPontos()));
            inv.setItem(13, JavaUtils.skull("fb96c585ccd35f073da38d165cb9bb18ff136f1a184eee3f44725354640ebbd4", "§aInforma\u00e7oes", lore));
            p.openInventory(inv);
        }
        return false;
    }
}
