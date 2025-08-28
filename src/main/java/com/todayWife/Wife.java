package com.todayWife;

import java.util.UUID;

public class Wife {
    private String wifeName;
    private UUID wifeUUID;
    private int affection;
    private long lastSelected;

    public Wife(String wifeName, int affection) {
        this.wifeName = wifeName;
        this.affection = affection;
        this.lastSelected = System.currentTimeMillis();
    }

    public Wife(String wifeName, UUID wifeUUID, int affection, long lastSelected) {
        this.wifeName = wifeName;
        this.wifeUUID = wifeUUID;
        this.affection = affection;
        this.lastSelected = lastSelected;
    }

    public String getWifeName() {
        return wifeName;
    }

    public UUID getWifeUUID() {
        return wifeUUID;
    }

    public int getAffection() {
        return affection;
    }

    public long getLastSelected() {
        return lastSelected;
    }

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public void setAffection(int affection) {
        this.affection = affection;
    }

    public void addAffection(int change) {
        this.affection += change;
    }

    public boolean isExpired() {
        // 24小时过期，可以根据实际情况修改
        return System.currentTimeMillis() - lastSelected > 24 * 60 * 60 * 1000;
    }

    public void refresh() {
        this.lastSelected = System.currentTimeMillis();
    }
}