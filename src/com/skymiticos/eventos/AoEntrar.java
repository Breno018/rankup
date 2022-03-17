// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.eventos;

import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import com.skymiticos.rankup.Group;
import org.bukkit.entity.Player;
import com.skymiticos.rankup.PlayerRank;
import com.skymiticos.javautils.TagAPI;
import com.skymiticos.Main;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;

public class AoEntrar implements Listener
{
    @EventHandler
    public void aoEntrar(final PlayerJoinEvent e) {
        e.setJoinMessage((String)null);
        final Player p = e.getPlayer();
        Main.getPlugin().rankManeger.setPlayerrank(p);
        Main.getPlugin().rankGrupo.setGrupo(p);
        Main.getPlugin().rankBoard.setscore(p);
        final Group g = Main.getPlugin().rankGrupo.getgrupo(p);
        TagAPI.sendNameTag(p, g.getTag(), "", g.getOrdem());
        e.setJoinMessage((String)null);
        p.setFoodLevel(20);
        if (p.hasPermission("Aula.fly")) {
            p.setAllowFlight(true);
            p.setFlying(true);
            final PlayerRank rank = Main.getPlugin().rankManeger.getPlayerrank().get(p);
            p.sendMessage(Main.getPlugin().rankGrupo.getgrupo(p).getTag() + p.getName() + "§e entrou no servidor.");
            p.sendMessage("§eSeja bem-vindo ao servidor!");
        }
    }
    
    @EventHandler
    public void aoSair(final PlayerQuitEvent e) {
        e.setQuitMessage((String)null);
        final Player p = e.getPlayer();
        Main.getPlugin().rankManeger.getPlayerrank().get(p).save();
        Main.getPlugin().rankManeger.getPlayerrank().remove(p);
        Main.getPlugin().rankBoard.getPlayerscore().remove(p);
        Main.getPlugin().rankGrupo.removegrupo(p);
    }
    
    @EventHandler
    public void aoExpulsar(final PlayerKickEvent e) {
        e.setLeaveMessage((String)null);
        final Player p = e.getPlayer();
        if (Main.getPlugin().eventosManegers.getLoteria() != null) {
            Main.getPlugin().eventosManegers.getLoteria().getContas().remove(p);
        }
        Main.getPlugin().rankManeger.getPlayerrank().get(p).save();
        Main.getPlugin().rankManeger.getPlayerrank().remove(p);
        Main.getPlugin().rankBoard.getPlayerscore().remove(p);
        Main.getPlugin().rankGrupo.removegrupo(p);
    }
    
    @EventHandler
    public void listPing(final ServerListPingEvent event) {
        event.setMotd("§6§lSky§r§eMiticos §7[1.8 - 1.16]\n§fUma experiencia a cada bloco.");
    }
}
