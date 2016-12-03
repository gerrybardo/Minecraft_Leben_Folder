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

    private String[] ColorCodes = new String[]{"§11. ", "§22. ", "§33. ", "§44. ", "§55. "};
    private ArrayList<Integer> topKills = new ArrayList<Integer>();
    private HashMap<String, Double> topClansHashMap = new HashMap<String, Double>();
    public SubCommandTop(Player p, Main plugin) {

        topClans(p);

    }
    private void topClans(Player p) {
        try {
            String sql = "Select kills, clankürzel from clan order by kills desc Limit 5";
            Statement statement = CreateConnection.connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                topClansHashMap.put(set.getString("clankürzel"), set.getDouble("kills"));


            }
            p.sendMessage("§6§LTOP-CLAN-KILLS");
            Map<String, Double> sortedMap = sortByValue(topClansHashMap);
            printMap(sortedMap, p, ColorCodes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void printMap(Map mp, Player p, String[] colorCodes) {
        Iterator it = mp.entrySet().iterator();
        int counter = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            p.sendMessage(colorCodes[counter] +  "" + pair.getKey() + " Kills: " + pair.getValue());
            counter ++;
            it.remove();
        }
    }
    private static Map<String, Double> sortByValue(HashMap<String, Double> unsortMap) {

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
}
