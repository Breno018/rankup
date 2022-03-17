// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.comandos;

import com.skymiticos.javautils.JavaUtils;
import org.bukkit.Bukkit;
import com.skymiticos.Main;
import com.skymiticos.rankup.PlayerRank;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class Pontos implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String s, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            final PlayerRank rank = Main.getPlugin().rankManeger.getPlayerrank().get(p);
            if (args.length > 0) {
                final String subcomando = args[0];
                if (subcomando.equalsIgnoreCase("dar")) {
                    if (p.hasPermission("sky.admin")) {
                        if (args.length == 1) {
                            p.sendMessage("§c/ponto dar <Jogador> <Quantia>");
                            return true;
                        }
                        if (args.length == 2) {
                            final String nome = args[1];
                            if (Bukkit.getPlayer(nome) == null) {
                                p.sendMessage("§cJogador online ou invalido.");
                                return true;
                            }
                            p.sendMessage("§c/pontos dar " + nome + " §c<Quantia>");
                        }
                        if (args.length == 3) {
                            final String nome = args[1];
                            if (Bukkit.getPlayer(nome) == null) {
                                p.sendMessage("§cJogador online ou invalido.");
                                return true;
                            }
                            final Player player = Bukkit.getPlayer(nome);
                            final PlayerRank rank2 = Main.getPlugin().rankManeger.getPlayerrank().get(player);
                            try {
                                final double value = Double.parseDouble(args[2]);
                                rank2.setPontos(rank.getPontos() + value);
                                p.sendMessage("§aVoc\u00ea enviou §f" + JavaUtils.convert(value) + " §apara o jogador §f" + nome);
                            }
                            catch (NumberFormatException e) {
                                p.sendMessage("§cNumero invalido");
                            }
                        }
                    }
                    else {
                        p.sendMessage("§cVoc\u00ea precisa do grupo Gerente ou superior para executar este comando.");
                    }
                }
                if (subcomando.equalsIgnoreCase("remover")) {
                    if (p.hasPermission("sky.admin")) {
                        if (args.length == 1) {
                            p.sendMessage("§c/ponto remover <Jogador> <Quantia>");
                            return true;
                        }
                        if (args.length == 2) {
                            final String nome = args[1];
                            if (Bukkit.getPlayer(nome) == null) {
                                p.sendMessage("§cJogador online ou invalido.");
                                return true;
                            }
                            p.sendMessage("§c/pontos remover " + nome + " §c<Quantia>");
                        }
                        if (args.length == 3) {
                            final String nome = args[1];
                            if (Bukkit.getPlayer(nome) == null) {
                                p.sendMessage("§cJogador online ou invalido.");
                                return true;
                            }
                            final Player player = Bukkit.getPlayer(nome);
                            final PlayerRank rank2 = Main.getPlugin().rankManeger.getPlayerrank().get(player);
                            try {
                                final double value = Double.parseDouble(args[2]);
                                rank2.setPontos(rank.getPontos() - value);
                                p.sendMessage("§aVoc\u00ea removel §f" + JavaUtils.convert(value) + " §apara o jogador §f" + nome);
                            }
                            catch (NumberFormatException e) {
                                p.sendMessage("§cNumero invalido");
                            }
                        }
                    }
                    else {
                        p.sendMessage("§cVoc\u00ea precisa do grupo Gerente ou superior para executar este comando.");
                    }
                }
            }
            else if (p.hasPermission("sky.admin")) {
                p.sendMessage("§e/pontos dar <Jogador> <Quantia>");
                p.sendMessage("§e/pontos remover <Jogador> <Quantia>");
            }
            else {
                p.sendMessage("§aPontos: " + JavaUtils.convert(rank.getPontos()));
            }
        }
        return false;
    }
}
