/*
 *     Copyright (C) 2019  Blaumeise03 - bluegame61@gmail.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
