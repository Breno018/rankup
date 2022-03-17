// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.javautils;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.Plugin;
import com.skymiticos.Main;
import org.bukkit.Bukkit;
import net.milkbowl.vault.economy.Economy;
import java.util.logging.Logger;

public class VaultAPI
{
    private final Logger log;
    private Economy ecom;
    
    public VaultAPI() {
        this.log = Logger.getLogger("Minecraft");
        this.ecom = null;
        if (!this.setupEconomy()) {
            this.log.severe(String.format("Servidor foi desativado por falta do Vault", new Object[0]));
            Bukkit.getServer().getPluginManager().disablePlugin((Plugin)Main.getPlugin());
            return;
        }
        this.setupEconomy();
    }
    
    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)Bukkit.getServer().getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return false;
        }
        this.ecom = (Economy)rsp.getProvider();
        return this.ecom != null;
    }
    
    public Economy getEconomy() {
        return this.ecom;
    }
}
