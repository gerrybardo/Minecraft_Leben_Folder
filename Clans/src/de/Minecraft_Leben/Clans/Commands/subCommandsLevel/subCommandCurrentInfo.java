package de.Minecraft_Leben.Clans.Commands.subCommandsLevel;

import de.Minecraft_Leben.Clans.Handler.LevelHandler;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.entity.Player;

/**
 * Created by Gerrit Harmeling on 05.12.2016.
 */
public class subCommandCurrentInfo extends LevelHandler{
    public subCommandCurrentInfo(Player p, Main plugin) {
        super(p);
        if(Main.isUserInDatabase(p)){
            if(Main.isPlayerInClan(p)){

                /*
                Level-Handler Klasse aufrufen
                 */

                if(Main.getClanKills(p) == requiredKills){
                    p.sendMessage(plugin.prefix + "Clan-Kills für das nächste Level §7(§a" + Main.getClanKills(p) + "§7/§a" + requiredKills + "§7)");
                } else {
                    p.sendMessage(plugin.prefix + "Clan-Kills für das nächste Level §7(§c" + Main.getClanKills(p) + "§7/§a" + requiredKills + "§7)");
                }
                if(Main.getClanMoney(p) == requiredMoney){
                    p.sendMessage(plugin.prefix + "Clan-Money für das nächste Level §7(§a" + Main.getClanMoney(p) + "§7/§a" + requiredMoney + "§7)");
                } else {
                    p.sendMessage(plugin.prefix + "Clan-Money für das nächste Level §7(§c" + Main.getClanMoney(p) + "§7/§a" + requiredMoney + "§7)");
                }
                p.sendMessage(plugin.prefix + "Dein Clan-Level: §6" + Main.getClanLevel(p));
            } else {
                p.sendMessage(plugin.prefix + "Du befindest dich in keinem Clan");
            }
        }
    }
}
