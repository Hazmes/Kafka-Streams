package dhbw.config;

import org.apache.log4j.Logger;

import dhbw.kafkaConn.Producer;
import dhbw.kafkaConn.ProducerWrapper;
import dhbw.twitterConn.TwitterClient;
import dhbw.twitterConn.TwitterClientWrapper;

public class Config {
	private static Config instance = null;
	private TwitterClient twitterClient;
	private Producer kafkaProducer;
	public String name;
	public static Logger log = Logger.getLogger(Config.class.getPackage().getName());
	
	public static Config getInstance(){
		return instance;
	}
	
	public Config(){
		Config.instance = this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setTwitterClient (TwitterClientWrapper twitterClientWrapper) throws Exception{
		try {
			@SuppressWarnings("unchecked")
			Class<TwitterClient> twitterClientClass = (Class<TwitterClient>) Class.forName(twitterClientWrapper.getName());
			this.twitterClient = (TwitterClient) twitterClientClass.newInstance();
			this.twitterClient.setParameters(twitterClientWrapper.getParameters());
			this.twitterClient.setFollowings(twitterClientWrapper.getFollowings());
			this.twitterClient.setTerms(twitterClientWrapper.getTerms());
		}catch(Exception e){
			throw e;
		}
	}
	
	public void setProducer (ProducerWrapper producerWrapper) throws Exception{
		try {
			@SuppressWarnings("unchecked")
			Class<Producer> producerClass = (Class<Producer>) Class.forName(producerWrapper.getName());
			this.kafkaProducer = (Producer) producerClass.newInstance();
			this.kafkaProducer.setParameters(producerWrapper.getParameters());
			this.kafkaProducer.init();
		}catch(Exception e) {
			throw e;
		}
	}
	
	public Producer getProducer() {
		return kafkaProducer;
	}
	
	public TwitterClient getTwitterClient() {
		return twitterClient;
	}
	
	
}
