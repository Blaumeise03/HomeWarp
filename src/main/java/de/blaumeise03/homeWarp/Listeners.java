/*
 * Copyright (c) 2019 Blaumeise03
 */

package de.blaumeise03.homeWarp;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            HomeWarp.damage.put((Player) e.getEntity(), System.currentTimeMillis());
            if (HomeWarp.teleport.containsKey(e.getEntity())) {
                HomeWarp.teleport.replace((Player) e.getEntity(), false);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        ItemStack stack = new ItemStack(Material.ELYTRA);

        if (HomeWarp.teleport.containsKey(e.getPlayer())) {
            Location locF = e.getFrom();
            Location locT = e.getTo();
            if (locT != null)
                if ((((int) (locF.getX() * 10)) != ((int) (locT.getX() * 10))) || (((int) (locF.getY() * 10)) != ((int) (locT.getY() * 10))) || (((int) (locF.getZ() * 10)) != ((int) (locT.getZ() * 10)))) {
                    HomeWarp.teleport.replace(e.getPlayer(), false);
                }
        }
    }
}
