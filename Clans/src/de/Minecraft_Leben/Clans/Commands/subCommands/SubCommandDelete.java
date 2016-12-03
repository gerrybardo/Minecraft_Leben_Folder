package de.Minecraft_Leben.Clans.Commands.subCommands;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 18.11.2016.
 */
public class SubCommandDelete {
    private Main plugin;
    public SubCommandDelete(Player p, Main plugin) {
        this.plugin = plugin;

        if(Main.isPlayerInClan(p) == false){
            p.sendMessage(plugin.prefix + "Du befindest dich in keinem Clan");
        } else {
            if(Main.getPlayerRank(p) >= 3){
                deleteClan(p, Main.getClanName(p));
            } else {
                p.sendMessage(plugin.keineRechte);
            }
        }
    }
    private void deleteClan(Player p, String arg1){
        try {

            //Alle benutzer die in diesem Clan waren auf den Status 0 Setzen
            String update = "update benutzer set Clanid = 0, rollenid = 0\n" +
                    "where Clanid = ?";
            PreparedStatement preparedStatement1 = CreateConnection.connection.prepareStatement(update);
            preparedStatement1.setInt(1, Main.getClanID(p));
            preparedStatement1.executeUpdate();

            //Clan löschen
            String delete ="delete from clan where Clanname =?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(delete);
            preparedStatement.setString(1, arg1);
            preparedStatement.executeUpdate();

            p.sendMessage(plugin.prefix + "§aDer Clan §6" + arg1 + "§a wurde erfolgreich gelöscht");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
