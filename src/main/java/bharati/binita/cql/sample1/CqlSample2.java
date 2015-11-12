package bharati.binita.cql.sample1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CqlSample2 {
	
	private static Session session;
	private static Cluster cluster;
	
	private static Properties config;
	
	static
	{
		config = new Properties();
		try {
			config.load(CqlSample1.class.getClassLoader().getResourceAsStream("cql.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<UUID> insertIntoEmployees()
	{
		List<UUID> uuidList = new ArrayList<UUID>();
		
		// Connect to the cluster and keyspace "test1"
		cluster = Cluster.builder().addContactPoint(config.getProperty("cassandra.db.coordinator.instance")).build();
		session = cluster.connect(config.getProperty("cassandra.db.sample1.ks"));
		
		
		
		return uuidList;
	}
	
	
	

}
