package com.todayWife;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TodayWife extends JavaPlugin {
    private static TodayWife instance;
    private WifeManager wifeManager;
    private CommandManager commandManager;
    private ConfigManager configManager;
    private SoundManager soundManager;

    @Override
    public void onEnable() {
        instance = this;

        // 初始化管理器awa
        configManager = new ConfigManager(this);
        wifeManager = new WifeManager(this);
        soundManager = new SoundManager(this);
        commandManager = new CommandManager(this);

        // 注册命令awa
        commandManager.registerCommands();

        getLogger().info("TodayWife 插件已启用! 作者: 蓝影");
    }

    @Override
    public void onDisable() {
        // 保存数据awa
        if (wifeManager != null) {
            wifeManager.saveAllData();
        }
        getLogger().info("TodayWife 插件已禁用");
    }

    public static TodayWife getInstance() {
        return instance;
    }

    public WifeManager getWifeManager() {
        return wifeManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
}