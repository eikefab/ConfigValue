package io.github.eikefs.minecraft.lib;

// import io.github.eikefs.minecraft.lib.api.ConfigLoader;
import io.github.eikefs.minecraft.lib.api.mapper.ConfigMapper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class ConfigValuePlugin extends JavaPlugin {

    @ConfigValue("hello-world") private static String helloWorld;
    @ConfigValue("a-random-number") private static int randomNumber;
    @ConfigValue("some-list") private static List<String> someList;
    @ConfigValue("a-section") private static ConfigurationSection section;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ConfigMapper.map(getDataFolder(), "io.github.eikefs.minecraft.lib");

        System.out.println("1: " + helloWorld);
        System.out.println("2: " + randomNumber);

        for (String content : someList) {
            System.out.println("3: [List Content] " + content);
        }

        String sectionHello = section.getString("hello");
        String sectionWorld = section.getString("world");
        String sectionMessage = section.getString("messages.hello-world");

        System.out.println("4: [Section Content] " + sectionHello);
        System.out.println("5: [Section Content] " + sectionWorld);
        System.out.println("6: [Section Content] " + sectionMessage);
    }

}
