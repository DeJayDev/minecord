package org.minecord.minecord;

import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.io.IOException;

public class MinecordConfig {

    public static boolean allowToasts;

    public static void init(File location) {
        File newFile = new File(location + "/minecord" + "/Minecord_General.cfg");

        try{
            newFile.createNewFile();
        }catch (IOException e) {}

        Configuration config = new Configuration(newFile);

        config.load();

        allowToasts = config.get("General", "Allow Toasts", false).getBoolean(true);

        config.save();
    }
}
