package dhbw.twitterConn;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;

import dhbw.config.Parameter;

public class TwitterClient {

	private static final String FOLLOWINGS = "followings";
	private List<Long> followings;
	
	

	public BasicClient build(String name, Hosts connectionHosts, List<Long> followings, List<String> terms, Authentication hosebirdAuth, BlockingQueue<String> msgQueue) {
		 StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
		  hosebirdEndpoint.followings(followings);
	      hosebirdEndpoint.trackTerms(terms);
		
		ClientBuilder builder = new ClientBuilder()
				.name("Hosebird-Client-01")                              // optional: mainly for the logs
				.hosts(connectionHosts)
				.authentication(hosebirdAuth)
				.endpoint(hosebirdEndpoint)
				.processor(new StringDelimitedProcessor(msgQueue));    
		
		return builder.build();
			
	}
	
	public void setParameters(List<Parameter> parameters) {
	for (Parameter parameter : parameters) {
		if (FOLLOWINGS.equals(parameter.getKey())) {
	//		this.followings =
//					parameter.getValue();
//			props.put(BOOTSTRAP_SERVERS_CONFIG, parameter.getValue());
//		} else if (PARTITION_ASSIGNMENT_STRATEGY.equals(parameter.getKey())) {
//			props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, parameter.getValue());
//		} else if (KEY_DESERIALIZER.equals(parameter.getKey())) {
//			props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, parameter.getValue());
//		} else if(AUTO_OFFSET_RESET.equals(parameter.getKey())){
//			props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, parameter.getValue());
//		} else if (VALUE_DESERIALIZER.equals(parameter.getKey())) {
//			props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, parameter.getValue());
//		} else if (GROUP_ID.equals(parameter.getKey())) {
//			props.put(ConsumerConfig.GROUP_ID_CONFIG, parameter.getValue());
//		} else if (TOPIC.equals(parameter.getKey())) {
//			this.topic = parameter.getValue();
//		} else {
//			log.warn("Invalid kafka consumer parameter " + parameter.getKey());
		}
	}
}
	
}
