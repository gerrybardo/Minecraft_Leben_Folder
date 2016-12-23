package de.Minecraft_Leben.Clans.Commands.subCommandsLevel.Clan_Home;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 23.12.2016.
 */
public class subCommandTeleportToClanHome {
    Main plugin;
    public subCommandTeleportToClanHome(Player p, Main plugin) {
        this.plugin = plugin;
       if(Main.isPlayerInClan(p)){
           if(isHomePointExisting(p)){
               teleportToClanHome(p);
           } else {
               p.sendMessage(plugin.prefix + "Es exestiert kein Clan-Home, erstelle eines mit /Clans sethome");
           }
       } else {
           p.sendMessage(plugin.prefix + "Für diese Funktion musst du dich in einem Clan befinden.");
       }

    }
    private void teleportToClanHome(Player p){
        try{
        String sql = "Select world, x, y ,z from clanhome where clanid = ?";
        PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setInt(1,Main.getClanID(p));
            ResultSet set = preparedStatement.executeQuery();

            String world = "";
            double x = 0;
            double y = 0;
            double z = 0;

            while (set.next()){
                world = set.getString("world");
                x = set.getDouble("x");
                y = set.getDouble("y");
                z = set.getDouble("z");
            }

            /*
            Create Location and Teleport
             */

            Location clanHome = new Location(Bukkit.getWorld(world),x,y,z);
            p.teleport(clanHome);
            p.sendMessage(plugin.prefix + "§aTeleportiert..");



    } catch (SQLException e) {
            e.printStackTrace();
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
