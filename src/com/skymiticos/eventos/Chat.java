// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import com.skymiticos.Main;
import com.skymiticos.rankup.PlayerRank;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.Listener;

public class Chat implements Listener
{
    @EventHandler
    public void event(final AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();
        final PlayerRank rank = Main.getPlugin().rankManeger.getPlayerrank().get(p);
        e.setFormat(Main.getPlugin().rankGrupo.getgrupo(p).getTag() + "§r" + rank.getRank().getDisplay() + " §r" + p.getName() + "§f: " + e.getMessage());
    }
}
