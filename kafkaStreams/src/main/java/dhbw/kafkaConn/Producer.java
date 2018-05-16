package dhbw.kafkaConn;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

import dhbw.config.Parameter;

public class Producer {
	private static Logger log = Logger.getLogger(Producer.class.getPackage().getName());
	private static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
	private static final String KEY_SERIALIZER = "key.serializer";
	private static final String VALUE_SERIALIZER = "value.serializer";
	private static final String TOPIC = "topic";
	private String topic;
	private KafkaProducer<String,String> producer;
	Properties props;
	
	public Producer() {
		props = new Properties();
	}
	
	public void putMessage(String key, String message) {
		producer.send(new ProducerRecord<String,String>(this.getTopic(),key,message));
		log.debug("Twitter Message Object "+ key + "send to Kafka");
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
				this.setTopic(param.getValue());
				break;
			default:
				log.error("Could not load Config Properties into " + this.getClass().getName() + "\n Failed Property: " + param.getKey());
			}
		}
	}
	
	public void init() {
		log.info("//Creating Producer\\");
		producer = new KafkaProducer<String,String>(props);
		log.info("//Created Producer\\");
	}
	
	public void close() throws Exception {
		producer.close();
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
}
