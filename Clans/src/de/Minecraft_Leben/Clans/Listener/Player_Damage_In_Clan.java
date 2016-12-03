package de.Minecraft_Leben.Clans.Listener;

import de.Minecraft_Leben.Clans.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Gerrit Harmeling on 30.11.2016.
 */
public class Player_Damage_In_Clan implements Listener {
    Main plugin;
    public Player_Damage_In_Clan(Main main) {
        this.plugin = main;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void playerInClanDamage(EntityDamageByEntityEvent e){
        if(e.getEntityType().equals(EntityType.PLAYER) && e.getDamager().getType().equals(EntityType.PLAYER)){
            if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
                Player p = (Player) e.getEntity();
                Player target = (Player) e.getDamager();
                if(Main.getClanID(p) == Main.getClanID(target)){
                    e.setCancelled(true);
                    target.sendMessage(plugin.prefix + "Du kannst Teammitglieder nicht schlagen!");
                }
            }
        }
    }
}
