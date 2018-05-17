package dhbw.twitterConn;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.hbc.core.Client;

import dhbw.config.Config;
import dhbw.kafkaConn.Producer;

public class TwitterStreamConn extends Thread {
	private static Logger log = Logger.getLogger(TwitterStreamConn.class.getPackage().getName());
	private Client hosebirdClient;
	private Producer kafkaProducer;
	static BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
	private int totalMsgCount = 0;
	private int msgsInfoStepSize;

	private void run(Config config) {
		hosebirdClient = config.getTwitterClient().build(msgQueue);
		hosebirdClient.connect();
		kafkaProducer = config.getProducer();
		List<String> bigPayloadFields = Arrays.asList("retweeted_status", "extended_entities", "quoted_status");
		msgsInfoStepSize = 100;

		while (!hosebirdClient.isDone()) {
			String msg = null;
			try {
				msg = msgQueue.take();
			} catch (InterruptedException e) {
				log.error("Error when taking a Message out of the Queue", e);
			}
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode object = new ObjectNode(null);
			try {

				object = (ObjectNode) mapper.readTree(msg);

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(object != null) {
				if (object.get("created_at") == null || object.get("id") == null) {
					continue;
				}
				
				log.debug("Original Tweet " + object.toString());
				
				object.remove(bigPayloadFields);
				
				log.debug("Cutted Tweet: " + object.toString());
				
				// Iterator<String> jsonobjects = object.fieldNames();
				// while(jsonobjects.hasNext()) {
				// log.info(jsonobjects.next().toString());
				// }
				
				if (kafkaProducer != null) {
					kafkaProducer.putMessage(object.get("id").toString(), object.toString());
					totalMsgCount++;
				}
			}
			if (totalMsgCount % msgsInfoStepSize == 0) {
				log.info("Send another " + msgsInfoStepSize + " with a total of " + totalMsgCount + " Messages send.");
			}
		}
	}

	public void start(Config config) {
		this.run(config);
	}

}
