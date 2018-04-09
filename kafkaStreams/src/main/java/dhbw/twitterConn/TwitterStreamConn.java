package dhbw.twitterConn;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.hbc.core.Client;

import dhbw.config.Config;
import dhbw.kafkaConn.Producer;




public class TwitterStreamConn extends Thread
{
	private static Logger log = Logger.getLogger(TwitterStreamConn.class.getPackage().getName());
	private Client hosebirdClient;
	private Producer kafkaProducer;
	static BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);

	private void run(Config config) {
		hosebirdClient = config.getTwitterClient().build(msgQueue);
		hosebirdClient.connect();
		kafkaProducer =  config.getProducer();
		while (!hosebirdClient.isDone()) {
			String msg = null;
			try {
				msg = msgQueue.take();
			} catch (InterruptedException e) {
				log.error("Error when taking a Message out of the Queue", e);
			}
			 ObjectMapper mapper = new ObjectMapper();
			 try {
				JsonNode actualObj = mapper.readTree(msg);
				 ObjectNode object = (ObjectNode) actualObj;
				 object.remove("retweeted_status");
				 object.remove("extended_entities");
				 object.remove("quoted_status");
				
//				Iterator<String> jsonobjects = object.fieldNames();
//				while(jsonobjects.hasNext()) {
//					log.info(jsonobjects.next().toString());
//				}
				
				log.info("Cutted Tweet: " + object.toString());
				System.out.println("It was the best log in the world. It was the best log in the world. Look into the log and its easy to see that the code you wrote does not make sense to me. It was stupidity");
				//System.out.println("Did Something goood.");
				if(kafkaProducer != null) {
					kafkaProducer.putMessage(object.get("id").toString(), object.toString());
				}
						
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void start(Config config) {
		this.run(config);
	}
}
