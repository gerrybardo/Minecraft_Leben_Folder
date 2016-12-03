package de.Minecraft_Leben.Clans.Listener;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 03.12.2016.
 */
public class Player_Death_In_Clan implements Listener {
    private Main plugin;
    public Player_Death_In_Clan(Main main) {
        this.plugin = main;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void playerDeathClan(PlayerDeathEvent e){
        Player deadPlayer = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();

        if(Main.isUserInDatabase(deadPlayer) && Main.isUserInDatabase(killer)){
           if(Main.isPlayerInClan(killer)){
               try {
                   String sql = "Update clan set kills = kills + 1 where Clanid = ?";
                   PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
                   preparedStatement.setInt(1, Main.getClanID(killer));
                   preparedStatement.executeUpdate();
               } catch (SQLException e1) {
                   e1.printStackTrace();
               }
           }
        }
    }
}
