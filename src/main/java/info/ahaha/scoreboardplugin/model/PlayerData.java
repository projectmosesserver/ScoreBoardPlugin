package info.ahaha.scoreboardplugin.model;

import java.util.UUID;

public class PlayerData {
    private UUID uuid;
    private boolean enableSB;

    public boolean isEnableSB() {
        return enableSB;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setEnableSB(boolean enableSB) {
        this.enableSB = enableSB;
    }
}
