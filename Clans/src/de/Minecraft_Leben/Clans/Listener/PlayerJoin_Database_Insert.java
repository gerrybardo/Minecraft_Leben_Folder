package de.Minecraft_Leben.Clans.Listener;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 18.11.2016.
 */
public class PlayerJoin_Database_Insert implements Listener {
    private Main plugin;

    public PlayerJoin_Database_Insert(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, main);
    }
    @EventHandler
    private void PlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();

        if(Main.isUserInDatabase(p) == false){
            //Falls User nicht in Datenbank ist
            insertUser(p);
        }
    }
    //Neuen Benutzer in die Datenbank eintragen
    private void insertUser(Player p) {
        try {
            String sql = "Insert into benutzer (name) VALUES (?)";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, p.getName());
            preparedStatement.executeUpdate();

            /*
            Benutzer erfolgreich erstellt
            */

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
