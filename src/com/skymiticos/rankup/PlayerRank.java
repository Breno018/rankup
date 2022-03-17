// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.rankup;

import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import com.skymiticos.Main;
import org.bukkit.entity.Player;

public class PlayerRank
{
    private Player jogador;
    private Rank rank;
    private Rank nextrank;
    private double pontos;
    
    public PlayerRank(final Player jogador, final Rank rank, final Rank nexrank, final double pontos) {
        this.jogador = jogador;
        this.rank = rank;
        this.nextrank = nexrank;
        this.pontos = pontos;
    }
    
    public void save() {
        final File file = new File(Main.getPlugin().getDataFolder() + "/datas/" + this.jogador.getUniqueId() + ".yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("rank", (Object)this.rank.getNome());
        config.set("pontos", (Object)this.pontos);
        try {
            config.save(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Player getJogador() {
        return this.jogador;
    }
    
    public void setJogador(final Player jogador) {
        this.jogador = jogador;
    }
    
    public Rank getRank() {
        return this.rank;
    }
    
    public void setRank(final Rank rank) {
        this.rank = rank;
    }
    
    public Rank getNextrank() {
        return this.nextrank;
    }
    
    public void setNextrank(final Rank nextrank) {
        this.nextrank = nextrank;
    }
    
    public double getPontos() {
        return this.pontos;
    }
    
    public void setPontos(final double pontos) {
        this.pontos = pontos;
    }
}
