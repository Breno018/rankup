// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.eventos;

import org.bukkit.event.EventHandler;
import com.skymiticos.Main;
import com.skymiticos.javautils.SoundsAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

public class Interagir implements Listener
{
    @EventHandler
    public void Event(final InventoryClickEvent e) {
        final Player p = (Player)e.getWhoClicked();
        if (e.getCurrentItem() == null) {
            return;
        }
        if (!e.getCurrentItem().hasItemMeta()) {
            return;
        }
        if (!e.getCurrentItem().getItemMeta().hasDisplayName()) {
            return;
        }
        if (e.getInventory().getTitle().equals("§7Deseja subir de rank?")) {
            final String nome = e.getCurrentItem().getItemMeta().getDisplayName();
            e.setCancelled(true);
            if (nome.equals("§aSim")) {
                p.playSound(p.getLocation(), SoundsAPI.CLICK.bukkitSound(), 1.0f, 1.0f);
                Main.getPlugin().rankManeger.rankup(p);
                p.closeInventory();
            }
            if (nome.equals("§cN\u00e3o")) {
                p.playSound(p.getLocation(), SoundsAPI.CLICK.bukkitSound(), 1.0f, 1.0f);
                p.closeInventory();
            }
        }
    }
}
