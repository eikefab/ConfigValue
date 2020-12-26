package io.github.eikefs.minecraft.lib.api.mapper;

import io.github.eikefs.minecraft.lib.ConfigFile;
import io.github.eikefs.minecraft.lib.ConfigValue;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;

public final class ConfigMapper {

    public static void map(File folder, Class<?> clazz) {
        String fileName =
                clazz.isAnnotationPresent(ConfigFile.class) ?
                clazz.getAnnotation(ConfigFile.class).value() :
                "config.yml";

        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(ConfigValue.class)) {
                    field.setAccessible(true);

                    ConfigValue configValue = field.getAnnotation(ConfigValue.class);

                    FileConfiguration configuration;

                    if (configValue.file().equals("configFile")) {
                        configuration = getConfig(folder, fileName);
                    } else {
                        configuration = getConfig(folder, configValue.file());
                    }

                    if (configuration == null) continue;

                    Object value = configuration.get(configValue.value());

                    field.set(field, value);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    private static FileConfiguration getConfig(File folder, String name) {
        try {
            return YamlConfiguration.loadConfiguration(new File(folder, name));
        } catch (Exception exception) {
            return null;
        }
    }

}
