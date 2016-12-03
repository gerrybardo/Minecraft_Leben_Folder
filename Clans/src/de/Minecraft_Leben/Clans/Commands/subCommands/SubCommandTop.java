package de.Minecraft_Leben.Clans.Commands.subCommands;

import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by Gerrit Harmeling on 03.12.2016.
 */
public class SubCommandTop {

    private String[] ColorCodes = new String[]{"§e#1 ", "§e#2 ", "§e#3 ", "§e#4 ", "§e#5 "};
    private HashMap<String, Double> topClansHashMap = new HashMap<String, Double>();
    private HashMap<String, Double> singleClanHashMap = new HashMap<String, Double>();
    private boolean isListed = false;
    private Main plugin;

    public SubCommandTop(Player p, Main plugin) {
        this.plugin = plugin;
        topClans(p);
    }

    private void topClans(Player p) {
        try {
            String sql = "Select kills, clankürzel from clan order by kills desc Limit 5";
            Statement statement = CreateConnection.connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            isListed = false;
            while (set.next()) {
                topClansHashMap.put(set.getString("clankürzel"), set.getDouble("kills"));

                if(Main.getClanKürzel(p).equals(set.getString("clankürzel"))){
                    isListed = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*
              Message Output
         */

        p.sendMessage("§8§L§N                        ");
        p.sendMessage("");
        p.sendMessage("        §6§LTop-5");
        Map<String, Double> sortedMap = sortByValue(topClansHashMap);
        printMap(sortedMap, p, ColorCodes);
        p.sendMessage("§8§L§N                         ");
        p.sendMessage("");

        /*
        Show Clan Ranking if not listed above
         */

        clanRanking(p, plugin);
    }
    private void printMap(Map mp, Player p, String[] colorCodes) {
        Iterator it = mp.entrySet().iterator();
        int counter = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            p.sendMessage(colorCodes[counter] + "§6" + pair.getKey() + " §7>> §6" + pair.getValue() + " kills");
            counter++;
            it.remove();
        }
    }
    private Map<String, Double> sortByValue(HashMap<String, Double> unsortMap) {

        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o2,
                               Map.Entry<String, Double> o1) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;

    }
    private void clanRanking(Player p, Main plugin) {
        if (isListed == false) {
            try {
                String sql = "Select kills, clankürzel from clan order by kills desc ";
                Statement statement = CreateConnection.connection.createStatement();
                ResultSet set = statement.executeQuery(sql);
                while (set.next()) {
                    singleClanHashMap.put(set.getString("clankürzel"), set.getDouble("kills"));
                }

                //Map sortieren
                Map<String, Double> sortedMap = sortByValue(singleClanHashMap);
                Iterator it = sortedMap.entrySet().iterator();
                int counter = 1;
                while (it.hasNext()) {
                    counter++;
                    Map.Entry pair = (Map.Entry) it.next();
                    if (Main.getClanKürzel(p).equals(pair.getKey())) {
                        p.sendMessage("     §6§LDein Clan");
                        p.sendMessage("§e#" + counter + " §7>> §6" + pair.getValue() + " kills");
                        p.sendMessage("§8§L§N                         ");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
