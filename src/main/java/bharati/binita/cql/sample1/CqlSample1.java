package bharati.binita.cql.sample1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;

public class CqlSample1 {
	
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
	
	public static List<UUID> insertIntoUsers()
	{
		
		List<UUID> uuidList = new ArrayList<UUID>();
		
		// Connect to the cluster and keyspace "test1"
		cluster = Cluster.builder().addContactPoint(config.getProperty("cassandra.db.coordinator.instance")).build();
		session = cluster.connect(config.getProperty("cassandra.db.sample1.ks"));
		
		UUID uuid = UUIDs.random();
		ResultSet rs = session.execute
				("insert into users (user_id, name, email, phone, address, residency_status)"
						+ "	values" +
			"(" + 
						uuid+ 
						", 'Dora', "
						+ "'dora.mathew@gmail.com',"
						+ "['+91-9845168763','+91-877879067'],"
						+ "{'street' : '1st','lane' : 'Prasanth Layout', 'area' : 'Whitefield','city' : 'Bangalore', 'zip' : '560060'},"
						+ "'Indian')");
		
		uuidList.add(uuid);
		
		uuid = UUIDs.random();
				
		rs = session.execute
		("insert into users (user_id, name, email, phone, address, residency_status)"
				+ "	values" +
	"(" + 
		uuid + 
		", 'Blue', "
		+ "'blue.bob@gmail.com',"
		+ "['+91-77890989','+91-45578787'],"
		+ "{'street' : '65','lane' : 'Byrappa Reddy Layout', 'area' : 'Kadubessnahalli','city' : 'Bangalore', 'zip' : '560032'},"
		+ "'Indian')");
		
		uuidList.add(uuid);
		
		uuid = UUIDs.random();
				
		rs = session.execute
				("insert into users (user_id, name, email, phone, address, residency_status)"
						+ "	values" +
			"(" + 
						uuid + 
						", 'nemo', "
						+ "'nemo.mathew@gmail.com',"
						+ "['+91-88905663','+91-7878782345'],"
						+ "{'street' : '121','lane' : 'Blue Petals', 'area' : 'Sarjaput','city' : 'Bangalore', 'zip' : '560012'},"
						+ "'Non-Resident')");
		uuidList.add(uuid);
		
				
		cluster.close();
		
		return uuidList;
		
		
	}
	
	/**
	 * Eg of bad query!
	 * Using only secondary index in where clause! A strict no-no!. All the nodes will
	 * be queried to match the where clause.
	 */
	public static void readFromUsers1()
	{
		// Connect to the cluster and keyspace "test1"
		cluster = Cluster.builder().addContactPoint(config.getProperty("cassandra.db.coordinator.instance")).build();
		session = cluster.connect(config.getProperty("cassandra.db.sample1.ks"));
		
		ResultSet rs = session.execute("select * from users where residency_status ='Indian'");//bad bad query;
		for(Row each : rs)
		{
			System.out.println("readFromUsers: "+each.getUUID("user_id") + " " + each.getString("name"));
		}
		
	}
	
	/**
	 * Ok query.
	 * Using combination of partition_key and secondary index in where clause. Only the node where the partition resides will
	 * be queried to match the where clause.
	 */
	public static void readFromUsers2(List<String> uuidsList)
	{
		// Connect to the cluster and keyspace "test1"
		cluster = Cluster.builder().addContactPoint(config.getProperty("cassandra.db.coordinator.instance")).build();
		session = cluster.connect(config.getProperty("cassandra.db.sample1.ks"));
		
		for(String eachUUid : uuidsList)
		{
			ResultSet rs = session.execute("select * from users where user_id = " +
					UUID.fromString(eachUUid)+
					" and residency_status ='Indian'");//better query;
			for(Row each : rs)
			{
				System.out.println("readFromUsers2: "+each.getUUID("user_id") + " " + each.getString("name"));
			}
		}
		
		
		
	}

}
