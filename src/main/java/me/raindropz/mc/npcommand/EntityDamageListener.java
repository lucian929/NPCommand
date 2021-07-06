package me.raindropz.mc.npcommand;

import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityDamageListener implements Listener {

    private final Npcommand plugin;
    public EntityDamageListener(Npcommand plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void EntityDamageByEntity(NPCDamageByEntityEvent event) {
        Entity entity = event.getNPC().getEntity();
        boolean isCitizensNPC = entity.hasMetadata("NPC");
        NPC npc = event.getNPC();
        String command = plugin.getConfig().getString("NPCs" + npc + ".command");

        if (event.getDamager() instanceof Arrow && isCitizensNPC && npc.getId().equals(plugin.getConfig().getConfigurationSection("NPCs.id"))) {
            System.out.println("NPC ID: " + npc.getId());
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, command);
        }
        if (!(event.getDamager() instanceof Arrow && isCitizensNPC)) return;
    }
}
/*public class EntityDamageListener implements Listener {
    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent event) { // Changed method name to lowerCamelCase
        Entity entity = event.getEntity();
        if (!(event.getDamager() instanceof Arrow && entity instanceof Player && entity.hasMetadata("NPC"))) return;
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "say Command Executed.");
    }
}
 */
