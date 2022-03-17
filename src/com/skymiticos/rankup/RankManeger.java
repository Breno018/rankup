// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.rankup;

import com.skymiticos.javautils.RexAPI;
import org.bukkit.OfflinePlayer;
import com.skymiticos.javautils.JavaUtils;
import com.skymiticos.javautils.SoundsAPI;
import java.util.Iterator;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import com.skymiticos.Main;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class RankManeger
{
    private HashMap<Player, PlayerRank> playerrank;
    private HashMap<String, Rank> ranks;
    
    public RankManeger() {
        this.playerrank = new HashMap<Player, PlayerRank>();
        this.ranks = new HashMap<String, Rank>();
        this.carregarranks();
    }
    
    private void carregarranks() {
        final File file = new File(Main.getPlugin().getDataFolder(), "ranks.yml");
        if (!file.exists()) {
            Main.getPlugin().saveResource("ranks.yml", true);
        }
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        int id = 0;
        for (final String key : config.getConfigurationSection("Ranks").getKeys(false)) {
            final String display = config.getString("Ranks." + key + ".Display").replace("&", "§");
            final double pontos = config.getDouble("Ranks." + key + ".Pontos");
            final double preco = config.getDouble("Ranks." + key + ".Preco");
            final Rank rank = new Rank(id, key, display, pontos, preco);
            this.getRanks().put(key, rank);
            ++id;
        }
    }
    
    public void setPlayerrank(final Player p) {
        final File file = new File(Main.getPlugin().getDataFolder() + "/datas/" + p.getUniqueId() + ".yml");
        if (!file.exists()) {
            final Rank rank = this.getRankbyId(0);
            final PlayerRank prank = new PlayerRank(p, rank, this.getRankbyId(rank.getId() + 1), 0.0);
            this.playerrank.put(p, prank);
            return;
        }
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        final String rank2 = config.getString("rank");
        final double pontos = config.getDouble("pontos");
        Rank proximo;
        final Rank atual = proximo = this.getRanks().get(rank2);
        if (this.getRankbyId(atual.getId() + 1) != null) {
            proximo = this.getRankbyId(atual.getId() + 1);
        }
        this.getPlayerrank().put(p, new PlayerRank(p, atual, proximo, pontos));
    }
    
    public void rankup(final Player p) {
        final PlayerRank prank = this.getPlayerrank().get(p);
        if (prank.getNextrank() == prank.getRank()) {
            p.sendMessage("§cVoc\u00ea j\u00e1 atingiu o limite de rank");
            return;
        }
        if (prank.getNextrank().getPontos() > prank.getPontos()) {
            p.playSound(p.getLocation(), SoundsAPI.VILLAGER_NO.bukkitSound(), 3.0f, 1.0f);
            p.sendMessage("§cVoce nao possui pontos suficientes (§b" + JavaUtils.convert(prank.getPontos()) + "/§b" + JavaUtils.convert(prank.getNextrank().getPontos()) + "§c)");
            return;
        }
        if (Main.getPlugin().vaultAPI.getEconomy().getBalance((OfflinePlayer)p) < prank.getNextrank().getPreco()) {
            p.playSound(p.getLocation(), SoundsAPI.ENDERMAN_TELEPORT.bukkitSound(), 1.0f, 1.0f);
            p.sendMessage("§cVoce nao possui saldo suficiente (§a" + JavaUtils.convert(prank.getNextrank().getPreco()) + "§c)");
            return;
        }
        Main.getPlugin().vaultAPI.getEconomy().withdrawPlayer((OfflinePlayer)p, prank.getNextrank().getPreco());
        prank.setPontos(prank.getPontos() - prank.getNextrank().getPontos());
        final PlayerRank newrank = new PlayerRank(p, prank.getNextrank(), null, prank.getPontos());
        Rank proximo = prank.getNextrank();
        if (this.getRankbyId(prank.getNextrank().getId() + 1) != null) {
            proximo = this.getRankbyId(newrank.getRank().getId() + 1);
        }
        newrank.setNextrank(proximo);
        this.getPlayerrank().put(p, newrank);
        p.playSound(p.getLocation(), SoundsAPI.LEVEL_UP.bukkitSound(), 1.0f, 1.0f);
        p.sendMessage("§aVoce evoluiu para o rank " + newrank.getRank().getDisplay());
        for (final Player player : Main.getPlugin().getServer().getOnlinePlayers()) {
            RexAPI.sendActionBar(player, p.getDisplayName() + " §aevoluiu para o rank " + newrank.getRank().getDisplay());
        }
    }
    
    public Rank getRankbyId(final int id) {
        for (final Rank r : this.getRanks().values()) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }
    
    public void sendprogresso(final Player p, final double ponto) {
        final PlayerRank prank = this.playerrank.get(p);
        final double coins = Main.getPlugin().vaultAPI.getEconomy().getBalance((OfflinePlayer)p);
        final double atual = prank.getPontos();
        final double proximo = prank.getNextrank().getPontos();
    }
    
    public HashMap<Player, PlayerRank> getPlayerrank() {
        return this.playerrank;
    }
    
    public void setPlayerrank(final HashMap<Player, PlayerRank> playerrank) {
        this.playerrank = playerrank;
    }
    
    public HashMap<String, Rank> getRanks() {
        return this.ranks;
    }
    
    public void setRanks(final HashMap<String, Rank> ranks) {
        this.ranks = ranks;
    }
}
