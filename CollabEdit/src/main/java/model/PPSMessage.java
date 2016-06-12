package model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

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
	
	public String toString() {
		JSONObject json_obj=new JSONObject();
        for (Entry<Double, Character> entry : pps.entrySet()) {
            Double key = entry.getKey();
            Character value = entry.getValue();
            try {
                json_obj.put(key.toString(), value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json_obj.toString();
	}

}
