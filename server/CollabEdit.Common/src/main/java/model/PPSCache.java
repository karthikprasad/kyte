package model;

import java.util.TreeMap;

/**
 * This class contains a partial view of the PPS updates. These are updates 
 * that are sent from the client(browser) and forwarded to the Master Server but 
 * haven't been accepted by the Master Server yet.
 * @author tanoojp
 *
 */
public class PPSCache {

	private TreeMap<Double, Character> pps = new TreeMap<Double, Character>();

	public TreeMap<Double, Character> getPps() {
		return pps;
	}
	
}
