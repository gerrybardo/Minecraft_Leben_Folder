package de.Minecraft_Leben.Clans.Commands.subCommands;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 23.11.2016.
 */
public class SubCommandLeave {
    public SubCommandLeave(Player p, Main plugin) {
        if(Main.isUserInDatabase(p)){
            if(Main.isPlayerInClan(p)){
                leaveSql(p, plugin);
            } else {
                p.sendMessage(plugin.prefix + "Du bist in keinem Clan");
            }
        }
    }
    private void leaveSql(Player p, Main plugin){
        if(Main.getLeaderCountInClan(p) <= 1 && Main.getPlayerRank(p) == 3){
            p.sendMessage(plugin.prefix + "Du bist der letzte Clan Leader benutze /Clans delete um den Clan zu löschen");
        } else {
            try {
                String sql = "Update benutzer set clanid = 0, rollenid = 0 where name = ?";
                PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
                preparedStatement.setString(1, p.getName());
                // Sende die Nachricht vor dem ausführen des Statements weil sonst kein Clanname mehr vorhanden ist.
                p.sendMessage(plugin.prefix + "Du hast den Clan: §6" + Main.getClanName(p) + " §ferfolgreich verlassen");
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
