package me.raindropz.mc.loader;

import me.raindropz.mc.NPCommand;
import me.raindropz.mc.model.Loader;
import me.raindropz.mc.model.RaindropzNPC;
import me.raindropz.mc.npc.NPCManager;
import org.bukkit.configuration.ConfigurationSection;

public class NPCLoader implements Loader<NPCManager> {

    /**
     * Plugin context
     */
    private final NPCommand plugin;

    public NPCLoader(NPCommand plugin) {
        this.plugin = plugin;
    }

    /**
     * Loads by context O
     *
     * @param context context
     */
    @Override
    public void load(NPCManager context) {
        ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("NPCs");

        // loop through npcs in configuration
        configurationSection.getKeys(false).forEach(handle -> {
            ConfigurationSection section = configurationSection.getConfigurationSection(handle);

            int id = section.getInt("id");
            String command = section.getString("command");

            RaindropzNPC raindropzNPC = new RaindropzNPC(handle, id, command);

            // add to manager
            context.put(handle, raindropzNPC);
        });
    }
}
