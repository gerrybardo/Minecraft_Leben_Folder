package de.Minecraft_Leben.Clans.Listener;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by Gerrit Harmeling on 26.11.2016.
 */
public class PlayerChat_Prefix_ClanChat implements Listener {
    private Main plugin;
    private String cc = "";
    private static final Pattern pat = Pattern.compile("^[@]");
    public PlayerChat_Prefix_ClanChat(Main main) {
        this.plugin = main;
        Bukkit.getServer().getPluginManager().registerEvents(this ,plugin);
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(Main.isUserInDatabase(e.getPlayer())){
            if(Main.isPlayerInClan(e.getPlayer())){

                String msg = e.getMessage().replace("&", "§");
                String Clankürzel = Main.getClanKürzel(e.getPlayer()).replace("&", "§");
                e.setFormat("§l" + Clankürzel + "§r " + msg);
            }
        }
    }
    @EventHandler
    public void clanChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        if (pat.matcher(message).find()) {
            e.setCancelled(true);
            if(Main.isPlayerInClan(e.getPlayer())){
                //String bauen der die Message beinhaltet die ein Spieler gesendet hat
                String[] Message = e.getMessage().split(" ");
                cc = "";
                for (int i = 0; i < Message.length; i++) {
                    cc = cc + Message[i] + " ";
                }
                ArrayList<String> Player = new ArrayList<String>(getPlayersInClan(e.getPlayer()));
                for(String s : Player){
                    if(Bukkit.getServer().getPlayer(s) != null) {
                        org.bukkit.entity.Player target = Bukkit.getServer().getPlayer(s);
                        target.sendMessage("§7[§6" + Main.getClanName(e.getPlayer()) + "´s ClanChat§7]§f " + e.getPlayer().getDisplayName() + ": " + cc.replace("@", ""));
                    }
                }
            } else {
                e.getPlayer().sendMessage(plugin.prefix + "Du kannst den ClanChat nicht benutzen wenn du in keinem Clan bist");
            }
        }
    }
    private ArrayList<String> getPlayersInClan(Player p)  {
        ArrayList<String> players = new ArrayList<String>();
        players.clear();
        try {
            String sql = "Select name from benutzer where clanid = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setInt(1, Main.getClanID(p));
            ResultSet set = preparedStatement.executeQuery();

            while (set.next()){
                players.add(set.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(players);
    }
}
