package dhbw.kafkaStreams;

import java.awt.Event;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.BasicAuth;
import com.twitter.hbc.httpclient.auth.OAuth1;

/**
 * Hello world!
 *
 */
public class TwitterStreamConn 
{
    public static void main( String[] args ) throws InterruptedException
    {
        System.out.println( "Hello World!" );
        
        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
        BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        //STREAM_HOSTS : https://stream.twitter.com
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        // Optional: set up some followings and track terms
        List<Long> followings = Lists.newArrayList(1234L, 566788L);
        List<String> terms = Lists.newArrayList("twitter", "api");
        hosebirdEndpoint.followings(followings);
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1("G6gJYlHP3qxvT6lQT1UtzfAsm", "vr5FSQUdXOJHb2npjJE9xyxN4m0qMkIbmhiZGVdZK5wIeM8Obp", "3834719313-W1e7tddRJ35TgFaLlD8BueaczKNprK7U0QRCTna", "dbbUi2bFdXsniK9nQkEjciqU4cC4FH6ewZnyLEZaWRCvR");
     
        ClientBuilder builder = new ClientBuilder()
		  		  .name("Hosebird-Client-01")                              // optional: mainly for the logs
		  		  .hosts(hosebirdHosts)
		  		  .authentication(hosebirdAuth)
		  		  .endpoint(hosebirdEndpoint)
		  		  .processor(new StringDelimitedProcessor(msgQueue));    
	
	                     

		Client hosebirdClient = builder.build();
		// Attempts to establish a connection.
		hosebirdClient.connect();
		
		while (!hosebirdClient.isDone()) {
			  String msg = msgQueue.take();
			  System.out.println(msg);
			  
			}
    }
}
