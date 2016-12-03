package de.Minecraft_Leben.Clans.Database;
import de.Minecraft_Leben.Clans.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 17.11.2016.
 */
public class CreateConnection {

    private static Main plugin;
    public static Connection connection = null;

    public CreateConnection(Main main) {
        this.plugin = main;
        Connect();
    }
    public static void Connect(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + plugin.getConfig().getString("Host") + ":3306/"+ plugin.getConfig().getString("Datenbankname"), plugin.getConfig().getString("Benutzer"), plugin.getConfig().getString("Passwort"));
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
