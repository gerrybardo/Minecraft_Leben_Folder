package de.Minecraft_Leben.Clans.Handler;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 05.12.2016.
 */
public class LevelHandler{

    public static double requiredKills;
    public static double requiredMoney;

    public LevelHandler(Player p) {
        getRequiredUpdateSqlData(p);

    }

    public double getRequiredKills(){
        return requiredKills;
    }
    public double getRequiredMoney(){
        return requiredMoney;
    }

    private static void getRequiredUpdateSqlData(Player p) {
        try {
            String sql = "select cl.kills, cl.money from clanlevel  cl\n" +
                    "inner join clan c on (cl.levelid=c.levelid)\n" +
                    "where clanid = ?\n";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setInt(1, Main.getClanID(p));
            ResultSet set = preparedStatement.executeQuery();

            while (set.next()) {
                requiredKills = set.getDouble("cl.kills");
                requiredMoney = set.getDouble("cl.money");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean checkRequiredUpdateSqlData(Player p){
        if(Main.isPlayerInClan(p)){
            if(Main.getClanKills(p) == requiredKills && Main.getClanMoney(p) == requiredMoney){
                return true;
            }
        }
        return false;
    }
}
