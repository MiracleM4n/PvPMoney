package com.miraclem4n.pvpmoney;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

import java.io.File;
import java.io.IOException;

public class ConfigFile {
    PvPMoney plugin;
    YamlConfiguration config;
    YamlConfigurationOptions configO;
    File configF;
    Boolean hasChanged = false;

    public ConfigFile(PvPMoney plugin) {
        this.plugin = plugin;

        configF = new File("plugins/PvPMoney/config.yml");
        config = YamlConfiguration.loadConfiguration(configF);
        configO = config.options();

        configO.indent(4);
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(configF);
        config.options().indent(4);
    }

    public void save() {
        try {
            config.save(configF);
        } catch (IOException ignored) {}
    }

    public void load() {
        checkConfig();

        plugin.takeMoney = config.getBoolean("take.money", plugin.takeMoney);
        plugin.moneyAmount = config.getDouble("amount.money", plugin.moneyAmount);
        plugin.scoreAmount = config.getDouble("amount.score", plugin.scoreAmount);
        plugin.takenMessage = config.getString("message.taken", plugin.takenMessage);
        plugin.givenMessage = config.getString("message.given", plugin.givenMessage);
        plugin.scoreMessage = config.getString("message.score", plugin.scoreMessage);
    }

    void defaultConfig() {
        configO.header("PvPMoney Configuration File");

        config.set("take.money", plugin.takeMoney);
        config.set("amount.money", plugin.moneyAmount);
        config.set("amount.score", plugin.scoreAmount);
        config.set("message.taken", plugin.takenMessage);
        config.set("message.given", plugin.givenMessage);
        config.set("message.score", plugin.scoreMessage);

        save();
    }

    void checkConfig() {
        if (!(configF.exists()))
            defaultConfig();

        checkOption(config, "take.money", plugin.takeMoney);
        checkOption(config, "amount.money", plugin.moneyAmount);
        checkOption(config, "amount.score", plugin.scoreAmount);
        checkOption(config, "message.taken", plugin.takenMessage);
        checkOption(config, "message.given", plugin.givenMessage);
        checkOption(config, "message.score", plugin.scoreMessage);
        
        if (hasChanged) {
            configO.header("PvPMoney Configuration File");

            save();
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    void checkOption(YamlConfiguration config, String option, Object defValue) {
        if (!config.isSet(option)) {
            config.set(option, defValue);
            hasChanged = true;
        }
    }
}
