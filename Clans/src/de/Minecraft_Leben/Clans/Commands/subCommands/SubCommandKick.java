package de.Minecraft_Leben.Clans.Commands.subCommands;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 18.11.2016.
 */
public class SubCommandKick {

    private Main plugin;
    public SubCommandKick(Player p, String arg1, Main plugin) {
        this.plugin = plugin;

        if(Main.getPlayerRank(p) == 3){
            if(Bukkit.getServer().getPlayer(arg1) != null) {
                Player target = Bukkit.getServer().getPlayer(arg1);
                if (Main.isUserInDatabase(target) == true) {
                    if (Main.getClanName(p).equals(Main.getClanName(target))) {
                        if(!p.getName().equals(target.getName())){
                            if(Main.getPlayerRank(target) != 3){
                                kickPlayer(p, target);
                            } else {
                                p.sendMessage(plugin.prefix + "Clan Leader können nicht aus dem Clan gekickt werden");
                            }
                        } else {
                            p.sendMessage(plugin.prefix + "Du kannst dich nicht selber kicken");
                        }
                    } else {
                        p.sendMessage(plugin.prefix + "Dieser Spieler ist nicht in deinem Clan");
                    }
                } else {
                    p.sendMessage(plugin.prefix + "Dieser Spieler exestiert nicht");
                }
            } else {
                p.sendMessage(plugin.prefix + "Dieser Spieler ist nicht Online");
            }
        } else {
            p.sendMessage(plugin.prefix + "Diese Funktion ist dem Clan Leader vorbehalten");
        }
    }
    private void kickPlayer(Player p,Player target){
        try {
        String update = "update benutzer set Clanid = 0, rollenid = 0 where name = ?";
        PreparedStatement preparedStatement1 = CreateConnection.connection.prepareStatement(update);
        preparedStatement1.setString(1, target.getName());
        preparedStatement1.executeUpdate();

            p.sendMessage(plugin.prefix + "Der Spieler §6" + target.getName() + "§f wurde aus dem Clan geworfen");
            target.sendMessage(plugin.prefix + "Du wurdest aus dem Clan §6" + Main.getClanName(p) + "§f geworfen");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
