package message;

import java.util.concurrent.TimeUnit;

import com.sun.org.apache.bcel.internal.generic.NEW;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class Message {

	
	private int port; 
	
	public Message(int port) {
		// TODO Auto-generated constructor stub
		this.port = port;
	}
	
	
	public void run() throws Exception {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		
		try {
			
			ServerBootstrap bootstrap = new  ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel channel) throws Exception {
						// TODO Auto-generated method stub
						
						channel.pipeline().addLast(new IdleStateHandler(60, 80, 90, TimeUnit.SECONDS));
						//在原来的之前新增了两个解码器LineBasedFrameDecoder、StringDecoder
						channel.pipeline().addLast(new LineBasedFrameDecoder(1024));
						channel.pipeline().addLast(new StringDecoder());
						channel.pipeline().addLast(new MessageHandler());
					}
				})
				.option(ChannelOption.SO_BACKLOG, 128) //对应的TCP/IP协议listen函数中的backlog参数，用于初始化服务端的可连接队列  所以目前版本中最大连接数为128
				.childOption(ChannelOption.SO_KEEPALIVE, true);//对应套接字中的SO_KEEPALIVE，该参数用于设置TCP连接，设置该选项后，如果两小时内没有数据通信，TCP会自动发送一个活动探测数据报文
				
			//ChannelOption.SO_REUSEADDR  表示重复使用端口
			
			
			ChannelFuture future = bootstrap.bind(port).sync();
			
			
			future.channel().closeFuture().sync();
			
		} finally {
			// TODO: handle finally clause
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			
		}
		
	}
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		


		int port = 20000;
		
		try {
			new Message(port).run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
}
