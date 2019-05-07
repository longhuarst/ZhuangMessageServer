package message;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

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
						channel.pipeline().addLast(new MessageHandler());
					}
				})
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			
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
