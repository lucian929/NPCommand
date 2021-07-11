package me.raindropz.mc.commands;

import me.raindropz.mc.NPCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {

    /**
     * Plugin context
     */
    private final NPCommand plugin;

    /**
     * Default constructor
     *
     * @param plugin plugin
     */
    public ReloadCommand(NPCommand plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("npcommand.reload")) return false;

        if (!command.getName().equalsIgnoreCase("npcommand") || args.length < 1) {
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            plugin.saveDefaultConfig();
            sender.sendMessage(ChatColor.YELLOW + "You have reloaded npcommand config files!");

            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> arguments = Collections.singletonList("reload");
        List<String> Flist = new ArrayList<>();

        if (sender.hasPermission("npcommand.reload")) {
            if (args.length == 1) {
                for (String s : arguments) {
                    if (s.toLowerCase().startsWith(args[0].toLowerCase())) Flist.add(s);
                }
                return Flist;
            }
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }
}