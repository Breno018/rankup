// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.rankeventos;

import com.skymiticos.javautils.SoundsAPI;
import org.bukkit.OfflinePlayer;
import com.skymiticos.Main;
import org.bukkit.entity.Player;
import com.skymiticos.javautils.JavaUtils;
import com.skymiticos.rankeventos.gladiador.Gladiador;

public class EventosManegers
{
    private Loteria loteria;
    private Gladiador gladiador;
    
    public void loteria(final double minimo) {
        final String alerta = " \n&aEvento Loteria \n&fParticipantes: <total>\n&fValor: &a100.000\n&fMinimo: &a" + JavaUtils.convert(minimo) + "\n ";
        (this.loteria = new Loteria(minimo, alerta)).tempo();
    }
    
    public void loteriaEntrar(final Player p, final double aposta) {
        if (this.loteria == null) {
            p.sendMessage("§cN\u00e3o existe evento loteria");
            return;
        }
        if (this.loteria.getContas().containsKey(p)) {
            p.sendMessage("§cVoce j\u00e1 fez uma aposta");
            return;
        }
        if (this.loteria.getMinimo() > aposta) {
            p.sendMessage("§cValor minimo da aposta: §f" + JavaUtils.convert(this.loteria.getMinimo()));
            return;
        }
        if (Main.getPlugin().vaultAPI.getEconomy().getBalance((OfflinePlayer)p) < this.loteria.getMinimo()) {
            p.sendMessage("§cVoce nao possui saldo suficiente");
            return;
        }
        this.loteria.getContas().put(p, aposta);
        Main.getPlugin().vaultAPI.getEconomy().withdrawPlayer((OfflinePlayer)p, aposta);
        p.sendMessage("§aAposta realizada com sucesso");
        p.playSound(p.getLocation(), SoundsAPI.LEVEL_UP.bukkitSound(), 1.0f, 1.0f);
    }
    
    public Loteria getLoteria() {
        return this.loteria;
    }
    
    public void setLoteria(final Loteria loteria) {
        this.loteria = loteria;
    }
    
    public Gladiador getGladiador() {
        return this.gladiador;
    }
    
    public void setGladiador(final Gladiador gladiador) {
        this.gladiador = gladiador;
    }
}
