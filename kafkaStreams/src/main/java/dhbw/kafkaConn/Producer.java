package dhbw.kafkaConn;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

import dhbw.config.Parameter;

public class KafkaProducer {
	private static Logger log = Logger.getLogger(KafkaProducer.class.getPackage().getName());
	private static final String BOOTSTRAP_SERVERS = "bootstrap.server";
	private static final String KEY_SERIALIZER = "key.serializer";
	private static final String VALUE_SERIALIZER = "value.serializer";
	private static final String TOPIC = "topic";
	private KafkaProducer<String,String> producer;
	Properties props;
	
	public KafkaProducer() {
		props = new Properties();
	}
	
	public void putMessage() {
		
	}
	
	public void setParameters(List<Parameter> params) {
		for(Parameter param : params) {
			switch(param.getKey()) {
			case BOOTSTRAP_SERVERS:
				props.put(BOOTSTRAP_SERVERS, param.getValue());
				break;
			case KEY_SERIALIZER:
				props.put(KEY_SERIALIZER, param.getValue());
				break;
			case VALUE_SERIALIZER:
				props.put(VALUE_SERIALIZER, param.getValue());
				break;
			case TOPIC:
				props.put(TOPIC, param.getValue());
				break;
			default:
				log.error("Could not load Config Properties into " + this.getClass().getName() + "\n Failed Property: " + param.getKey());
			}
		}
	}
	
	public void close() throws Exception {
		producer.close();
	}
	
}
