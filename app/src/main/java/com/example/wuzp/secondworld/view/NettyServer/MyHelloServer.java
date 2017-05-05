package com.example.wuzp.secondworld.view.NettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by wuzp on 2017/5/3.
 */

public class MyHelloServer {
    private static final int PORT = 8090;

    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childrenGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup, childrenGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            //添加工作线程
            serverBootstrap.childHandler(new MyHelloServerInitializer());
            // 服务器绑定端口监听
            ChannelFuture cf = serverBootstrap.bind(PORT).sync();
            // 监听服务器关闭监听
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
            childrenGroup.shutdownGracefully();
        }
    }
}
