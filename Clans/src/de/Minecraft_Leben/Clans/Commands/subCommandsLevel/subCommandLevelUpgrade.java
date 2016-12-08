package de.Minecraft_Leben.Clans.Commands.subCommandsLevel;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Handler.LevelHandler;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 08.12.2016.
 */
public class subCommandLevelUpgrade extends LevelHandler{
    private Main plugin;
    public subCommandLevelUpgrade(Player p, Main plugin) {
        super(p);
        this.plugin = plugin;
        if(Main.isPlayerInClan(p)){
            upgrade(p);
        } else {
            p.sendMessage(plugin.prefix + "Du befindest dich in keinem CLan");
        }
    }
    private void upgrade(Player p){
        if(checkRequiredUpdateSqlData(p) == true){
            try{
                String sql = "Update Clan set levelid = levelid +1 where clanid = ?";
                PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
                preparedStatement.setInt(1, Main.getClanID(p));
                preparedStatement.executeUpdate();
                p.sendMessage(plugin.prefix + "Dein Clan wurde erfolgreich auf Level: §e" + Main.getClanLevel(p) + " §fverbessert");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            p.sendMessage(plugin.prefix + "Dein Clan erfüllt die Anforderungen nicht §e-> §f/Clans info für mehr Informationen");
        }
    }
}
