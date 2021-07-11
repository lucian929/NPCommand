package me.raindropz.mc.npc;

import me.raindropz.mc.model.MapManager;
import me.raindropz.mc.model.RaindropzNPC;

public class NPCManager extends MapManager<String, RaindropzNPC> {

    public RaindropzNPC getByHandle(String handle) {
        return this.get(handle);
    }

    public RaindropzNPC getById(int id) {
        return this.get((n) -> n.getValue().getId() == id, null);
    }
}
