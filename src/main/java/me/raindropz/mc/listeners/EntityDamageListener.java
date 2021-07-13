package me.raindropz.mc.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.raindropz.mc.npcommand.Npcommand;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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
                    Location npcLoc = npc.getEntity().getLocation();
                    Player p = (Player) arrow.getShooter();
                    PersistentDataContainer npcHitCount = ((Player) arrow.getShooter()).getPersistentDataContainer();

                    /*
                    Npc hit counting handling (For PlaceholderAPI Support)
                     */

                    int hitCount = ((Player) arrow.getShooter()).getPersistentDataContainer().get(new NamespacedKey(plugin, "npc-hit"), PersistentDataType.INTEGER);
                    hitCount++;
                    npcHitCount.set(new NamespacedKey(plugin, "npc-hit"), PersistentDataType.INTEGER, hitCount);

                    /*
                    Firework and Arrow handling
                     */

                    Firework firework = (Firework) npcLoc.getWorld().spawnEntity(npcLoc, EntityType.FIREWORK);
                    FireworkMeta fireworkMeta = firework.getFireworkMeta();
                    FireworkEffect.Builder fireworkEffect = FireworkEffect.builder();

                    boolean flicker = plugin.getConfig().getBoolean("flicker");
                    int redColor = plugin.getConfig().getInt("firework-color.RED");
                    int greenColor = plugin.getConfig().getInt("firework-color.GREEN");
                    int blueColor = plugin.getConfig().getInt("firework-color.BLUE");

                    fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.fromRGB(redColor, greenColor, blueColor)).flicker(flicker).build());
                    firework.setFireworkMeta(fireworkMeta);

                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, command);

                    arrow.remove();

                }
            }
        }
    }
}