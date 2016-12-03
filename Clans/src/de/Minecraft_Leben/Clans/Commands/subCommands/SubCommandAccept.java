package de.Minecraft_Leben.Clans.Commands.subCommands;

import de.Minecraft_Leben.Clans.Main;
import org.bukkit.entity.Player;

/**
 * Created by Gerrit Harmeling on 26.11.2016.
 */
public class SubCommandAccept {
    public SubCommandAccept(Player p, Main plugin) {
        if(!Main.playerThreads.contains(p.getName())){
              p.sendMessage(plugin.prefix + "Du hast keine ausstehenden Einladungen");
        } else {
            Main.playerThreads.remove(p.getName());
        }
    }
}
