// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.rankup;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Iterator;
import java.text.DateFormat;
import org.bukkit.OfflinePlayer;
import com.skymiticos.javautils.JavaUtils;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import com.skymiticos.Main;
import java.util.ArrayList;
import java.util.List;
import com.skymiticos.javautils.Board;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class RankBoard
{
    private HashMap<Player, Board> playerscore;
    private List<String> linhas;
    private String titulo;
    
    public RankBoard() {
        this.playerscore = new HashMap<Player, Board>();
        this.linhas = new ArrayList<String>();
        this.carregar();
        this.update();
    }
    
    private void carregar() {
        final File file = new File(Main.getPlugin().getDataFolder(), "scoreboard.yml");
        if (!file.exists()) {
            Main.getPlugin().saveResource("scoreboard.yml", true);
        }
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        this.setLinhas(config.getStringList("Score.linhas"));
        this.setTitulo(config.getString("Score.titulo").replace("&", "§"));
    }
    
    public void setscore(final Player p) {
        final Board board = new Board(this.getTitulo());
        final PlayerRank rank = Main.getPlugin().rankManeger.getPlayerrank().get(p);
        final List<String> linhas = new ArrayList<String>();
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final Date date = new Date();
        String proximo = "Proximo Rank :" + rank.getNextrank().getDisplay();
        if (rank.getNextrank().equals(rank.getRank())) {
            proximo = " §cUltimo rank!";
        }
        else if (rank.getPontos() >= rank.getNextrank().getPontos()) {
            proximo = " §7use /upar";
        }
        for (final String s : this.getLinhas()) {
            linhas.add(s.replace("<player>", p.getName()).replace("<rank>", rank.getRank().getDisplay()).replace("<proximo>", proximo).replace("<pontos>", JavaUtils.convert(rank.getPontos())).replace("<gold>", JavaUtils.convert(Main.getPlugin().vaultAPI.getEconomy().getBalance((OfflinePlayer)p))).replace("<data>", dateFormat.format(date)).replace("<grupo>", Main.getPlugin().rankGrupo.getgrupo(p).getTag()).replace("<progresso>", JavaUtils.progressobar(rank.getPontos(), rank.getNextrank().getPontos())).replace("<jogador>", p.getName()));
        }
        board.setLines(linhas);
        board.show(p);
        this.getPlayerscore().put(p, board);
    }
    
    public void update() {
        new BukkitRunnable() {
            public void run() {
                for (final Player p : RankBoard.this.getPlayerscore().keySet()) {
                    final Board board = RankBoard.this.getPlayerscore().get(p);
                    final PlayerRank rank = Main.getPlugin().rankManeger.getPlayerrank().get(p);
                    final List<String> linhas = new ArrayList<String>();
                    final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    final Date date = new Date();
                    String proximo = "Proximo Rank: " + rank.getNextrank().getDisplay();
                    if (rank.getNextrank().equals(rank.getRank())) {
                        proximo = " §cUltimo rank!";
                    }
                    else if (rank.getPontos() >= rank.getNextrank().getPontos()) {
                        proximo = " §7use /upar";
                    }
                    for (final String s : RankBoard.this.getLinhas()) {
                        linhas.add(s.replace("<player>", p.getName()).replace("<rank>", rank.getRank().getDisplay()).replace("<proximo>", proximo).replace("<pontos>", JavaUtils.convert(rank.getPontos())).replace("<gold>", JavaUtils.convert(Main.getPlugin().vaultAPI.getEconomy().getBalance((OfflinePlayer)p))).replace("<data>", dateFormat.format(date)).replace("<grupo>", Main.getPlugin().rankGrupo.getgrupo(p).getTag()).replace("<progresso>", JavaUtils.progressobar(rank.getPontos(), rank.getNextrank().getPontos())).replace("<jogador>", p.getName()));
                    }
                    board.setLines(linhas);
                }
            }
        }.runTaskTimer((Plugin)Main.getPlugin(), 20L, 20L);
    }
    
    public HashMap<Player, Board> getPlayerscore() {
        return this.playerscore;
    }
    
    public void setPlayerscore(final HashMap<Player, Board> playerscore) {
        this.playerscore = playerscore;
    }
    
    public List<String> getLinhas() {
        return this.linhas;
    }
    
    public void setLinhas(final List<String> linhas) {
        this.linhas = linhas;
    }
    
    public String getTitulo() {
        return this.titulo;
    }
    
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }
}
