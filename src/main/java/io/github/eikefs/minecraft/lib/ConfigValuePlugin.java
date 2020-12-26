package io.github.eikefs.minecraft.lib;

import io.github.eikefs.minecraft.lib.api.mapper.ConfigMapper;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConfigValuePlugin extends JavaPlugin {

    @ConfigValue("hello-world") private static String helloWorld;
    @ConfigValue("a-random-number") private static int randomNumber;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ConfigMapper.map(getDataFolder(), "io.github.eikefs.minecraft.lib");

        System.out.println("1: " + helloWorld);
        System.out.println("2: " + randomNumber);
    }

}
