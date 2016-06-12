package model;

import java.util.TreeMap;

public class PPSMessage implements Message {

	TreeMap<Double, Character> pps;
	
	public Class<? extends Message> getMessageType() {
		return this.getClass();
	}

	public Integer getMessageID() {
		// TODO Auto-generated method stub
		return null;
	}

	public TreeMap<Double, Character> getPps() {
		return pps;
	}

	public void setPps(TreeMap<Double, Character> pps) {
		this.pps = pps;
	}

}
