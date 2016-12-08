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
public class SubCommandPayOut {
    Economy econ;
    private double amount;
    public SubCommandPayOut(Player p, String arg, Main plugin) {
        this.econ = Main.economy;
        this.amount = Double.parseDouble(String.valueOf(arg));

        if(Main.isPlayerInClan(p)){
            if(Main.getClanMoney(p) > amount){
                removeMoneyFromClanSQL(p);
                econ.depositPlayer(p, amount);
                p.sendMessage(plugin.prefix + "Du hast erfolgreich §e" + amount + "$§f erhalten");
            } else {
                p.sendMessage(plugin.prefix + "Die Clan-Kasse enhält nur noch §e" + Main.getClanMoney(p) + "$§f");
            }
        } else {
            p.sendMessage(plugin.prefix + "Du befindest dich in keine Clan");
        }
    } private void removeMoneyFromClanSQL(Player p){
        try{
            String sql = "update clan set money = money - ? where Clanid = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, Main.getClanID(p));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
