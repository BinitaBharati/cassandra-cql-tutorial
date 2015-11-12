package bharati.binita.test.cql.sample1;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bharati.binita.cql.sample1.CqlSample1;
import junit.framework.TestCase;

public class CqlSample1Test extends TestCase {
	
	public void testSample1Cql()
	{
		
		
		/*List<UUID> uuidList = CqlSample1.insertIntoUsers();
		System.out.println("UUIDList = "+uuidList);*/
		
		List<String> uuidList = new ArrayList<String>();
		uuidList.add("c4bb34a2-c4fe-4728-a0ac-11037a50ef5f");
		uuidList.add("30c4c58d-8609-426b-8eb5-cbec0442ede6");
		
		//CqlSample1.readFromUsers1();
		
		CqlSample1.readFromUsers2(uuidList);
		
		
	}

}
