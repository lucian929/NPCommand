package me.raindropz.mc.event;

import me.clip.placeholderapi.PlaceholderAPI;
import me.raindropz.mc.NPCommand;
import me.raindropz.mc.model.RaindropzNPC;
import me.raindropz.mc.npc.NPCManager;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;

public class EntityDamageListener implements Listener {

    /**
     * Plugin context
     */
    private final NPCommand plugin;

    /**
     * Default constructor
     *
     * @param plugin plugin
     */
    public EntityDamageListener(NPCommand plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void EntityDamageByEntity(NPCDamageByEntityEvent event) {
        // get entity
        Entity entity = event.getNPC().getEntity();

        boolean isCitizensNPC = entity.hasMetadata("NPC");

        // if the entity doesn't adhere to the standard set by citizens
        // and if the damager isn't an arrow
        if (!isCitizensNPC || !(event.getDamager() instanceof Arrow)) {
            return;
        }

        // get arrow
        Arrow arrow = (Arrow) event.getDamager();

        // if the shooter isn't a player, return
        if (!(arrow.getShooter() instanceof Player)) {
            return;
        }

        // get npc
        NPC npc = event.getNPC();

        NPCManager npcManager = this.plugin.getNpcManager();
        // get applicable RaindropzNPC
        RaindropzNPC raindropzNPC = npcManager.getById(npc.getId());

        // if it doesn't exist, return
        if (raindropzNPC == null) {
            return;
        }

        // parse command with placeholder api
        String command = PlaceholderAPI.setPlaceholders((Player) arrow.getShooter(), raindropzNPC.getCommand());

        Location location = npc.getEntity().getLocation();

        // spawn firework
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        boolean flicker = plugin.getConfig().getBoolean("flicker");
        int redColor = plugin.getConfig().getInt("firework-color.RED");
        int greenColor = plugin.getConfig().getInt("firework-color.GREEN");
        int blueColor = plugin.getConfig().getInt("firework-color.BLUE");

        FireworkEffect.Builder fireworkEffect = FireworkEffect.builder()
                .flicker(flicker)
                .withColor(Color.fromRGB(redColor, greenColor, blueColor));

        fireworkMeta.addEffect(fireworkEffect.build());
        firework.setFireworkMeta(fireworkMeta);

        // get console sender
        ConsoleCommandSender consoleSender = Bukkit.getServer().getConsoleSender();
        Bukkit.dispatchCommand(consoleSender, command);

        // remove arrow
        arrow.remove();
    }
}