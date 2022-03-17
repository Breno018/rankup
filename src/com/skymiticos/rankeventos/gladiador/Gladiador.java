// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.rankeventos.gladiador;

import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import com.skymiticos.javautils.RexAPI;
import org.bukkit.OfflinePlayer;
import com.skymiticos.Main;
import com.skymiticos.javautils.SoundsAPI;
import com.skymiticos.javautils.JavaUtils;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.List;

public class Gladiador
{
    private List<Player> jogadores;
    private Location lobby;
    private Location spawn;
    private double premio;
    private boolean ativado;
    private GladiadorState estado;
    
    public Gladiador(final double premio, final Location lobby, final Location spawn) {
        this.jogadores = new ArrayList<Player>();
        this.setAtivado(true);
        this.setEstado(GladiadorState.ESPERANDO);
        this.setPremio(this.getPremio());
        this.setLobby(lobby);
        this.setSpawn(spawn);
    }
    
    public void iniciar() {
        new BukkitRunnable() {
            int tempo = 15;
            Player ganhador = null;
            
            public void run() {
                if (Gladiador.this.getEstado() == GladiadorState.ESPERANDO) {
                    if (Gladiador.this.getJogadores().size() >= 2) {
                        --this.tempo;
                    }
                    else {
                        this.tempo = 120;
                    }
                    if (this.tempo < 6 || this.tempo == 30 || this.tempo == 60) {
                        String tempos = this.tempo + "s";
                        if (this.tempo > 60) {
                            tempos = JavaUtils.convertTime(this.tempo);
                        }
                        for (final Player p : Gladiador.this.getJogadores()) {
                            p.sendMessage("§e[Gladiador] §aEvento iniciando em " + tempos);
                            p.playSound(p.getLocation(), SoundsAPI.CLICK.bukkitSound(), 1.0f, 1.0f);
                        }
                    }
                    if (this.tempo <= 0) {
                        Gladiador.this.setEstado(GladiadorState.JOGO);
                        for (final Player p2 : Gladiador.this.getJogadores()) {
                            p2.teleport(Gladiador.this.getSpawn());
                            p2.sendMessage("§e[Gladiador] §aEvento iniciado");
                            p2.playSound(p2.getLocation(), SoundsAPI.ENDERDRAGON_GROWL.bukkitSound(), 1.0f, 1.0f);
                        }
                    }
                }
                if (Gladiador.this.getEstado() == GladiadorState.JOGO) {
                    if (this.tempo <= 0 && Gladiador.this.getJogadores().size() <= 1) {
                        this.tempo = 60;
                        if (!Gladiador.this.getJogadores().isEmpty()) {
                            for (final Player p2 : Gladiador.this.getJogadores()) {
                                this.ganhador = p2;
                                Main.getPlugin().vaultAPI.getEconomy().depositPlayer((OfflinePlayer)p2, Gladiador.this.premio);
                            }
                            for (final Player p2 : Main.getPlugin().getServer().getOnlinePlayers()) {
                                RexAPI.sendActionBar(p2, "§6O Jogador " + this.ganhador.getDisplayName() + " §6ganhou o evento gladiador");
                            }
                        }
                    }
                    if (this.tempo <= 0) {
                        this.cancel();
                        Gladiador.this.setAtivado(false);
                        for (final Player p2 : Gladiador.this.getJogadores()) {
                            p2.teleport(Gladiador.this.getLobby());
                        }
                        Gladiador.this.getJogadores().clear();
                        Gladiador.this.setEstado(GladiadorState.ESPERANDO);
                    }
                }
            }
        }.runTaskTimer((Plugin)Main.getPlugin(), 20L, 20L);
    }
    
    public List<Player> getJogadores() {
        return this.jogadores;
    }
    
    public void setJogadores(final List<Player> jogadores) {
        this.jogadores = jogadores;
    }
    
    public Location getLobby() {
        return this.lobby;
    }
    
    public void setLobby(final Location lobby) {
        this.lobby = lobby;
    }
    
    public Location getSpawn() {
        return this.spawn;
    }
    
    public void setSpawn(final Location spawn) {
        this.spawn = spawn;
    }
    
    public double getPremio() {
        return this.premio;
    }
    
    public void setPremio(final double premio) {
        this.premio = premio;
    }
    
    public boolean isAtivado() {
        return this.ativado;
    }
    
    public void setAtivado(final boolean ativado) {
        this.ativado = ativado;
    }
    
    public GladiadorState getEstado() {
        return this.estado;
    }
    
    public void setEstado(final GladiadorState estado) {
        this.estado = estado;
    }
}
