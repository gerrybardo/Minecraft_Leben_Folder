package de.Minecraft_Leben.Clans.Commands;

import de.Minecraft_Leben.Clans.Commands.subCommands.*;
import de.Minecraft_Leben.Clans.Commands.subCommandsLevel.Clan_Home.subCommandSetClanHome;
import de.Minecraft_Leben.Clans.Commands.subCommandsLevel.Clan_Home.subCommandTeleportToClanHome;
import de.Minecraft_Leben.Clans.Commands.subCommandsLevel.Inventory.subCommandClanInventory;
import de.Minecraft_Leben.Clans.Commands.subCommandsLevel.subCommandCurrentInfo;
import de.Minecraft_Leben.Clans.Commands.subCommandsLevel.subCommandLevelUpgrade;
import de.Minecraft_Leben.Clans.Commands.subCommandsVault.SubCommandClanBank;
import de.Minecraft_Leben.Clans.Commands.subCommandsVault.SubCommandPay;
import de.Minecraft_Leben.Clans.Commands.subCommandsVault.SubCommandPayOut;
import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Gerrit Harmeling on 17.11.2016.
 */
public class Main_Command implements CommandExecutor {

    private Main plugin;
    private String cc = "";
    public Main_Command(Main main) {
        plugin = main;
    }

    public Main_Command() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        if(args.length == 0){
            p.sendMessage(plugin.prefix + "/Clans help | Um alle Befehle anzuzeigen");
        } else {
            if(args.length == 1){
                //Falls Benutzer /Clans help eingibt
                if(args[0].equalsIgnoreCase("help")){
                    if(p.hasPermission("Clans.Admin")){
                        p.sendMessage(plugin.prefix + "/Clans reload | Lädt Config neu");
                        p.sendMessage(plugin.prefix);
                    }
                    p.sendMessage(plugin.prefix + "1. /Clans create <Clanname> <Clankürzel> | Erstellt einen Clan");
                    p.sendMessage(plugin.prefix + "2. /Clans delete | löscht deinen Clan");
                    p.sendMessage(plugin.prefix + "3. /Clans promote <Spieler> | befördert einen Spieler");
                    p.sendMessage(plugin.prefix + "4. /Clans demote <Spieler> | Degradiert einen Spieler");
                    p.sendMessage(plugin.prefix + "4. /Clans kick <Spieler> | Wirft einen Spieler aus dem Clan");
                    p.sendMessage(plugin.prefix + "5. /Clans invite <Spieler> | Lädt einen Spieler in deinen Clan ein");
                    p.sendMessage(plugin.prefix + "6. /Clans accept | Nimmt die Einladung eines Clans an");
                    p.sendMessage(plugin.prefix + "7. /Clans leave | verlässt deinen Clan");
                    p.sendMessage(plugin.prefix + "8. /Clans top | Zeigt dir die Bestenliste an");
                    p.sendMessage(plugin.prefix + "9. /Clans money | Zeigt die Clan-Kasse an");
                    p.sendMessage(plugin.prefix + "10. /Clans info |Zeigt das aktuelle Clan Level");
                    p.sendMessage(plugin.prefix + "11. /Clans upgrade | Erhöt das Clan-Level");
                    p.sendMessage(plugin.prefix + "12. /Clans pay <Betrag> | Zahlt Geld in die Clan-Kasse ein");
                    p.sendMessage(plugin.prefix + "13. /Clans payout <Betrag> | Holt Geld aus der Clan-Kasse raus");
                    p.sendMessage(plugin.prefix + "14. /Clans chest | öffnet die Clan-Chest");
                    p.sendMessage(plugin.prefix + "15. @<Nachricht> | schreibt eine Nachricht im Clan Chat");
                }
                //Falls User mit Clans.Admin rechten /reload eingibt
                if(args[0].equalsIgnoreCase("reload")){
                    if(p.hasPermission("Clans.Admin")){
                        plugin.reloadConfig();
                        p.sendMessage(plugin.prefix + "Config erfolgreich neu geladen");
                        CreateConnection.connection = null;
                        CreateConnection.Connect();
                        CheckConnection(p);
                    } else {
                        p.sendMessage(plugin.keineRechte);
                    }
                }
                if(args[0].equalsIgnoreCase("info")){
                    //Wenn Spieler  /Clans level eingibt
                    subCommandCurrentInfo subCommandCurrentInfo = new subCommandCurrentInfo(p, plugin);
                }
                if(args[0].equalsIgnoreCase("sethome")){
                    //Wenn Spieler  /Clans level eingibt
                    subCommandSetClanHome subCommandSetClanHome = new subCommandSetClanHome(p, plugin);
                }
                if(args[0].equalsIgnoreCase("home")){
                    //Wenn Spieler  /Clans level eingibt
                    subCommandTeleportToClanHome subCommandTeleportToClanHome = new subCommandTeleportToClanHome(p, plugin);
                }
                if(args[0].equalsIgnoreCase("chest")){
                    //Wenn Spieler  /Clans level eingibt
                    subCommandClanInventory subCommandClanInventory = new subCommandClanInventory(p, plugin);
                }
                if(args[0].equalsIgnoreCase("upgrade")){
                    //Wenn Spieler  /Clans level eingibt
                    subCommandLevelUpgrade subCommandLevelUpgrade = new subCommandLevelUpgrade(p, plugin);
                }
                if(args[0].equalsIgnoreCase("delete")){
                    //Wenn Spieler /Clans delete eingibt
                    SubCommandDelete delete = new SubCommandDelete(p, plugin);
                }
                if(args[0].equalsIgnoreCase("leave")){
                    //Wenn Spieler /Clans leave eingibt
                    SubCommandLeave leave = new SubCommandLeave(p, plugin);
                }
                if(args[0].equalsIgnoreCase("accept")) {
                    //Wenn Spieler /Clans accept eingibt
                    SubCommandAccept accept = new SubCommandAccept(p, plugin);
                }
                if(args[0].equalsIgnoreCase("money")){
                    //Wenn Spieler /Clans money eingeben
                    SubCommandClanBank subCommandClanBank = new SubCommandClanBank(p,plugin);
                }
                if(args[0].equalsIgnoreCase("top")){
                    //Wenn Spieler /Clans money eingeben
                    SubCommandTop subCommandTop = new SubCommandTop(p,plugin);
                }

                /*
                Die Folgenden Commands sind nur dazu gedacht /Clans CommandUse auszugeben
                 */

                if(args[0].equalsIgnoreCase("create")){
                    //Wenn Spieler nur /Clans Create eingibt
                    p.sendMessage(plugin.prefix + "/Clans create <Clanname> <Clankürzel>");
                }
                if(args[0].equalsIgnoreCase("promote")) {
                    //Wenn Spieler /Clans promote eingibt
                    p.sendMessage(plugin.prefix + "/Clans promote <Spieler>");
                }
                if(args[0].equalsIgnoreCase("pay")) {
                    //Wenn Spieler /Clans promote eingibt
                    p.sendMessage(plugin.prefix + "/Clans pay <Betrag>");
                }
                if(args[0].equalsIgnoreCase("payout")) {
                    //Wenn Spieler /Clans promote eingibt
                    p.sendMessage(plugin.prefix + "/Clans payout <Betrag>");
                }
                if(args[0].equalsIgnoreCase("demote")) {
                    //Wenn Spieler /Clans demote eingibt
                    p.sendMessage(plugin.prefix + "/Clans demote <Spieler>");
                }
                if(args[0].equalsIgnoreCase("kick")) {
                    //Wenn Spieler /Clans kick eingibt
                    p.sendMessage(plugin.prefix + "/Clans kick <Spieler>");
                }
                if(args[0].equalsIgnoreCase("invite")) {
                    //Wenn Spieler /Clans invite eingibt
                    p.sendMessage(plugin.prefix + "/Clans invite <Spieler>");
                }
            } else {
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("promote")){
                        //Wenn Spieler /Clans Promote eingibt
                        SubCommandPromote promote = new SubCommandPromote(p, args[1], plugin);
                    }
                    if(args[0].equalsIgnoreCase("demote")){
                        //Wenn Spieler /Clans demote eingibt
                        SubCommandDemote demote = new SubCommandDemote(p, args[1], plugin);
                    }
                    if(args[0].equalsIgnoreCase("kick")){
                        //Wenn Spieler /Clans kick eingibt
                        SubCommandKick kick = new SubCommandKick(p, args[1], plugin);
                    }
                    if(args[0].equalsIgnoreCase("invite")){
                        //Wenn Spieler /Clans kick eingibt
                        SubCommandInvite invite = new SubCommandInvite(p, args[1], plugin, sender, cmd);
                    }
                    if(args[0].equalsIgnoreCase("pay")){
                        //Wenn Spieler /Clans kick eingibt
                        SubCommandPay pay = new SubCommandPay(p, args[1], plugin);
                    }
                    if(args[0].equalsIgnoreCase("payout")){
                        //Wenn Spieler /Clans kick eingibt
                        SubCommandPayOut payOut = new SubCommandPayOut(p, args[1], plugin);
                    }

                     /*
                    Die Folgenden Commands sind nur dazu gedacht /Clans CommandUse auszugeben
                     */

                    if(args[0].equalsIgnoreCase("create")){
                       p.sendMessage(plugin.prefix + "/Clans create <Clanname> <Clankürzel>");
                    }
                } else {
                    if (args.length == 3){
                        if(args[0].equalsIgnoreCase("create")){
                            if(args[1] != null && args[2] != null){
                                //Clan wird erstellt
                                SubCommandCreate create = new SubCommandCreate(p, args[1], args[2], plugin);
                            } else {
                                p.sendMessage(plugin.prefix + "/Clans create <Clanname> <Clankürzel>");
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    private void CheckConnection(Player p){
        if(CreateConnection.connection == null){
            p.sendMessage(plugin.prefix + "§cDatenbankverbindung fehlgeschlagen");
        } else {
            p.sendMessage(plugin.prefix + "§aDatenbankverbindung erfolgreich");
        }
    }
}
