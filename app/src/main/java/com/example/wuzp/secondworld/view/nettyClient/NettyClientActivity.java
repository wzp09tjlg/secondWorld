package com.example.wuzp.secondworld.view.nettyClient;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.example.wuzp.secondworld.R;
import com.example.wuzp.secondworld.databinding.ActivityNettyClientBinding;
import com.example.wuzp.secondworld.view.base.BindingActivity;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by wuzp on 2017/5/3.
 */
public class NettyClientActivity extends BindingActivity<ActivityNettyClientBinding,NettyPresenter> implements NettyContract.IView {
    private NettyViewWrapper viewWrapper = new NettyViewWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewWrapper.addBinding(binding);

        LogUtils.e("----------------------------------------------------------------");
        context = this;
        connected();
        sendButton = (Button) findViewById(R.id.btn_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    @Override
    protected NettyPresenter createPresenter() {
        return new NettyPresenter(this);
    }

    @Override
    protected int createLayoutId() {
        return R.layout.activity_netty_client;
    }

   /***********************************************************/
   private static final String TAG = "MainActivity";
    private static Context context;
    public static int MSG_REC=0xabc;
    public static int PORT = 8090;
    public static final String HOST = "192.168.155.1";
    private NioEventLoopGroup group;
    private Button sendButton;
    private Channel mChannel;
    private ChannelFuture cf;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==MSG_REC){
                Toast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };


    // 连接到Socket服务端
    private void connected() {
        new Thread() {
            @Override
            public void run() {
                group = new NioEventLoopGroup();
                try {
                    // Client服务启动器 3.x的ClientBootstrap
                    // 改为Bootstrap，且构造函数变化很大，这里用无参构造。
                    Bootstrap bootstrap = new Bootstrap();
                    // 指定EventLoopGroup
                    bootstrap.group(group);
                    // 指定channel类型
                    bootstrap.channel(NioSocketChannel.class);
                    // 指定Handler
                    bootstrap.handler(new MyClientHandler(context,mHandler));
                    bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
                    bootstrap.option(ChannelOption.TCP_NODELAY, true);
                    bootstrap.option(ChannelOption.SO_TIMEOUT, 5000);
                    // 连接到本地的PORT端口的服务端
                    cf = bootstrap.connect(new InetSocketAddress(
                            HOST, PORT));
                    mChannel = cf.sync().channel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //发送数据
    private void sendMessage() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG, "mChannel.write sth & " + mChannel.isOpen());
                    mChannel.writeAndFlush("hello,this message is from client.\r\n");
                    mChannel.read();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (group != null) {
            group.shutdownGracefully();
        }
    }
}
