package dhbw.kafkaConn;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import dhbw.config.Parameter;

public class KafkaProducer {
	private static Logger log = Logger.getLogger(KafkaProducer.class.getPackage().getName());
	private static final String BOOTSTRAP_SERVERS = "bootstrap.server";
	private static final String KEY_SERIALIZER = "key.serializer";
	private static final String VALUE_SERIALIZER = "value.serializer";
	private static final String TOPIC = "topic";
	Properties props;
	
	public KafkaProducer() {
		props = new Properties();
	}
	
	public void setParameters(List<Parameter> params) {
		for(Parameter param : params) {
			switch(param.getKey()) {
			
			}
			if(param.getKey().equals(BOOTSTRAP_SERVERS)) {
				
			}
		}
	}
	
}
