package me.raindropz.mc.npcommand;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Npcommand extends JavaPlugin {

    @Override
    public void onEnable() {
        // Register EntityDamageListener
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
