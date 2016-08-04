package db;

import java.util.Map.Entry;
import java.util.TreeMap;

import model.PPSCache;
import model.PPSMessage;
import server.clientserver.ServerMain;

/**
 * Wakes up after every POLLER_REFRESH_INTERVAL and polls the database for new PPS 
 * entries. Any updates found are 
 * @author tanoojp
 *
 */
public class ScheduledDatabasePoller implements Runnable{

	public static final Long POLLER_REFRESH_INTERVAL = 1000l;

	
	public void run() {
		while(true)
		{
			try {
				Thread.sleep(POLLER_REFRESH_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			poll();
		}
	}

	public static TreeMap<Double, Character> poll() {
		TreeMap<Double, Character> dbEntries = ServerMain.getInstance().getDbConnector().getAllEntries();
		
		System.out.println("\n\n-----New ouput-----");
		for(Entry<Double, Character> e: dbEntries.entrySet())
		{
			System.out.println(e.getKey() + " " + e.getValue());
		}
		
		PPSCache cache = ServerMain.getInstance().getCache();
		for(Entry<Double, Character> e: cache.getPps().entrySet())
		{
			dbEntries.put(e.getKey(), e.getValue());
		}
		PPSMessage m = new PPSMessage();
		m.setPps(dbEntries);
		return dbEntries;
		//ServerMain.getInstance().getMessageSender().sendMessageToClient(m);
	}
	
}