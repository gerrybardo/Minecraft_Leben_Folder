package de.Minecraft_Leben.Clans.Commands.subCommandsVault;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 08.12.2016.
 */
public class SubCommandPay {
    Economy econ;
    private double amount;
    public SubCommandPay(Player p, String arg, Main plugin) {
        this.econ = Main.economy;
        this.amount = Double.parseDouble(String.valueOf(arg));

        if(Main.isPlayerInClan(p)){
            if(econ.getBalance(p) > amount){
                econ.withdrawPlayer(p, amount);
                insertMoneyToSql(p);
                p.sendMessage(plugin.prefix + "Erfolgreich §e" + amount + "$§f in die Clan-Kasse eingezahlt");
            } else {
                p.sendMessage(plugin.prefix + "Dein Aktuelles Guthaben reicht nicht aus!");
            }
        } else {
            p.sendMessage(plugin.prefix + "Du befindest dich in keinem Clan");
        }
    }
    private void insertMoneyToSql(Player p){
        try{
            String sql = "Update clan set money = money + ? where clanid = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, Main.getClanID(p));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
