package dhbw.config;
//GenConsConfig

import org.apache.log4j.Logger;

import dhbw.twitterConn.TwitterClient;
import dhbw.twitterConn.TwitterClientWrapper;



public class Config {
	private static Config instance = null;
	private TwitterClient twitterClient;
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
		}catch(Exception e){
			throw e;
		}
	}
	
	public TwitterClient getTwitterClient() {
		return twitterClient;
	}
}
