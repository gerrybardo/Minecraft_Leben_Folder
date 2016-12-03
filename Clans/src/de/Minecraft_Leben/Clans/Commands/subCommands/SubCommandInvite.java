package de.Minecraft_Leben.Clans.Commands.subCommands;

import de.Minecraft_Leben.Clans.Main;
import de.Minecraft_Leben.Clans.Thread.InviteThread;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 * Created by Gerrit Harmeling on 18.11.2016.
 */
public class SubCommandInvite {

    private Main plugin;
    public SubCommandInvite(Player p, String arg1, Main plugin, CommandSender sender, Command cmd) {
        this.plugin = plugin;

        if(Main.isPlayerInClan(p) == true) {
            if (Main.getPlayerRank(p) >= 2) {
                if (Bukkit.getServer().getPlayer(arg1) != null) {
                    Player target = Bukkit.getServer().getPlayer(arg1);
                    if (Main.isUserInDatabase(target) == true) {
                        if (Main.isPlayerInClan(target) == false) {
                            if (Main.playerThreads.contains(target.getName())) {
                                p.sendMessage(plugin.prefix + "Dieser Spieler hat bereits eine Einladung ausstehend");
                            } else {
                                Main.playerThreads.add(target.getName());
                                InviteThread thread = new InviteThread(p, target, plugin);
                                thread.start();
                            }
                        } else {
                            p.sendMessage(plugin.prefix + "Dieser Spieler ist bereits in einem Clan");
                        }
                    } else {
                        p.sendMessage(plugin.prefix + "Dieser Spieler exestiert nicht");
                    }
                } else {
                    p.sendMessage(plugin.prefix + "Dieser Spieler ist nicht Online");
                }
            } else {
                p.sendMessage(plugin.prefix + "Diese Funktion ist dem Clan Mod und dem Clan Leader vorbehalten");
            }
        } else {
            p.sendMessage(plugin.prefix + "Du bist in keinem Clan, also kannst du auch keine Leute einladen");
        }
    }
}
