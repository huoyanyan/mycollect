package distributed.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;

import distributed.zookeeper.curator.DistributedQueueConsumer.StringQueueSerializer;

public class DistributedQueueProducer {

	private DistributedQueue<String> queue;

	public DistributedQueueProducer(CuratorFramework client, String queuePath) throws Exception {
		QueueBuilder<String> builder = QueueBuilder.builder(client, null, new StringQueueSerializer(), queuePath);
		queue = builder.lockPath("queue-lock").buildQueue();// 消费担保
	}

	public void start() throws Exception {
		queue.start();
	}

	public void close() throws Exception {
		queue.close();
	}

	public void put(String message) throws Exception {
		queue.put(message);
	}

}
