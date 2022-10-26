package info.ahaha.scoreboardplugin.model;

import java.util.UUID;

public class PlayerData {
    private UUID uuid;
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
