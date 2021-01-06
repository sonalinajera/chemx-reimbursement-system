package ChemX;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.chemx.dao.ReimbDaoImpl;
import com.chemx.dao.ReimbursementDao;
import com.chemx.model.Reimbursement;

public class ReimbTest {
	
	static ReimbDaoImpl reimbDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reimbDao = new ReimbDaoImpl("jdbc:h2:./testDBFolder;MODE=PostgreSQL","sa","sa");

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		reimbDao.h2InitDao();
	}

	@After
	public void tearDown() throws Exception {
		reimbDao.h2DestroyDao();
	}

	@Test
	public void test() {
		List<Reimbursement> reimbs = reimbDao.getAllReimb();
		assertEquals("should be true",true, reimbs.size() == 0);
		
	}


}
