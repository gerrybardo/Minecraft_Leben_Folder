package de.Minecraft_Leben.Clans;
import de.Minecraft_Leben.Clans.Commands.Main_Command;
import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Listener.PlayerChat_Prefix_ClanChat;
import de.Minecraft_Leben.Clans.Listener.PlayerJoin_Database_Insert;
import de.Minecraft_Leben.Clans.Listener.Player_Damage_In_Clan;
import de.Minecraft_Leben.Clans.Listener.Player_Death_In_Clan;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends JavaPlugin {

    public void onEnable(){
        init();

    }
    public void onDisable(){

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /* Variabeln
    * Prefix
     */
    public static Economy economy = null;
    public final String prefix = "§7[§3§LClans§7]§f ";
    public final String keineRechte = prefix + "§c§LKeine Berechtigung";
    public static ArrayList<String> playerThreads = new ArrayList<String>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void init(){
        Commands();
        Listener();
        LoadConfig();
        Database();
        /*
        Setup Economy by Vault Plugin
         */
        setupEconomy();
    }

    private void Commands(){
        Main_Command main_command = new Main_Command(this);
        getCommand("Clans").setExecutor(main_command);

    }
    private void Listener(){
        PlayerJoin_Database_Insert playerJoinDatabaseInsert = new PlayerJoin_Database_Insert(this);
        PlayerChat_Prefix_ClanChat playerChatPrefixClanChat = new PlayerChat_Prefix_ClanChat(this);
        Player_Damage_In_Clan player_damage_in_clan = new Player_Damage_In_Clan(this);
        Player_Death_In_Clan player_death_in_clan = new Player_Death_In_Clan(this);

    }

    private void Database() {
        CreateConnection createconnection = new CreateConnection(this);
    }

    private void LoadConfig(){
        getConfig().options().copyDefaults(true);
        getConfig().addDefault("Host", new String("localhost"));
        getConfig().addDefault("Datenbankname", new String("clans"));
        getConfig().addDefault("Benutzer", new String("root"));
        getConfig().addDefault("Passwort", new String("test"));
        getConfig().addDefault("Clanname Länge:", new String("10"));
        getConfig().addDefault("Clankürzel Länge:", new String("5"));
        saveConfig();
    }
    //Abfrage ob User in der Datenbank ist | Return True falls er exestiert | Retrun false fals er nicht exestiert
    public static boolean isUserInDatabase(Player p){
        try {
            String sql = "Select name from benutzer where name = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, p.getName());
            ResultSet set = preparedStatement.executeQuery();
            int count = 0;
            while (set.next()){
                count ++;
            }
            if(count == 1){
                //Benutzer exestiert bereits in der Datenbank
                return true;
            } else {
                //Benutzer exestiert noch nicht in der Datenbank
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isClanInDatabase(Player p, String arg1, String arg2){
        try {
            String sql = "Select Clanname, clankürzel from clan where clanname = ? OR clankürzel = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, arg1);
            preparedStatement.setString(2, arg2);
            ResultSet set = preparedStatement.executeQuery();
            int count = 0;
            while (set.next()){
                count ++;
            }
            if(count >= 1){
                //Clan exestiert bereits in der Datenbank
                return true;
            } else {
                //Clan exestiert noch nicht in der Datenbank
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isPlayerInClan(Player p){
        if(getClanID(p) == 0){
            //User ist in keinem Clan
            return false;
        } else {
            //User ist bereits in einem Clan
            return true;
        }
    }
    public static int getClanID(Player p){
        try {
            String sql = "Select clanid from benutzer where name =?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, p.getName());
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                return set.getInt("clanid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String getClanName(Player p){
        try {
            String sql = "select clanname from clan c \n" +
                    "inner join benutzer b on (b.clanid = c.clanid)\n" +
                    "where b.name = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, p.getName());
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                return set.getString("clanname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getClanKürzel(Player p){
        try {
            String sql = "select clankürzel from clan c \n" +
                    "inner join benutzer b on (b.clanid = c.clanid)\n" +
                    "where b.name = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, p.getName());
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                return set.getString("clankürzel");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static int getPlayerRank(Player p){
        try {
            String sql = "select rollenid from benutzer where name = ?";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setString(1, p.getName());
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                return set.getInt("rollenid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int getLeaderCountInClan(Player p){
        try{
            String sql = "select count(clanid) from benutzer where clanid = ? AND rollenid = 3";
            PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
            preparedStatement.setInt(1, getClanID(p));
            ResultSet set = preparedStatement.executeQuery();
            int playerCount = 0;
            while (set.next()){
                playerCount = set.getInt("count(clanid)");
            }
            return playerCount;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /*
    Initalisiere Vault Plugin
     */
    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    /*
    SQL Clan Money
    */
    public static double getClanMoney(Player p){
        if(isPlayerInClan(p) == true && isUserInDatabase(p) == true){
            try{
                String sql = "select money from clan c\n" +
                        "inner join benutzer b on (c.Clanid=b.Clanid)\n" +
                        "where name = ?";
                PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
                preparedStatement.setString(1, p.getName());
                ResultSet set = preparedStatement.executeQuery();
                while (set.next()){
                    return set.getDouble("money");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    public static void setClanMoney(Double money, Player p){
        if(isPlayerInClan(p) == true && isUserInDatabase(p) == true){
            try{
                String sql = "update clan set money = ? where clanid = ?";
                PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
                preparedStatement.setDouble(1, money);
                preparedStatement.setInt(2, getClanID(p));
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static int getClanLevel(Player p){
        if(isUserInDatabase(p) == true){
            if(isPlayerInClan(p)){
                try{
                    String sql = "Select clanlvl from clan where clanid = ?";
                    PreparedStatement preparedStatement = CreateConnection.connection.prepareStatement(sql);
                    preparedStatement.setInt(1, getClanID(p));
                    ResultSet set = preparedStatement.executeQuery();

                    while (set.next()){
                        return set.getInt("clanlvl");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }
}
