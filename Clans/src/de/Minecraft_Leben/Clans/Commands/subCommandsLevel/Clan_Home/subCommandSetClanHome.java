package de.Minecraft_Leben.Clans.Commands.subCommandsLevel.Clan_Home;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 23.12.2016.
 */
public class subCommandSetClanHome {
    private Main plugin;
    public subCommandSetClanHome(Player p, Main plugin) {
        this.plugin = plugin;
        if(Main.isPlayerInClan(p)) {
            safeLocationToSQL(p);
        } else {
            p.sendMessage(plugin.prefix + "Für diese Funktion musst du dich in einem Clan befinden.");
        }
    }

    private void safeLocationToSQL(Player p) {
        if(isHomePointExisting(p)){
            try{
            String sql = "Update clanhome set world = ?,x = ?,y = ?,z = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
                preparedStatement.setString(1, p.getWorld().getName().toString());
                preparedStatement.setDouble(2, p.getLocation().getX());
                preparedStatement.setDouble(3, p.getLocation().getY());
                preparedStatement.setDouble(4, p.getLocation().getZ());
                preparedStatement.executeUpdate();

                p.sendMessage(plugin.prefix + "Clan-Home erfolgreich gesetzt");

            } catch (SQLException e) {
                e.printStackTrace();
            }

            } else {
            try {
                String sql = "Insert into clanhome (clanid, world, x, y, z) VALUES (?,?,?,?,?)";
                PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
                preparedStatement.setInt(1, Main.getClanID(p));
                preparedStatement.setString(2, p.getWorld().getName().toString());
                preparedStatement.setDouble(3, p.getLocation().getX());
                preparedStatement.setDouble(4, p.getLocation().getY());
                preparedStatement.setDouble(5, p.getLocation().getZ());
                preparedStatement.executeUpdate();

                p.sendMessage(plugin.prefix + "Clan-Home erfolgreich gesetzt");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isHomePointExisting(Player p) {
        try {
            String sql = "Select * from clanhome where clanid = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setInt(1, Main.getClanID(p));
            ResultSet set = preparedStatement.executeQuery();

            int counter = 0;
            while(set.next()){
                counter ++;
            }
            if(counter == 0){
                return false;
            } else{
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

