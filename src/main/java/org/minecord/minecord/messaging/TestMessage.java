package org.minecord.minecord.messaging;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TestMessage implements IMessage {

    private String text;

    public TestMessage(){}

    public TestMessage(String text){
        this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        text = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf){
        ByteBufUtils.writeUTF8String(buf, text);
    }

    public static class Handler implements IMessageHandler<TestMessage, IMessage>{
        @Override
        public IMessage onMessage(TestMessage message, MessageContext ctx){
            System.out.println("Received " + message + " from server.");
            return null;
        }
    }
}
