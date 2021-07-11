package me.raindropz.mc;

import me.raindropz.mc.commands.ReloadCommand;
import me.raindropz.mc.event.EntityDamageListener;
import me.raindropz.mc.loader.NPCLoader;
import me.raindropz.mc.npc.NPCManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class NPCommand extends JavaPlugin {

    /**
     * NPC Manager
     */
    private NPCManager npcManager;

    @Override
    public void onEnable() {
        this.npcManager = new NPCManager();

        // saves default config
        this.saveDefaultConfig();

        // load npcs
        new NPCLoader(this).load(this.npcManager);

        registerEvents();

        logPluginStateChange(true);

        Objects.requireNonNull(this.getCommand("npcommand")).setExecutor(new ReloadCommand(this));
    }

    /**
     * Registers events
     * <p>
     * E.g. EntityDamageListener
     */
    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
    }

    @Override
    public void onDisable() {
        this.saveDefaultConfig();

        logPluginStateChange(false);
    }

    private void logPluginStateChange(boolean isStartup) {
        PluginDescriptionFile descriptionFile = this.getDescription();
        String version = descriptionFile.getVersion();
        String author = descriptionFile.getAuthors().stream()
                .findFirst()
                .orElse("Raindropz");

        this.getLogger().info("NPCommand v" + version + "by " + author + " is now " + (isStartup ? "enabled" : "disabled") + ".");
    }

    public NPCManager getNpcManager() {
        return npcManager;
    }
}
