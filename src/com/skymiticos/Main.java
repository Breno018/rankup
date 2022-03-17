// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos;

import com.skymiticos.rankup.PlayerRank;
import java.util.Iterator;
import com.skymiticos.javautils.TagAPI;
import org.bukkit.entity.Player;
import com.skymiticos.rankeventos.gladiador.Gladiador;
import org.bukkit.Bukkit;
import org.bukkit.World;
import com.skymiticos.comandos.Pontos;
import com.skymiticos.comandos.LoteriaComando;
import org.bukkit.command.CommandExecutor;
import com.skymiticos.comandos.RankUP;
import com.skymiticos.eventos.Combate;
import com.skymiticos.eventos.Interagir;
import com.skymiticos.eventos.BlocoEvento;
import com.skymiticos.eventos.AoEntrar;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import com.skymiticos.eventos.Chat;
import com.skymiticos.rankup.RankGrupo;
import com.skymiticos.javautils.VaultAPI;
import com.skymiticos.rankeventos.EventosManegers;
import com.skymiticos.rankup.RankBoard;
import com.skymiticos.rankup.RankManeger;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    private static Main plugin;
    public RankManeger rankManeger;
    public RankBoard rankBoard;
    public EventosManegers eventosManegers;
    public VaultAPI vaultAPI;
    public RankGrupo rankGrupo;
    
    public void onEnable() {
        Main.plugin = this;
        this.rankManeger = new RankManeger();
        this.rankBoard = new RankBoard();
        this.eventosManegers = new EventosManegers();
        this.vaultAPI = new VaultAPI();
        this.rankGrupo = new RankGrupo();
        this.getServer().getPluginManager().registerEvents((Listener)new Chat(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new AoEntrar(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BlocoEvento(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new Interagir(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new Combate(), (Plugin)this);
        this.getCommand("rankup").setExecutor((CommandExecutor)new RankUP());
        this.getCommand("loteria").setExecutor((CommandExecutor)new LoteriaComando());
        this.getCommand("pontos").setExecutor((CommandExecutor)new Pontos());
        final Gladiador gladiador = new Gladiador(100.0, Bukkit.getWorlds().get(0).getSpawnLocation(), Bukkit.getWorlds().get(0).getSpawnLocation());
        gladiador.iniciar();
        this.eventosManegers.setGladiador(gladiador);
        for (final Player p : getPlugin().getServer().getOnlinePlayers()) {
            this.rankManeger.setPlayerrank(p);
            gladiador.getJogadores().add(p);
            this.rankGrupo.setGrupo(p);
            this.rankBoard.setscore(p);
            TagAPI.sendNameTag(p, this.rankGrupo.getgrupo(p).getTag(), "", this.rankGrupo.getgrupo(p).getOrdem());
        }
        Bukkit.getConsoleSender().sendMessage("§aYAY sRank ativo");
        Bukkit.getConsoleSender().sendMessage("§aDireitos reservado ©SkyMiticos");
        System.gc();
    }
    
    public void onDisable() {
        if (!this.rankManeger.getPlayerrank().isEmpty()) {
            this.rankManeger.getPlayerrank().values().forEach(PlayerRank::save);
        }
    }
    
    public static Main getPlugin() {
        return Main.plugin;
    }
}
