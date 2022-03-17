// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.eventos;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.EventHandler;
import com.skymiticos.rankeventos.gladiador.Gladiador;
import com.skymiticos.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.Listener;

public class Combate implements Listener
{
    @EventHandler
    public void Event(final EntityDamageEvent e) {
        if (e instanceof Player) {
            final Player p = (Player)e.getEntity();
            if (Main.getPlugin().eventosManegers.getGladiador() != null) {
                final Gladiador gladiador = Main.getPlugin().eventosManegers.getGladiador();
                if (gladiador.getJogadores().contains(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void Event(final PlayerDeathEvent e) {
        final Player p = e.getEntity();
        if (Main.getPlugin().eventosManegers.getGladiador() != null) {
            final Gladiador gladiador = Main.getPlugin().eventosManegers.getGladiador();
            if (gladiador.getJogadores().contains(p)) {
                gladiador.getJogadores().remove(p);
                p.spigot().respawn();
                p.teleport(gladiador.getLobby());
            }
        }
    }
}
