package com.miraclem4n.pvpmoney;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PvPMoney extends JavaPlugin {
    PluginManager pm;
    PluginDescriptionFile pdfFile;

    // Vault
    Boolean vaultB = false;
    Permission perm = null;
    Economy econ = null;

    Boolean takeMoney = false;

    Double moneyAmount = 5.0;
    Double scoreAmount = 5.0;

    String takenMessage = "[PvP Money] You lost %money%!";
    String givenMessage = "[PvP Money] You earned %money%!";
    String scoreMessage = "[PvP Money] Your score is now %score%!";

    public void onDisable() {
        System.out.println("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now disabled!");
    }

    public void onEnable() {
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        new ConfigFile(this).load();

        if (!setupVault()) {
            System.out.println("[" + pdfFile.getName() + "] Cannot find Vault plugin! Please install!");
            pm.disablePlugin(this);
            return;
        }

        pm.registerEvents(new PvPEntityListener(this), this);

        System.out.println("[" + pdfFile.getName() + "] v" + pdfFile.getVersion() + " is now enabled!");
    }

    Boolean setupVault() {
        Plugin plugin = pm.getPlugin("Vault");

        if (plugin == null)
            return false;

        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);

        if (permissionProvider != null)
            perm = permissionProvider.getProvider();
        else
            log("[" + pdfFile.getName() + "] Vault Perms not found disabling.");

        if (economyProvider != null)
            econ = economyProvider.getProvider();
        else
            log("[" + pdfFile.getName() + "] Vault Econ not found disabling.");

        vaultB = econ != null
                && perm != null;

        if (vaultB)
            log("[" + pdfFile.getName() + "] " + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " found hooking in.");

        return vaultB;
    }
    
    public void log(Object object) {
        System.out.println(object);
    }
}