package io.github.eikefs.minecraft.lib.api.mapper;

import io.github.eikefs.minecraft.lib.ConfigFile;
import io.github.eikefs.minecraft.lib.ConfigValue;
import io.github.eikefs.minecraft.lib.api.parser.ConfigParser;
import io.github.eikefs.minecraft.lib.api.parser.Parsers;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ConfigMapper {

    @Deprecated
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void map(File folder, String pckg) {
        final Reflections reflections = new Reflections(pckg, new FieldAnnotationsScanner());
        final Set<Field> fields = reflections.getFieldsAnnotatedWith(ConfigValue.class);

        for (Field field : fields) {
            field.setAccessible(true);

            final ConfigValue configValue = field.getAnnotation(ConfigValue.class);
            final Class<?> declaringClass = field.getDeclaringClass();

            FileConfiguration configuration;

            if (declaringClass.isAnnotationPresent(ConfigFile.class)) {
                final ConfigFile configFile = declaringClass.getAnnotation(ConfigFile.class);

                configuration = getConfig(folder, configFile.value());
            } else {
                configuration = getConfig(folder, configValue.file());
            }

            if (configuration == null) continue;

            final Object value = configuration.get(configValue.value());
            final ConfigParser parser = Parsers.of(field.getType());

            try {
                if (field.getType().newInstance() instanceof List) {
                    List<Object> list = (List<Object>) value;
                    List<Object> valueList = new ArrayList<>();

                    for (Object listValue : list) {
                        if (listValue instanceof String) {
                            valueList.add(ChatColor.translateAlternateColorCodes('&', listValue.toString()));
                        } else {
                            valueList.add(listValue);
                        }
                    }

                    field.set(field, valueList);
                }

                if (parser == null) field.set(field, value);
                else field.set(field, parser.deserialize(value.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
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
