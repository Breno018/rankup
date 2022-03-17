// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.rankeventos;

import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import java.util.Random;
import com.skymiticos.javautils.JavaUtils;
import org.bukkit.OfflinePlayer;
import com.skymiticos.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class Loteria
{
    private HashMap<Player, Double> contas;
    private double valor;
    private double minimo;
    private String alerta;
    private boolean cancelar;
    
    public Loteria(final double minimo, final String alerta) {
        this.contas = new HashMap<Player, Double>();
        this.valor = 1000000.0;
        this.alerta = alerta;
        this.minimo = minimo;
        this.setCancelar(false);
    }
    
    public void tempo() {
        new BukkitRunnable() {
            int tempo = 120;
            
            public void run() {
                if (Loteria.this.isCancelar()) {
                    this.cancel();
                    for (final Player p : Loteria.this.contas.keySet()) {
                        Main.getPlugin().vaultAPI.getEconomy().depositPlayer((OfflinePlayer)p, (double)Loteria.this.contas.get(p));
                    }
                    JavaUtils.sendMessageAll("\n &cEvento Loteria cancelado \n&cMotivo: &7*-*.\n ");
                }
                if (this.tempo == 0) {
                    if (Loteria.this.contas.isEmpty()) {
                        this.cancel();
                        Main.getPlugin().eventosManegers.setLoteria(null);
                        JavaUtils.sendMessageAll("\n &cEvento Loteria cancelado \n&cMotivo: &7falta de jogadores.\n ");
                    }
                    else {
                        this.cancel();
                        final int random = new Random().nextInt(Loteria.this.contas.size());
                        int i = 0;
                        for (final double d : Loteria.this.contas.values()) {
                            Loteria.this.setValor(Loteria.this.getValor() + d);
                        }
                        for (final Player p2 : Loteria.this.contas.keySet()) {
                            if (i == random) {
                                JavaUtils.sendMessageAll(" \n&aEvento Loteria \n&aGanhador: &f" + p2.getName() + "\n ");
                                Main.getPlugin().vaultAPI.getEconomy().depositPlayer((OfflinePlayer)p2, Loteria.this.getValor());
                                this.cancel();
                                Main.getPlugin().eventosManegers.setLoteria(null);
                                break;
                            }
                            ++i;
                        }
                    }
                }
                if (this.tempo == 120 || this.tempo == 60 || this.tempo == 30 || this.tempo == 15) {
                    JavaUtils.sendMessageAll(Loteria.this.getAlerta().replace("<total>", Loteria.this.contas.size() + ""));
                }
                --this.tempo;
            }
        }.runTaskTimer((Plugin)Main.getPlugin(), 20L, 20L);
    }
    
    public HashMap<Player, Double> getContas() {
        return this.contas;
    }
    
    public void setContas(final HashMap<Player, Double> contas) {
        this.contas = contas;
    }
    
    public double getValor() {
        return this.valor;
    }
    
    public void setValor(final double valor) {
        this.valor = valor;
    }
    
    public String getAlerta() {
        return this.alerta;
    }
    
    public void setAlerta(final String alerta) {
        this.alerta = alerta;
    }
    
    public double getMinimo() {
        return this.minimo;
    }
    
    public void setMinimo(final double minimo) {
        this.minimo = minimo;
    }
    
    public boolean isCancelar() {
        return this.cancelar;
    }
    
    public void setCancelar(final boolean cancelar) {
        this.cancelar = cancelar;
    }
}
