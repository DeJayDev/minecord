package org.minecord.minecord.discord;


import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class ReadyClass implements ReadyCallback {
    @Override
    public void apply() {
        System.out.println("okie dokie");
    }
}
