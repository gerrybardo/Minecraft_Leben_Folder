package de.Minecraft_Leben.Clans.Commands.subCommands;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 18.11.2016.
 */
public class SubCommandPromote {

    private Main plugin;
    public SubCommandPromote(Player p, String arg1, Main plugin) {
        this.plugin = plugin;

        if(Main.getPlayerRank(p) == 3){
            //Clan Leader
            if(Bukkit.getServer().getPlayer(arg1) != null){
                Player target = Bukkit.getServer().getPlayer(arg1);
                if(Main.isUserInDatabase(target) == true){
                    if(Main.getClanName(p).equals(Main.getClanName(target))){
                        if(Main.getPlayerRank(target) != 3){
                            promotePlayer(p, target);
                        } else {
                            p.sendMessage(plugin.prefix + "Der Spieler hat bereits den höchstmöglichen Rang");
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
    private void promotePlayer(Player p,Player target){
        try {
            String sql = "Update benutzer set rollenid = ? where name = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setInt(1, Main.getPlayerRank(target) + 1);
            preparedStatement.setString(2, target.getName());
            preparedStatement.executeUpdate();

            String rang = "Select rollenname from rollen where rollenid =?";
            PreparedStatement preparedStatement1 = CreateConnection.connection.prepareStatement(rang);
            preparedStatement1.setInt(1, Main.getPlayerRank(target));
            ResultSet set = preparedStatement1.executeQuery();

            String rollenname = "";
            while (set.next()){
                rollenname = set.getString("rollenname");
            }
            p.sendMessage(plugin.prefix + "Der Spieler §6" + target.getName() + "§f wurde erfolgreich zum §6" + rollenname + " §fbefördert");
            target.sendMessage(plugin.prefix + "Du wurdest von §6" + p.getName() + "§f zum §6" + rollenname + "§f befördert");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
