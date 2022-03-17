// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.comandos;

import com.skymiticos.rankeventos.Loteria;
import com.skymiticos.Main;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class LoteriaComando implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String s, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (args.length > 0) {
                final String subcomando = args[0];
                if (subcomando.equalsIgnoreCase("iniciar")) {
                    if (p.hasPermission("admin.iniciar")) {
                        if (args.length == 1) {
                            p.sendMessage("§a/loteria iniciar <minimo>");
                            return true;
                        }
                        if (args.length == 2) {
                            final String valor = args[1];
                            try {
                                final int d = Integer.parseInt(valor);
                                Main.getPlugin().eventosManegers.loteria(d);
                            }
                            catch (NumberFormatException e) {
                                p.sendMessage("§cEvento Loteria Valor digitado invalido");
                            }
                        }
                    }
                    else {
                        p.sendMessage("§cVoce nao possui permissao.");
                    }
                }
                if (subcomando.equalsIgnoreCase("cancelar")) {
                    if (p.hasPermission("admin.loteria")) {
                        if (args.length == 1) {
                            if (Main.getPlugin().eventosManegers.getLoteria() == null) {
                                p.sendMessage("§cEvento n\u00e3o existe");
                                return true;
                            }
                            Main.getPlugin().eventosManegers.getLoteria().setCancelar(true);
                            Main.getPlugin().eventosManegers.setLoteria(null);
                            p.sendMessage("§aEvento cancelado com sucesso");
                            return true;
                        }
                    }
                    else {
                        p.sendMessage("§cVoce nao possui permissao.");
                    }
                }
                if (subcomando.equalsIgnoreCase("participar")) {
                    if (args.length == 1) {
                        p.sendMessage("§c/loteria participar <minimo>");
                        return true;
                    }
                    if (args.length == 2) {
                        final String valor = args[1];
                        try {
                            final int d = Integer.parseInt(valor);
                            Main.getPlugin().eventosManegers.loteriaEntrar(p, d);
                        }
                        catch (NumberFormatException e) {
                            p.sendMessage("§cEvento Loteria Valor digitado invalido");
                        }
                    }
                }
            }
            else if (p.hasPermission("admin.iniciar")) {
                p.sendMessage("§a/loteria participar.");
                p.sendMessage("§a/loteria iniciar <valor>");
            }
            else {
                p.sendMessage("§a/loteria participar.");
            }
        }
        return false;
    }
}
