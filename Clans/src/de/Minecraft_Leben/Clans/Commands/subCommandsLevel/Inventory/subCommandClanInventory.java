package de.Minecraft_Leben.Clans.Commands.subCommandsLevel.Inventory;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 08.12.2016.
 */
public class subCommandClanInventory extends InventoryStringDeSerializer implements Listener {
    private Player p;
    private Main plugin;
    public subCommandClanInventory(Player p, Main plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        this.p = p;
        this.plugin = plugin;

        if(Main.isPlayerInClan(p)){
            if(Main.getClanLevel(p) == 3){
                p.openInventory(getInventoryFromSQL(p));
            } else {
                p.sendMessage(plugin.prefix + "Du benötigst Clan-Level §e3§f um diese Funktion zu benutzen");
            }
        }
    }
    private void safeInvToSQL(Inventory inv, Player p){
        try{
            String sql = "Update clan set chest = ? where clanid = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, InventoryToString(inv));
            preparedStatement.setInt(2, Main.getClanID(p));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @EventHandler
    private void inventoryCloseEvent(InventoryCloseEvent e){
        if(Main.isPlayerInClan(p) && e.getInventory().getName().equals("Clan-Chest")){
            safeInvToSQL(e.getInventory(), p);
            p.sendMessage(plugin.prefix + "§aClan-Chest erfolgreich gespeichert!");
            InventoryCloseEvent.getHandlerList().unregister(this);
        }
    }
    private Inventory getInventoryFromSQL(Player p){
        try{
            String sql = "Select chest from clan where clanid = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setInt(1, Main.getClanID(p));
            ResultSet set = preparedStatement.executeQuery();

            String inventoryString = "";
            Inventory inv = Bukkit.createInventory(null, 27, "Clan-Chest");

            while (set.next()){
                inventoryString = set.getString("chest");
            }
            if(inventoryString.isEmpty()){
                return inv;

            } else {
                Inventory i = StringToInventory(inventoryString);
                for (ItemStack is : i) {
                    if (is != null) {
                        inv.addItem(is);
                    }
                    return inv;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
