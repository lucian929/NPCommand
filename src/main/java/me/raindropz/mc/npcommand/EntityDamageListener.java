package me.raindropz.mc.npcommand;

import me.clip.placeholderapi.PlaceholderAPI;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Collections;

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

        ConfigurationSection path = plugin.getConfig().getConfigurationSection("NPCs");

        if (!(event.getDamager() instanceof Arrow && isCitizensNPC)) return;

        if (event.getDamager() instanceof Arrow) {

            Arrow arrow = (Arrow) event.getDamager();

            for (String npcs : path.getKeys(false)) {
                int npcID = plugin.getConfig().getInt("NPCs." + npcs + ".id");

                if (npc.getId() == npcID) {
                    String command = PlaceholderAPI.setPlaceholders(((Player) arrow.getShooter()).getPlayer(), plugin.getConfig().getString("NPCs." + npcs + ".command"));
                    String fireworkColor = plugin.getConfig().getString("firework-color");
                    Location npcLoc = npc.getEntity().getLocation();

                    Firework firework = (Firework) npcLoc.getWorld().spawnEntity(npcLoc, EntityType.FIREWORK);
                    FireworkMeta fireworkMeta = firework.getFireworkMeta();

                    fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.YELLOW).flicker(plugin.getConfig().getBoolean("flicker")).build());
                    firework.setFireworkMeta(fireworkMeta);

                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, command);

                    event.getDamager().remove();

                }
            }
        }
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