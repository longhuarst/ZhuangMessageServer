package message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageHandler extends ChannelInboundHandlerAdapter {
	
	
	
	
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		
		System.out.println("channelActive");
	}
	
	
	
	
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
		
		System.out.println("channelInactive");
	}
	
	
	
	
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		super.channelRead(ctx, msg);
		
		System.out.println("channelRead");
	}
	
	
	
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
		

		
		System.out.println("channelReadComplete");
	}
	
	
	
	
	
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
		
		System.out.println("channelRegistered");
	}
	
	
	
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelUnregistered(ctx);
		
		
		System.out.println("channelUnregistered");
	}
	
	
	
	
	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelWritabilityChanged(ctx);
		
		System.out.println("channelWritabilityChanged");
	}
	
	
	
	
	
	
	
	
}





