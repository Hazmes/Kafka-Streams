package dhbw.twitterConn;

import java.util.LinkedList;

import dhbw.config.Parameter;

public class TwitterClientWrapper {
	private LinkedList<Parameter> parameters = new LinkedList<Parameter>();
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Parameter> getParameters() {
		return parameters;
	}
	
	public void setParameters(LinkedList<Parameter> parameters) throws Exception {
		this.parameters = parameters;
	}
}
