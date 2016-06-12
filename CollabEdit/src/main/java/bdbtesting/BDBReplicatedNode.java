package bdbtesting;
import java.io.File;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.rep.ReplicatedEnvironment;
import com.sleepycat.je.rep.ReplicationConfig;

public class BDBReplicatedNode {
	public static void main(String[] args) {

		// Setup
		Environment myDbEnvironment = null;
		Database myDatabase = null;
		ReplicatedEnvironment repEnv = null;

		try {
			// Open the environment, creating one if it does not exist
			EnvironmentConfig envConfig = new EnvironmentConfig();
			envConfig.setAllowCreate(true);
			envConfig.setTransactional(true);
//			myDbEnvironment = new Environment(new File("src/main/resources/dbEnv"), envConfig);

			// Replication
			// Identify the node
			ReplicationConfig repConfig = new ReplicationConfig();
			repConfig.setGroupName("PlanetaryRepGroup");
			repConfig.setNodeName("Jupiter");
			repConfig.setNodeHostPort("192.168.0.12:5002");

			// This is the first node, so its helper is itself
			repConfig.setHelperHosts("192.168.0.17:5001");

			repEnv = new ReplicatedEnvironment(new File("src/main/resources/rep/repDbEnv"), repConfig,
					envConfig);

			// Open the database, creating one if it does not exist
			DatabaseConfig dbConfig = new DatabaseConfig();
			dbConfig.setAllowCreate(true);
			dbConfig.setTransactional(true);
			myDatabase = repEnv.openDatabase(null, "TestDatabase", dbConfig);
		} catch (DatabaseException dbe) {
			System.out.println(dbe);
		}

		// Insert
		String key = "myKey";
		String data = "myData";
//
//		try {
//			DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
//			DatabaseEntry theData = new DatabaseEntry(data.getBytes("UTF-8"));
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		System.out.println("Rep Node sleeping");
//		try {
//			Thread.sleep(50000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		System.out.println("Rep done sleeping");
		// Read
		try {
			// Create two DatabaseEntry instances:
			// theKey is used to perform the search
			// theData will hold the value associated to the key, if found
			DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();

			// Call get() to query the database
			if (myDatabase.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {

				// Translate theData into a String.
				byte[] retData = theData.getData();
				String foundData = new String(retData, "UTF-8");
				System.out.println("rep key: '" + key + "' data: '" + foundData + "'.");
			} else {
				System.out.println("rep No record found with key '" + key + "'.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Rep done sleeping");
		myDatabase.close();
        repEnv.close();
	}
}
