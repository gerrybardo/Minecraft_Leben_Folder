package de.Minecraft_Leben.Clans.Commands;

import de.Minecraft_Leben.Clans.Commands.subCommands.*;
import de.Minecraft_Leben.Clans.Commands.subCommandsVault.SubCommandClanBank;
import de.Minecraft_Leben.Clans.Database.CreateConnection;
import de.Minecraft_Leben.Clans.GUI.MainGUI;
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
            MainGUI maiGUI = new MainGUI(p,plugin);
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
                    p.sendMessage(plugin.prefix + "7. /Clans leave| verlässt deinen Clan");
                    p.sendMessage(plugin.prefix + "7. @<Nachricht> | schreibt eine Nachricht im Clan Chat");
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
                if(args[0].equalsIgnoreCase("create")){
                    //Wenn Spieler nur /Clans Create eingibt
                    p.sendMessage(plugin.prefix + "/Clans create <Clanname> <Clankürzel>");
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

                /*
                Die Folgenden Commands sind nur dazu gedacht /Clans CommandUse auszugeben
                 */

                if(args[0].equalsIgnoreCase("cc")) {
                    //Wenn Spieler /Clans accept eingibt
                    p.sendMessage(plugin.prefix + "/cc <Nachricht>");
                }
                if(args[0].equalsIgnoreCase("promote")) {
                    //Wenn Spieler /Clans promote eingibt
                    p.sendMessage(plugin.prefix + "/Clans promote <Spieler>");
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
