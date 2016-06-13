package server.masterserver;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientCountTracker {
	private static AtomicInteger clientCounter;
	private static ClientCountTracker instance;
	
	private ClientCountTracker() {
		clientCounter = new AtomicInteger(0);
	}
	
	public static ClientCountTracker getInstance()
	{
		if(instance==null)
		{
			instance = new ClientCountTracker();
		}
		return instance;
	}
	
	public int incrementAndGetClientCount()
	{
		return clientCounter.incrementAndGet();
	}
	
	public int getCurrentClientCount()
	{
		return clientCounter.get();
	}
	
	public int test()
	{
		int x = 5;
		if(x<10)
		{
			System.out.println(1);
		}
		else
		{
			System.out.println(2);
		}
		return x;
	}
	
}
