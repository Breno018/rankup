// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.rankup;

import java.util.Iterator;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import com.skymiticos.Main;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class RankGrupo
{
    private HashMap<Player, Group> playergrupo;
    private List<Group> grupos;
    
    public RankGrupo() {
        this.playergrupo = new HashMap<Player, Group>();
        this.grupos = new ArrayList<Group>();
        this.carregartags();
    }
    
    private void carregartags() {
        final File file = new File(Main.getPlugin().getDataFolder(), "config.yml");
        if (!file.exists()) {
            Main.getPlugin().saveResource("config.yml", true);
        }
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (final String key : config.getConfigurationSection("Grupos").getKeys(false)) {
            final String display = config.getString("Grupos." + key + ".Display").replace("&", "§");
            final String permissao = config.getString("Grupos." + key + ".Permissao");
            final String ordem = config.getString("Grupos." + key + ".Ordem");
            final boolean padrao = config.getBoolean("Grupo." + key + ".Padrao");
            this.grupos.add(new Group(display, ordem, permissao, padrao));
        }
    }
    
    public void setGrupo(final Player p) {
        for (final Group group : this.grupos) {
            if (p.hasPermission(group.getPermissao())) {
                this.playergrupo.put(p, group);
                return;
            }
        }
        for (final Group group : this.grupos) {
            if (group.isPadrao()) {
                this.playergrupo.put(p, group);
            }
        }
    }
    
    public Group getgrupo(final Player p) {
        return this.playergrupo.get(p);
    }
    
    public void removegrupo(final Player p) {
        this.playergrupo.remove(p);
    }
}
