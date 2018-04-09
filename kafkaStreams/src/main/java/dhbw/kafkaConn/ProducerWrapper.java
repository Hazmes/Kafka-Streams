package dhbw.kafkaConn;

import java.util.LinkedList;

import dhbw.config.Parameter;

public class ProducerWrapper {
	private String name;
	private LinkedList<Parameter> parameters = new LinkedList<Parameter>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LinkedList<Parameter> getParameters() {
		return parameters;
	}
	public void setParameters(LinkedList<Parameter> parameters) {
		this.parameters = parameters;
	}
	
}
