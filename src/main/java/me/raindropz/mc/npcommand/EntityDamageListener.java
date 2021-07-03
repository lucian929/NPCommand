package me.raindropz.mc.npcommand;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageListener implements Listener {

    private final Npcommand plugin;
    public EntityDamageListener(Npcommand plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void EntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        boolean isCitizensNPC = entity.hasMetadata("NPC");

        if (event.getDamager() instanceof Arrow && event.getEntity() instanceof Player && isCitizensNPC) {
            // my code here
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String command = plugin.getConfig().getString("commandOnHit");
            Bukkit.dispatchCommand(console, command);
        }
    }
}
