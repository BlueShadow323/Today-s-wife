package com.todayWife;

import org.bukkit.entity.Player;

public class SoundManager {
    private final TodayWife plugin;

    public SoundManager(TodayWife plugin) {
        this.plugin = plugin;
    }

    public void playWifeSound(Player player) {
        if (!plugin.getConfigManager().isPluginEnabled()) return;

        VersionAdapter.playSound(player, "RANDOM_CHIME");

    }

    public void playInteractionSound(Player player) {
        if (!plugin.getConfigManager().isPluginEnabled()) return;
        VersionAdapter.playSound(player, "RANDOM_SUCCESS");
    }
}