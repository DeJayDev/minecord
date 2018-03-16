package org.minecord.minecord.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class ConfigHandler {

    private File configFile;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private GeneralConfig general;
    private OfflinePresenceConfig offlinePresence;

    public ConfigHandler(File f){
        this.configFile = f;
        deserialize();
    }

    public void serialize(){
        JsonObject config = new JsonObject();

        config.add("general", new JsonParser().parse(gson.toJson(general)).getAsJsonObject());
        config.add("offline", new JsonParser().parse(gson.toJson(offlinePresence)).getAsJsonObject());

        try{
            if(!configFile.exists())
                configFile.createNewFile();
            FileUtils.writeStringToFile(configFile, config.toString(), Charset.forName("UTF-8"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void deserialize(){
        if(!configFile.exists()) {
            general = new GeneralConfig();
            offlinePresence = new OfflinePresenceConfig();
            System.out.println("No config file found, using default bindings.");
            return;
        }

        try {
            JsonObject config = new JsonParser().parse(FileUtils.readFileToString(configFile, Charset.forName("UTF-8"))).getAsJsonObject();
            general = gson.fromJson(config.get("general"), GeneralConfig.class);
            offlinePresence = gson.fromJson(config.get("offline"), OfflinePresenceConfig.class);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    public GeneralConfig getGeneral() {
        return general;
    }

    public OfflinePresenceConfig getOfflinePresence() {
        return offlinePresence;
    }
}
