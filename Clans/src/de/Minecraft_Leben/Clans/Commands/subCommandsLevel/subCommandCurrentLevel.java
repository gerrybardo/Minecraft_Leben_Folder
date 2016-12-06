package de.Minecraft_Leben.Clans.Commands.subCommandsLevel;

import de.Minecraft_Leben.Clans.Main;
import org.bukkit.entity.Player;

/**
 * Created by Gerrit Harmeling on 05.12.2016.
 */
public class subCommandCurrentLevel {
    public subCommandCurrentLevel(Player p, Main plugin) {
        if(Main.isUserInDatabase(p)){
            if(Main.isPlayerInClan(p)){
                p.sendMessage(plugin.prefix + "Dein Clan-Level: §6" + Main.getClanLevel(p));
            } else {
                p.sendMessage(plugin.prefix + "Du befindest dich in keinem Clan");
            }
        }
    }
}
