package ChemX;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chemx.dao.ReimbDaoImpl;
import com.chemx.dao.ReimbursementDao;

public class ReimbursementTest {
	ReimbursementDao reimbDao = new ReimbDaoImpl();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void ReturnsUserList() {
		assertNull(reimbDao.getAllReimb() );
		
	}
	
	@Test
	public void getAllReimbByStatusTest() {
		assertNotNull(reimbDao.getAllReimbByStatus(1, 4) );
		
	}
	
	
	@Test 
	public void getAllReimbTest() {
		assertNotNull(reimbDao.getAllPendingReimb());
	}
	
	@Test
	public void getAllByUserTest() {
		assertNotNull(reimbDao.getAllReimbByUser(4));

	}



}
