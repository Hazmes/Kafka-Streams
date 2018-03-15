package dhbw.manage;

import org.apache.log4j.Logger;

import dhbw.config.Config;
import dhbw.config.ConfigLoader;
import dhbw.twitterConn.TwitterStreamConn;

public class Manager {
	private static Logger log = Logger.getLogger(Manager.class.getPackage().getName());
	private TwitterStreamConn twitterStream;
	
	
	public Manager(String[] args){
		Config config = ConfigLoader.importGenConsConfig((args[0]));
		if(config == null) {
			log.error("config could not be loaded");
		}
		twitterStream = new TwitterStreamConn();
		twitterStream.start(config);
		
	}
	
	public static void main(String[] args) {
		new Manager(args);
	}
	

}
