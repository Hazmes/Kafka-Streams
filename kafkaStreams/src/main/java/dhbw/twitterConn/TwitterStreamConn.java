package dhbw.twitterConn;

import java.awt.Event;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;
import com.twitter.hbc.core.Client;

import dhbw.config.Config;
import dhbw.config.ConfigLoader;


public class TwitterStreamConn//extends ConfigLoader
{
	private static Logger log = Logger.getLogger(TwitterStreamConn.class.getPackage().getName());
    public static void main( String[] args ) throws InterruptedException
    {    
   
    	Config config = ConfigLoader.importGenConsConfig((args[0]));
    	if(config == null) {
    		log.error("config could not be loaded");
    	}

        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
        BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Client hosebirdClient = config.getTwitterClient().build(msgQueue);
        
       
		// Attempts to establish a connection.
		hosebirdClient.connect();
		
		while (!hosebirdClient.isDone()) {
			  String msg = msgQueue.take();
			  log.info(msg);
			  
			}
    }
}
