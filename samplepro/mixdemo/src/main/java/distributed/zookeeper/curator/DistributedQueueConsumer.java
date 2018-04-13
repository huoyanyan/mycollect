package distributed.zookeeper.curator;

import java.nio.charset.Charset;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.framework.state.ConnectionState;

public class DistributedQueueConsumer {

	private DistributedQueue<String> queue;

	public DistributedQueueConsumer(CuratorFramework client, String queuePath) throws Exception {
		QueueBuilder<String> builder = QueueBuilder.builder(client, new StringQueueConsumer(),
				new StringQueueSerializer(), queuePath);
		queue = builder.lockPath("queue-lock").buildQueue();// 消费担保
	}

	public void start() throws Exception {
		queue.start();
	}

	public void close() throws Exception {
		queue.close();
	}

	public static DistributedQueue distributedQueueAsProducer(CuratorFramework client, String path) throws Exception {
		QueueBuilder<String> builder = QueueBuilder.builder(client, null, new StringQueueSerializer(), path);
		builder.maxItems(1024);// 有界队列,最大队列深度,如果深度达到此值,将阻塞"生产者"创建新的节点.
		return builder.buildQueue();
	}

	// utils for producer and consumer
	static class StringQueueSerializer implements QueueSerializer<String> {
		private static final Charset charset = Charset.forName("utf-8");

		// as producer
		@Override
		public byte[] serialize(String item) {
			return item.getBytes(charset);
		}

		// as consumer
		@Override
		public String deserialize(byte[] bytes) {
			return new String(bytes, charset);
		}
	}

	class StringQueueConsumer implements QueueConsumer<String> {
		static final String TAG = "_TAG";

		@Override
		public void consumeMessage(String message) throws Exception {
			System.out.println("Consumer:" + message);
			if (message.equals(TAG)) {
				System.out.println("Tag message...ignore it.");
			}
		}

		@Override
		public void stateChanged(CuratorFramework client, ConnectionState newState) {
			switch (newState) {
			case RECONNECTED:
				try {
					// 当链接重建之后,需要发送一个TAG消息,用于重新触发本地的watcher,以便获取新的children列表
					queue.put(TAG);
				} catch (Exception e) {
					//
				}
				break;
			default:
				System.out.println(newState.toString());
			}
		}
	}

}
