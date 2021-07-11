package me.raindropz.mc.model;

public class RaindropzNPC {

    /**
     * The npc's handle
     */
    private String handle;

    /**
     * Identifier
     */
    private int id;

    /**
     * The command associated with the npc
     */
    private String command;

    /**
     * Default constructor
     *
     * @param handle  handle
     * @param id      id
     * @param command command
     */
    public RaindropzNPC(String handle, int id, String command) {
        this.handle = handle;
        this.id = id;
        this.command = command;
    }

    public String getHandle() {
        return handle;
    }

    public RaindropzNPC setHandle(String handle) {
        this.handle = handle;
        return this;
    }

    public int getId() {
        return id;
    }

    public RaindropzNPC setId(int id) {
        this.id = id;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public RaindropzNPC setCommand(String command) {
        this.command = command;
        return this;
    }
}
