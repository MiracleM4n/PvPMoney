package com.miraclem4n.pvpmoney;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class PvPEntityListener implements Listener {
    PvPMoney plugin;

    public PvPEntityListener(PvPMoney instance) {
        plugin = instance;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent))
            return;

        EntityDamageByEntityEvent deathEvent = (EntityDamageByEntityEvent)event.getEntity().getLastDamageCause();

        if (!(deathEvent.getDamager() instanceof Player) && !(deathEvent.getEntity() instanceof Player))
            return;

        Player killed = (Player) deathEvent.getEntity();
        Player killer = (Player) deathEvent.getDamager();

        if (plugin.takeMoney)
            takeMoney(killed);

        giveMoney(killer);
    }
    
    void giveMoney(Player player) {
        plugin.econ.depositPlayer(player.getName(), plugin.moneyAmount);
        player.sendMessage((ChatColor.AQUA + plugin.givenMessage).replace("%money%", plugin.moneyAmount.toString()));
    }

    void takeMoney(Player player) {
        plugin.econ.withdrawPlayer(player.getName(), plugin.moneyAmount);
        player.sendMessage((ChatColor.AQUA + plugin.takenMessage).replace("%money%", plugin.moneyAmount.toString()));
    }
}