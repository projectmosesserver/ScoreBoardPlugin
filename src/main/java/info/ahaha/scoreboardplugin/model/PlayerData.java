package info.ahaha.scoreboardplugin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PlayerData {
    @JsonProperty
    private final UUID uuid;
    @JsonProperty
    private final boolean enabled;

    public PlayerData(UUID uuid, boolean enabled) {
        this.uuid = uuid;
        this.enabled = enabled;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
