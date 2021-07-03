package me.raindropz.mc.npcommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Npcommand extends JavaPlugin {
    @Override
    public void onEnable() {
        FileConfiguration config = getConfig();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
        Bukkit.getConsoleSender().sendMessage("NPCommand Version:" + getConfig().getString("version") + "by Raindropz is now enabled.");
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage("NPCommand Version:" + getConfig().getString("version") + "by Raindropz is now disabled.");
    }
}
