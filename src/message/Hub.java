package message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.netty.channel.ChannelHandlerContext;

public class Hub {

	private static class HubHolder {
		private static Hub instance = new Hub();
	}

	private Hub() {
		TopicMap = new HashMap<String, Set<ChannelHandlerContext>>();
		ContextMap = new HashMap<ChannelHandlerContext, String>();
		// System.out.println("构造");
	}

	public static Hub getInstance() {
		return HubHolder.instance;
	}

	// =======================================

	// 话题 - 订阅者映射
	Map<String, Set<ChannelHandlerContext>> TopicMap;
	Map<ChannelHandlerContext, String> ContextMap;

	// 订阅者删除
	void remove(ChannelHandlerContext ctx) {
		String topic = ContextMap.get(ctx);// 获取话题
		ContextMap.remove(ctx);

		if (topic != null) {
			// 有话题订阅的
			Set<ChannelHandlerContext> set = TopicMap.get(topic);
			set.remove(ctx);

			// 检测是不是空了

			if (set.isEmpty()) {
				TopicMap.remove(topic);// 没有订阅者了，把话题删除
			} else {
				TopicMap.put(topic, set);
			}

		}
	}

	// 订阅者加入
	void add(ChannelHandlerContext ctx, String topic) {
		Set<ChannelHandlerContext> set = TopicMap.get(topic);
		if (set == null) {
			set = new HashSet<ChannelHandlerContext>();
		}
		set.add(ctx);
		TopicMap.put(topic, set);

		ContextMap.put(ctx, topic);
	}

	// 获取转发对象

	Set<ChannelHandlerContext> get(String topic) {
		return TopicMap.get(topic);
	}

	// 列表
	public String topiclist() {
		String data = "";

		data += "{\r\n";

		for (String key : TopicMap.keySet()) {
			data += "\t" + key + " : { ";
			for (ChannelHandlerContext ctx : TopicMap.get(key)) {
				data += ctx.channel().remoteAddress().toString() + ", ";
			}
			data += "},\r\n";
		}

		data += "};";

		return data;
	}
	
	
	// 列表
		public String contextlist() {
			String data = "";

			data += "{\r\n";

			for (ChannelHandlerContext ctx : ContextMap.keySet()) {
				data += "\t" + ctx.channel().remoteAddress().toString() + " : { ";
				data += ContextMap.get(ctx) + ", ";
				data += "},\r\n";
			}

			data += "};";

			return data;
		}
		
		

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
