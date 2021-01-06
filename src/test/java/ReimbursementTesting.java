import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chemx.dao.ReimbDaoImpl;
import com.chemx.dao.ReimbursementDao;
import com.chemx.dao.UserDao;
import com.chemx.dao.UserDaoImpl;
import com.chemx.model.Reimbursement;

public class ReimbursementTesting {
	ReimbursementDao reimbDao= new ReimbDaoImpl();
	UserDao userDao = new UserDaoImpl();


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
	//read
	
	@Test
	public void testGetAllPendingReimb() {
//		List<Reimbursement> reimbursementList = reimbDao.getAllPendingReimb();
//		assertFalse(reimbursementList.isEmpty());
	}

}
