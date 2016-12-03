package de.Minecraft_Leben.Clans.Commands.subCommands;
import de.Minecraft_Leben.Clans.Commands.Main_Command;
import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 18.11.2016.
 */
public class SubCommandCreate extends Main_Command{

    private Main plugin;
    public SubCommandCreate(Player p, String arg1, String arg2, Main plugin) {
        this.plugin = plugin;

            if(Main.isPlayerInClan(p) == false) {
                //User ist in keinem Clan
                if (!(arg1.length() > Integer.parseInt(String.valueOf(plugin.getConfig().getString("Clanname Länge:"))) || arg2.length() > Integer.parseInt(String.valueOf(plugin.getConfig().getString("Clankürzel Länge:"))))) {

                    if (Main.isClanInDatabase(p, arg1, arg2) == false) {
                        //Clan exestiert noch nicht in der Datenbank
                        insertClan(p, arg1, arg2);
                    } else {
                        //Clan exestiert bereits in der Datenbank
                        p.sendMessage(this.plugin.prefix + "Dieser Clan oder das Clankürzel exestiert bereits");
                    }
                } else {
                    p.sendMessage(plugin.prefix + "Der Clanname darf nicht länger als " + plugin.getConfig().getString("Clanname Länge:") + " Zeichen sein, das Clankürzel darf nicht länger als " + plugin.getConfig().getString("Clankürzel Länge:") + " Zeichen sein");
                }
            } else {
                p.sendMessage(plugin.prefix + "Du befindest dich bereits in einem Clan");
            }
        }

    private void insertClan(Player p, String arg1, String arg2){
        try {
            //Clan erstellen
            String sql = "Insert into Clan (Clanname, Clankürzel) VALUES (?,?)";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, arg1);
            preparedStatement.setString(2, arg2);
            preparedStatement.executeUpdate();

            /*
            Clan erfolgreich erstellt
             */

            //Füge dem Benutzer den Jeweiligen Clan hinzu und die Leader Rechte
            String update = "Update benutzer set Clanid = (Select clanid from clan where Clanname = ? AND Clankürzel = ?), Rollenid = 3\n" +
                    "where name = ?";
            PreparedStatement preparedStatement1 = CreateConnection.connection.prepareStatement(update);
            preparedStatement1.setString(1, arg1);
            preparedStatement1.setString(2, arg2);
            preparedStatement1.setString(3, p.getName());
            preparedStatement1.executeUpdate();

            /*
            Clan erfolgreich dem Benutzer zugewiesen
            Clan Leader Rechte erfolgreich gesetzt
             */

            p.sendMessage(plugin.prefix + "§aDer Clan §6" + arg1 + " §awurde erfolgreich erstellt");
            p.sendMessage(plugin.prefix + "/Clans help | für weitere Informationen");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
