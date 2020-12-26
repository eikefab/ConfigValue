package io.github.eikefs.minecraft.lib.api;

import io.github.eikefs.minecraft.lib.api.mapper.ConfigMapper;

import java.io.File;

@Deprecated
public class ConfigLoader {

    private final File folder;

    public ConfigLoader(File folder) {
        this.folder = folder;
    }

    public File getFolder() {
        return folder;
    }

    public ConfigLoader map(Class<?> clazz) {
        ConfigMapper.map(getFolder(), clazz);

        return this;
    }

}
