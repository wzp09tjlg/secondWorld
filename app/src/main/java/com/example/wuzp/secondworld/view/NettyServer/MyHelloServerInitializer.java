package com.example.wuzp.secondworld.view.NettyServer;

import java.nio.charset.Charset;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by wuzp on 2017/5/3.
 */
public class MyHelloServerInitializer extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)
            throws Exception {
        String recStr = new String(msg.getBytes(), Charset.forName("UTF-8"));
        //收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress()+" say :"+recStr);
        //返回客户端
        ctx.writeAndFlush("Received your message!\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RamoteAddress: "+ctx.channel().remoteAddress()+" active!");
//      ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + "'s service!\n");
        ctx.writeAndFlush("我是服务端，我是服务端！\n");
        super.channelActive(ctx);
    }
}
