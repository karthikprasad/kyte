import java.io.File;
import java.io.UnsupportedEncodingException;

import com.sleepycat.je.Cursor;
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
 
public class TestBDB {
 
    public static void main(String[] args) {
        
    	//Setup
    	Environment myDbEnvironment = null;
        Database myDatabase = null;
        ReplicatedEnvironment repEnv = null;
        
        try {
            // Open the environment, creating one if it does not exist
            EnvironmentConfig envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);
            envConfig.setTransactional(true);
            myDbEnvironment = new Environment(new File("src/main/resources/dbEnv"),
                                              envConfig);
 
            //Replication
            // Identify the node
            ReplicationConfig repConfig = new ReplicationConfig();
            repConfig.setGroupName("PlanetaryRepGroup");
            repConfig.setNodeName("Mercury");
            repConfig.setNodeHostPort("localhost:5001");

            // This is the first node, so its helper is itself
            repConfig.setHelperHosts("localhost:5001");

            repEnv =
                new ReplicatedEnvironment(new File("src/main/resources/repDbEnv"), repConfig, envConfig);	
            
            
            // Open the database, creating one if it does not exist
            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setAllowCreate(true);
            dbConfig.setTransactional(true);
            myDatabase = repEnv.openDatabase(null,
                                             "TestDatabase", dbConfig);
        } catch (DatabaseException dbe) {
            System.out.println(dbe);
        }
        
        
        //Insert
        String key = "myKey";
        String data = "myData";
         
        try {
            DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
            DatabaseEntry theData = new DatabaseEntry(data.getBytes("UTF-8"));
            myDatabase.put(null, theKey, theData);
            theData.setData("newData".getBytes("UTF-8"));
            DatabaseEntry newKey = new DatabaseEntry("newKey".getBytes("UTF-8"));
            myDatabase.put(null, newKey, theData);
        } catch (Exception e) {
        	System.out.println(e);
        }
        
        //Read
//        try {
//            // Create two DatabaseEntry instances:
//            // theKey is used to perform the search
//            // theData will hold the value associated to the key, if found
//            DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
//            DatabaseEntry theData = new DatabaseEntry();
//         
//            // Call get() to query the database
//            if (myDatabase.get(null, theKey, theData, LockMode.DEFAULT) ==
//                OperationStatus.SUCCESS) {
//         
//                // Translate theData into a String.
//                byte[] retData = theData.getData();
//                String foundData = new String(retData, "UTF-8");
//                System.out.println("m_key: '" + key + "' data: '" +
//                                    foundData + "'.");
//            } else {
//                System.out.println("m: No record found with key '" + key + "'.");
//            }
//        } catch (Exception e) {
//        	System.out.println(e);
//        }
//        System.out.println("Master sleeping");
//        try {
//			Thread.sleep(200000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        System.out.println("Master done sleeping");

        Cursor myCursor = null;
         
        try {
            myCursor = myDatabase.openCursor(null, null);
         
            // Cursors returns records as pairs of DatabaseEntry objects
            DatabaseEntry foundKey = new DatabaseEntry();
            DatabaseEntry foundData = new DatabaseEntry();
         
            // Retrieve records with calls to getNext() until the
            // return status is not OperationStatus.SUCCESS
            while (myCursor.getNext(foundKey, foundData, LockMode.DEFAULT) ==
                OperationStatus.SUCCESS) {
                String keyString;
				try {
					keyString = new String(foundKey.getData(), "UTF-8");
					String dataString = new String(foundData.getData(), "UTF-8");
					System.out.println("Key| Data : " + keyString + " | " +
							dataString + "");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        } catch (DatabaseException de) {
            System.err.println("Error reading from database: " + de);
        } finally {
            try {
                if (myCursor != null) {
                    myCursor.close();
                }
            } catch(DatabaseException dbe) {
                System.err.println("Error closing cursor: " + dbe.toString());
            }
        }
        myDatabase.close();
        repEnv.close();
    }
}