package dhbw.twitterConn;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;
import com.twitter.hbc.core.Client;

import dhbw.config.Config;


public class TwitterStreamConn// extends Thread
{
	private static Logger log = Logger.getLogger(TwitterStreamConn.class.getPackage().getName());
	private Client hosebirdClient;
	static BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);

	private void run(Config config) {
		hosebirdClient = config.getTwitterClient().build(msgQueue);
		hosebirdClient.connect();
		
		while (!hosebirdClient.isDone()) {
			String msg = null;
			try {
				msg = msgQueue.take();
			} catch (InterruptedException e) {
				log.error("Error when taking a Message out of the Queue", e);
			}
			log.info(msg);
		}
	}

	public void start(Config config) {
		this.run(config);
	}
}
