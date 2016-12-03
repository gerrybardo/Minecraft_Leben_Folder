package de.Minecraft_Leben.Clans.Thread;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Gerrit Harmeling on 25.11.2016.
 */
public class InviteThread extends Thread implements Listener{
    private Player p;
    private Player target;
    private Main plugin;
    private int zeit = 61;
    private boolean running = true;
    public InviteThread(Player p, Player target, Main plugin) {
        this.p = p;
        this.target = target;
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }
    public void run(){
        p.sendMessage(plugin.prefix + "Eine Einladung wurde an: §6" + target.getName() + "§f versand");
        target.sendMessage(plugin.prefix + "Der Spieler §6" + p.getName() + "§f hat dich in den Clan §6" + Main.getClanName(p) + "§f eingelanden\n §a/Clans accept §fum die Einladung anzunehmen");
        while (zeit >= 1 && running){
            zeit --;
            if(zeit == 60) {
                target.sendMessage(plugin.prefix + "Du hast noch: §6" + zeit + " §fSekunden um die Einladung anzunehmen");
            }
            if(zeit == 30) {
                target.sendMessage(plugin.prefix + "Du hast noch: §6" + zeit + " §fSekunden um die Einladung anzunehmen");
            }
            if(zeit == 10) {
                target.sendMessage(plugin.prefix + "Du hast noch: §6" + zeit + " §fSekunden um die Einladung anzunehmen");
            }
            if(zeit <= 3 && zeit >= 1) {
                target.sendMessage(plugin.prefix + "Du hast noch: §6" + zeit + " §fSekunden um die Einladung anzunehmen");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       if(zeit <= 1){
           target.sendMessage(plugin.prefix + "Du hast die Einladung von §6" + p.getName() + "§f nicht rechtzeitig angenommen");
           p.sendMessage(plugin.prefix + "§6" + target.getName() + "§f hat deine Einladung nicht angenommen");
       }
        running = false;
        PlayerCommandPreprocessEvent.getHandlerList().unregister(this);
        Main.playerThreads.remove(target.getName());
        return;
    }
    @EventHandler
    private void Listener(PlayerCommandPreprocessEvent e){
        if(e.getMessage().equalsIgnoreCase("/Clans accept")){
            if(e.getPlayer().equals(target)){
                //Spieler in Datenbank in Clan einfügen
                InviteAccepted(p,target);
                target.sendMessage(plugin.prefix + "Einladung angenommen! du bist nun Mitglied des Clans: §6" + Main.getClanName(p));
                p.sendMessage(plugin.prefix + "Der Spieler §6" + target.getName() + "§f ist nun Mitglied in deinem Clan");
                running = false;
                PlayerCommandPreprocessEvent.getHandlerList().unregister(this);
                return;
            }
        }
    }
    //Spieler in Clan einfügen
    private void InviteAccepted(Player p, Player target){
        try {
            String sql = "Update benutzer set clanid = ?, rollenid = ? where name = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setInt(1, Main.getClanID(p));
            preparedStatement.setInt(2, 1);
            preparedStatement.setString(3, target.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
