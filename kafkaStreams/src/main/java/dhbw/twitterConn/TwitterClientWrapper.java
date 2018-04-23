package dhbw.twitterConn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dhbw.config.Parameter;

public class TwitterClientWrapper {
	private LinkedList<Parameter> parameters = new LinkedList<Parameter>();
	private List<Long> followings = new ArrayList<Long>();
	private List<String> terms = new ArrayList<String>();
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public LinkedList<Parameter> getParameters() {
		return parameters;
	}
	
	public void setParameters(LinkedList<Parameter> parameters) throws Exception {
		this.parameters = parameters;
	}
}
