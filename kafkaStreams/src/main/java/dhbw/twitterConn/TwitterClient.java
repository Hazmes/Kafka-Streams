package dhbw.twitterConn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import org.apache.log4j.Logger;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.OAuth1;

import dhbw.config.Parameter;

public class TwitterClient {
	private static Logger log = Logger.getLogger(TwitterClient.class.getPackage().getName());
	private static final String NAME = "client.name";
	private static final String CONSUMER_KEY = "client.consumer.key";
	private static final String CONSUMER_SEC = "client.consumer.secret";
	private static final String TOKEN = "client.token";
	private static final String TOKEN_SEC = "client.token.secret";
	private static final String STREAM_HOST = "client.http.hosts";
	private List<Long> followings = new ArrayList<Long>();
	private List<String> terms = new ArrayList<String>();
	private String clientName;
	private String consumerKey;
	private String consumerSecret;
	private String token;
	private String tokenSecret;
	private String streamHost;
// a phrase will match if all of the terms in the phrase are present in the Tweet
		// Terms or Followings have to be set. If nothing is set, the connection gets
		// rejected (406)
	
	public BasicClient build(BlockingQueue<String> msgQueue) {
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
		if (followings.isEmpty()) {
			hosebirdEndpoint.trackTerms(terms);
			log.warn("followings empty");
		} else if (terms.isEmpty()) {
			hosebirdEndpoint.followings(followings);
			log.warn("terms empty");
		} else {
			hosebirdEndpoint.followings(followings);
			hosebirdEndpoint.trackTerms(terms);
		}
		ClientBuilder builder = new ClientBuilder().name(this.getClientName()) 
				.hosts(new HttpHosts(this.getStreamHost()))
				.authentication(new OAuth1(this.getConsumerKey(), this.getConsumerSecret(), this.getToken(),
								this.getTokenSecret()))
				.endpoint(hosebirdEndpoint).processor(new StringDelimitedProcessor(msgQueue));
		return builder.build();
	}

	public void setParameters(List<Parameter> parameters) {
		for (Parameter parameter : parameters) {
			switch (parameter.getKey()) {
			case NAME:
				this.setClientName(parameter.getValue());
				break;
			case CONSUMER_KEY:
				this.setConsumerKey(parameter.getValue());
				break;
			case CONSUMER_SEC:
				this.setConsumerSecret(parameter.getValue());
				break;
			case TOKEN:
				this.setToken(parameter.getValue());
				break;
			case TOKEN_SEC:
				this.setTokenSecret(parameter.getValue());
				break;
			case STREAM_HOST:
				this.setStreamHost(parameter.getValue());
				break;
			default:
				log.error("Could not load Properties Config into " + this.getClass().getName() + "\n Failed Property: " + parameter.getKey());
			}
		}
	}

	public List<Long> getFollowings() {
		return followings;
	}

	public void setFollowings(List<Long> followings) {
		this.followings = followings;
	}

	public List<String> getTerms() {
		return terms;
	}

	public void setTerms(List<String> terms) {
		this.terms = terms;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public String getStreamHost() {
		return streamHost;
	}

	public void setStreamHost(String streamHost) {
		this.streamHost = streamHost;
	}
}
