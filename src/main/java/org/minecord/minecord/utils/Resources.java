package org.minecord.minecord.utils;

import net.minecraft.util.ResourceLocation;

public enum Resources {

    GUI(new ResourceLocation("minecord:textures/ui/gui.png")),
    CAPE_DEV(new ResourceLocation("minecord:textures/cosmetics/cape_dev.png"));


    private ResourceLocation resource;

    Resources(ResourceLocation loc){
        this.resource = loc;
    }

    public ResourceLocation getResource(){
        return resource;
    }
}
