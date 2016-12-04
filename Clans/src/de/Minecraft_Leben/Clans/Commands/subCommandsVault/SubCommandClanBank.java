package de.Minecraft_Leben.Clans.Commands.subCommandsVault;

import de.Minecraft_Leben.Clans.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

/**
 * Created by Gerrit Harmeling on 01.12.2016.
 */
public class SubCommandClanBank {

    private Economy econ;
    public SubCommandClanBank(Player p, Main plugin) {
        this.econ = Main.economy;
        p.sendMessage(plugin.prefix + "Clan-Kasse: §6" + Main.getClanMoney(p) + "$");
    }
}
