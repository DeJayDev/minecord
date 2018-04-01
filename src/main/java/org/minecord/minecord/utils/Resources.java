package org.minecord.minecord.utils;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Resource;

public enum Resources {

    GUI(new ResourceLocation("minecord:textures/gui/toasts.png")),
    CAPE_MOJANG(new ResourceLocation("minecord:texture/gui/cape_mojang.png")),
    CAPE_DEV(new ResourceLocation("minecord:texture/gui/cape_dev.png"));


    private ResourceLocation resource;

    Resources(ResourceLocation loc){
        this.resource = loc;
    }

    public ResourceLocation getResource(){
        return resource;
    }
}
