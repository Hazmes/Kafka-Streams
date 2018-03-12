package dhbw.twitterConn;

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
        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
        BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        //STREAM_HOSTS : https://stream.twitter.com
        Hosts hosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        // Optional: set up some followings and track terms
        //followings: Twitter UserId and an L after it --> Why L?
        //My own Twitter ID: 3834719313L
        List<Long> followings = Lists.newArrayList(3834719313L);
        //a phrase will match if all of the terms in the phrase are present in the Tweet
        //Terms or Followings have to be set. If nothing is set, the connection gets rejected (406)
        List<String> terms = Lists.newArrayList("twitter", "api");
     

        // These secrets should be read from a config file
        Authentication auth = new OAuth1("G6gJYlHP3qxvT6lQT1UtzfAsm", "vr5FSQUdXOJHb2npjJE9xyxN4m0qMkIbmhiZGVdZK5wIeM8Obp", "3834719313-W1e7tddRJ35TgFaLlD8BueaczKNprK7U0QRCTna", "dbbUi2bFdXsniK9nQkEjciqU4cC4FH6ewZnyLEZaWRCvR");
     
        Client hosebirdClient = new TwitterClient().build("TestName", hosts, followings, terms, auth, msgQueue);
        
		// Attempts to establish a connection.
		hosebirdClient.connect();
		
		while (!hosebirdClient.isDone()) {
			  String msg = msgQueue.take();
			  System.out.println(msg);
			  
			}
    }
}
