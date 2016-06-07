package db;
import java.io.File;
import java.io.UnsupportedEncodingException;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.rep.ReplicatedEnvironment;
import com.sleepycat.je.rep.ReplicationConfig;

public class PPSDBConnector {

	Database db = null;
	ReplicatedEnvironment repEnv = null;

	public PPSDBConnector(File envHome, String groupName, String nodeName,
			String nodeHostPort, String helperHostPort)
	{
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        envConfig.setTransactional(true);
		
        // Identify the node
        ReplicationConfig repConfig = new ReplicationConfig();
        repConfig.setGroupName(groupName);
        repConfig.setNodeName(nodeName);
        repConfig.setNodeHostPort(nodeHostPort);

        // This is the first node, so its helper is itself
        repConfig.setHelperHosts(helperHostPort);
		
        repEnv = new ReplicatedEnvironment(envHome, repConfig, envConfig);	
        
		// Open the database, creating one if it does not exist
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		dbConfig.setTransactional(true);
		db = this.repEnv.openDatabase(null, "PPSDatabase", dbConfig);
	}
	
	public void write(Double key, Character value) throws UnsupportedEncodingException
	{
		DatabaseEntry theKey = new DatabaseEntry(key.toString().getBytes("UTF-8"));
		DatabaseEntry theData = new DatabaseEntry(value.toString().getBytes("UTF-8"));
        db.put(null, theKey, theData);
	}
	
	public void delete(Double key) throws UnsupportedEncodingException
	{
		DatabaseEntry theKey = new DatabaseEntry(key.toString().getBytes("UTF-8"));
		DatabaseEntry theData = new DatabaseEntry("\0".getBytes("UTF-8"));
        db.put(null, theKey, theData);
	}

	/**
	 * Returns the value associated with this key, returns null if there is no 
	 * such key present.
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Character read(Double key) throws UnsupportedEncodingException
	{
      // Create two DatabaseEntry instances:
      // theKey is used to perform the search
      // theData will hold the value associated to the key, if found
      DatabaseEntry theKey = new DatabaseEntry(key.toString().getBytes("UTF-8"));
      DatabaseEntry theData = new DatabaseEntry();
      Character c = null;
      
      // Call get() to query the database
      if (db.get(null, theKey, theData, LockMode.DEFAULT) ==
          OperationStatus.SUCCESS) {
   
          // Translate theData into a String.
          byte[] retData = theData.getData();
          c = (char) retData[0];
      }
      return c;
	}
	
	public void closeDB()
	{
        db.close();
        repEnv.close();
	}
}
