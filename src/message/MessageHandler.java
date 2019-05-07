package message;

import java.util.Set;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MessageHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);

		// 用户连接了

		// Hub.getInstance().add(ctx, topic);

		System.out.println("channelActive");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);

		System.out.println("channelInactive");
		
		
		Hub.getInstance().remove(ctx);//用户断开链接了
		
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		// super.channelRead(ctx, msg);

		
		
		System.out.println("channelRead");

//		ByteBuf buf = (ByteBuf) msg;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
		String body = (String) msg;//new String(req, "UTF-8");

		System.out.println(ctx.channel().remoteAddress().toString());
		System.out.println("msg=[" + body + "]");
		
		if (body.startsWith("pub/")) {
			body = body.substring("pub/".length());
			
			int index = body.indexOf('/');
			
			if (index == -1) //无法检测
				return;

			String topic = body.substring(0, index);

			String message = body.substring(index + 1);
			
			if (message.endsWith("\r\n")) {
				message = message.substring(0,message.length()-1);
			}

			System.out.println("topic = [" + topic + "]");
			System.out.println("message = [" + message + "]");
			
			if (topic == "") 
				return; //话题为空
			
			if (message == "")
				return; //消息为空
			
			
			//发布消息
			Set<ChannelHandlerContext> sender = Hub.getInstance().get(topic);
			
			if (sender == null) {
				return;
			}
			
			ByteBuf respone = Unpooled.copiedBuffer(message.getBytes());
			
			for (ChannelHandlerContext client : sender){
				client.writeAndFlush(respone);
			}
			
		}else if (body.startsWith("sub/")){
			body = body.substring("sub/".length());
			
			//先移除
			
			Hub.getInstance().remove(ctx);

			//增加
			String topic = body;
			
			Hub.getInstance().add(ctx, topic);
			
			
		}else if (body.startsWith("list/topic/")){
			String lst = Hub.getInstance().topiclist();
			
			ByteBuf respone = Unpooled.copiedBuffer(lst.getBytes());
			
			ctx.writeAndFlush(respone);
		}else if (body.startsWith("list/context/")){
			String lst = Hub.getInstance().contextlist();
			
			ByteBuf respone = Unpooled.copiedBuffer(lst.getBytes());
			
			ctx.writeAndFlush(respone);
		}else{
			return;
		}

		
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
