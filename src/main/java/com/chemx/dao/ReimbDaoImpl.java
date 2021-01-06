 package com.chemx.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.chemx.model.Reimbursement;

public class ReimbDaoImpl implements ReimbursementDao {

	private static String DB_URL = "jdbc:postgresql://" + System.getenv("TRAINING_DB_URL") + "/"
			+ System.getenv("TRAINING_DB_CHEMX");
	private static String DB_USERNAME = System.getenv("TRAINING_DB_USERNAME");
	private static String DB_PASSWORD = System.getenv("TRAINING_DB_MACPASS");
	
	public ReimbDaoImpl() {
	}
	
	public ReimbDaoImpl(String url, String username, String password) {
		super();
		this.DB_URL = url;
		this.DB_USERNAME = username;
		this.DB_PASSWORD = password;
	}


	@Override
	public void addNewReimb(double reimb_amount, int reimb_author, int reimb_type_id, String description) {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

			String sql = "insert into chemx_reimbursements (reimb_amount,reimb_author, reimb_status_id, reimb_type_id, reimb_description)"
					+ "values (?,?,1,?,?)";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, reimb_amount);
			ps.setInt(2, reimb_author);
			ps.setInt(3, reimb_type_id);
			ps.setString(4, description);

			int successIndicator = ps.executeUpdate();
			System.out.println(successIndicator);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Reimbursement> getAllReimb() {
		return null;
	}

// TODO need to add resolver and date resolved
	@Override
	public List<Reimbursement> getAllReimbByStatus(int statusID, int userID) {
		List<Reimbursement> userReimbursements = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

			String sql = "select cr.reimb_id , cr.reimb_amount , cr.reimb_description , cr.reimb_submitted, crt.reimb_type, cr.reimb_status_id, crs.reimb_status "
					+ "	, cu.first_name , cu.last_name , cu.username , cu.\"password\" , cu.email, cur.role_title "
					+ "	, cu.user_id , cr.reimb_resolved, cr.reimb_description, cu2.first_name as resolver_first "
					+ "	, cu2.last_name as resolver_last " + "from chemx_reimbursements cr "
					+ "left join chemx_reimbursement_type crt " + "on cr.reimb_type_id = crt.reimb_type_id "
					+ "left join chemx_reimbursement_status crs " + "on cr.reimb_status_id = crs.reimb_status_id "
					+ "inner join chemx_users cu " + "on cr.reimb_author = cu.user_id " + "left join chemx_users cu2 "
					+ "on cr.reimb_resolver = cu2.user_id " + "left join chemx_user_roles cur "
					+ "on cu.user_role_id = cur.role_id " + "where cr.reimb_status_id = ?" + "and cr.reimb_author = ?"
					+ "order by cr.reimb_submitted asc";
//					"select cr.reimb_id , cr.reimb_amount , cr.reimb_description , cr.reimb_submitted, crt.reimb_type, cr.reimb_status_id, crs.reimb_status "
//					+ "	, cu.first_name , cu.last_name , cu.username , cu.\"password\" , cu.email, cur.role_title "
//					+ "	, cu.user_id , cr.reimb_resolved, cr.reimb_description, cu2.first_name as resolver_first "
//					+ "	, cu2.last_name as resolver_last " + "from chemx_reimbursements cr "
//					+ "left join chemx_reimbursement_type crt " + "on cr.reimb_type_id = crt.reimb_type_id "
//					+ "left join chemx_reimbursement_status crs " + "on cr.reimb_status_id = crs.reimb_status_id "
//					+ "inner join chemx_users cu " + "on cr.reimb_author = cu.user_id " + "left join chemx_users cu2 "
//					+ "on cr.reimb_resolver = cu2.user_id " + "left join chemx_user_roles cur "
//					+ "on cu.user_role_id = cur.role_id " + "where cr.reimb_status_id = ? "
//					+ "order by cr.reimb_submitted asc";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, statusID);
			ps.setInt(2, userID);
			ResultSet rs = ps.executeQuery();

//			int userId = 0;
			String firstName = null;
			String lastName = null;

			int reimbId = 0;
			double reimbAmount = 0;
			String dateSubmitted = null;
			String dateResolved = null;
			String description = null;
			String author = null;
			String resolver = null;
			String status = null;
			String type = null;

			while (rs.next()) {
//				userId = rs.getInt("user_id");
				firstName = rs.getString("first_name");
				lastName = rs.getString("last_name");

//				User newUser = new User(userId, firstName, lastName, username, password, email, role);

				reimbId = rs.getInt("reimb_id");
				reimbAmount = rs.getDouble("reimb_amount");
				dateSubmitted = rs.getString("reimb_submitted");
				dateResolved = rs.getString("reimb_resolved");
				description = rs.getString("reimb_description");
				author = firstName + " " + lastName;
				resolver = rs.getString("resolver_first") + " " + rs.getString("resolver_last");
				status = rs.getString("reimb_status");
				type = rs.getString("reimb_type");

				Reimbursement newReimbursement = new Reimbursement(reimbId, reimbAmount, dateSubmitted, dateResolved,
						author, resolver, status, type, description);

				userReimbursements.add(newReimbursement);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userReimbursements;
	}

	@Override
	public Reimbursement getReimbById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reimbursement> getAllPendingReimb() {
		List<Reimbursement> userReimbursements = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

			String sql = "select cr.reimb_id , cr.reimb_amount , cr.reimb_description , cr.reimb_submitted, crt.reimb_type, cr.reimb_status_id, crs.reimb_status "
					+ "	, cu.first_name , cu.last_name , cu.username , cu.\"password\" , cu.email, cur.role_title "
					+ "	, cu.user_id , cr.reimb_resolved, cr.reimb_description, cu2.first_name as resolver_first "
					+ "	, cu2.last_name as resolver_last " + "from chemx_reimbursements cr "
					+ "left join chemx_reimbursement_type crt " + "on cr.reimb_type_id = crt.reimb_type_id "
					+ "left join chemx_reimbursement_status crs " + "on cr.reimb_status_id = crs.reimb_status_id "
					+ "inner join chemx_users cu " + "on cr.reimb_author = cu.user_id " + "left join chemx_users cu2 "
					+ "on cr.reimb_resolver = cu2.user_id " + "left join chemx_user_roles cur "
					+ "on cu.user_role_id = cur.role_id " + "where cr.reimb_status_id = 1"
					+ "order by cr.reimb_submitted asc";

			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();

//			int userId = 0;
			String firstName = null;
			String lastName = null;

			int reimbId = 0;
			double reimbAmount = 0;
			String dateSubmitted = null;
			String dateResolved = null;
			String description = null;
			String author = null;
			String resolver = null;
			String status = null;
			String type = null;

			while (rs.next()) {
//				userId = rs.getInt("user_id");
				firstName = rs.getString("first_name");
				lastName = rs.getString("last_name");

//				User newUser = new User(userId, firstName, lastName, username, password, email, role);

				reimbId = rs.getInt("reimb_id");
				reimbAmount = rs.getDouble("reimb_amount");
				dateSubmitted = rs.getString("reimb_submitted");
				dateResolved = rs.getString("reimb_resolved");
				description = rs.getString("reimb_description");
				author = firstName + " " + lastName;
				resolver = rs.getString("resolver_first") + " " + rs.getString("resolver_last");
				status = rs.getString("reimb_status");
				type = rs.getString("reimb_type");

				Reimbursement newReimbursement = new Reimbursement(reimbId, reimbAmount, dateSubmitted, dateResolved,
						author, resolver, status, type, description);

				userReimbursements.add(newReimbursement);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userReimbursements;
	}

	@Override
	public List<Reimbursement> getAllReimbByUser(int id) {
		List<Reimbursement> userReimbursements = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

			String sql = "select cr.reimb_id , cr.reimb_amount , cr.reimb_description , cr.reimb_submitted, crt.reimb_type, cr.reimb_status_id, crs.reimb_status "
					+ "	, cu.first_name , cu.last_name , cu.username , cu.\"password\" , cu.email, cur.role_title "
					+ "	, cu.user_id , cr.reimb_resolved, cr.reimb_description, cu2.first_name as resolver_first "
					+ "	, cu2.last_name as resolver_last " + "from chemx_reimbursements cr "
					+ "left join chemx_reimbursement_type crt " + "on cr.reimb_type_id = crt.reimb_type_id "
					+ "left join chemx_reimbursement_status crs " + "on cr.reimb_status_id = crs.reimb_status_id "
					+ "inner join chemx_users cu " + "on cr.reimb_author = cu.user_id " + "left join chemx_users cu2 "
					+ "on cr.reimb_resolver = cu2.user_id " + "left join chemx_user_roles cur "
					+ "on cu.user_role_id = cur.role_id " + "where cu.user_id = ?"
					+ "order by cr.reimb_submitted desc;";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

//			int userId = 0;
			String firstName = null;
			String lastName = null;
//			String username = null;
//			String password = null;
//			String email = null;
//			String role = null;

			int reimbId = 0;
			double reimbAmount = 0;
			String dateSubmitted = null;
			String dateResolved = null;
			String description = null;
			String author = null;
			String resolver = null;
			String status = null;
			String type = null;

			while (rs.next()) {
//				userId = rs.getInt("user_id");
				firstName = rs.getString("first_name");
				lastName = rs.getString("last_name");
//				username = rs.getString("username");
//				password = rs.getString("password");
//				email = rs.getString("email");
//				role = rs.getString("role_title");

				reimbId = rs.getInt("reimb_id");
				reimbAmount = rs.getDouble("reimb_amount");
				dateSubmitted = rs.getString("reimb_submitted");
				dateResolved = rs.getString("reimb_resolved");
				description = rs.getString("reimb_description");
				author = firstName + " " + lastName;
				resolver = rs.getString("resolver_first") + " " + rs.getString("resolver_last");
				status = rs.getString("reimb_status");
				type = rs.getString("reimb_type");

				Reimbursement newReimbursement = new Reimbursement(reimbId, reimbAmount, dateSubmitted, dateResolved,
						author, resolver, status, type, description);

				userReimbursements.add(newReimbursement);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userReimbursements;
	}

	@Override
	public void updateReimbStatus(int resolver, int status, int reimbID, Timestamp time) {

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

			String sql = "update chemx_reimbursements "
					+ "set reimb_resolver = ?, reimb_status_id = ?, reimb_resolved = ?" + "where reimb_id = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, resolver);
			ps.setInt(2, status);
			ps.setTimestamp(3, time);
			ps.setInt(4, reimbID);

			int rs = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void h2InitDao() {
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "CREATE TABLE ers_user_roles (\r\n" + 
					"	ers_user_role_id SERIAL PRIMARY KEY\r\n" + 
					"	, user_role VARCHAR NOT NULL\r\n" + 
					");\r\n" + 
					"\r\n" + 
					"CREATE TABLE ers_reimbursement_status (\r\n" + 
					"	reimb_status_id INTEGER\r\n" + 
					"	, reimb_status VARCHAR(10) NOT NULL\r\n" + 
					"	, CONSTRAINT reimb_status_pk PRIMARY KEY (reimb_status_id)\r\n" + 
					");\r\n" + 
					"\r\n" + 
					"CREATE TABLE ers_reimbursement_type (\r\n" + 
					"	reimb_type_id INTEGER\r\n" + 
					"	, reimb_type VARCHAR(20) NOT NULL\r\n" + 
					"	, CONSTRAINT reimb_type_pk PRIMARY KEY (reimb_type_id)\r\n" + 
					");\r\n" + 
					"\r\n" + 
					"CREATE TABLE ers_users (\r\n" + 
					"	ers_users_id SERIAL\r\n" + 
					"	, ers_username VARCHAR(20) NOT NULL UNIQUE\r\n" + 
					"	, ers_password VARCHAR(50) NOT NULL\r\n" + 
					"	, user_first_name VARCHAR(100) NOT NULL\r\n" + 
					"	, user_last_name VARCHAR(100) NOT NULL\r\n" + 
					"	, user_email VARCHAR(150) NOT NULL UNIQUE\r\n" + 
					"	, user_role_id INTEGER\r\n" + 
					"	, CONSTRAINT ers_users_pk PRIMARY KEY (ers_users_id)\r\n" + 
					"	, CONSTRAINT user_role_fk FOREIGN KEY (user_role_id)\r\n" + 
					"		REFERENCES ers_user_roles (ers_user_role_id)\r\n" + 
					"		ON DELETE CASCADE \r\n" + 
					");\r\n" + 
					"\r\n" + 
					"CREATE TABLE ers_reimbursement (\r\n" + 
					"	reimb_id SERIAL PRIMARY KEY\r\n" + 
					"	, reimb_amount NUMERIC(30,2) NOT NULL\r\n" + 
					"	, reimb_submitted TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\r\n" + 
					"	, reimb_resolved TIMESTAMP\r\n" + 
					"	, reimb_description VARCHAR(250)\r\n" + 
					"	, reimb_receipt VARCHAR(250)\r\n" + 
					"	, reimb_author INTEGER\r\n" + 
					"	, CONSTRAINT ers_user_fk_auth FOREIGN KEY (reimb_author)\r\n" + 
					"		REFERENCES ers_users (ers_users_id)\r\n" + 
					"		ON DELETE CASCADE\r\n" + 
					"	, reimb_resolver INTEGER\r\n" + 
					"	, reimb_status_id INTEGER\r\n" + 
					"	, CONSTRAINT ers_reimbursment_status_id FOREIGN KEY (reimb_status_id)\r\n" + 
					"		REFERENCES ers_reimbursement_status (reimb_status_id)\r\n" + 
					"		ON DELETE CASCADE\r\n" + 
					"	, reimb_type_id INTEGER\r\n" + 
					"	, CONSTRAINT ers_reimbursement_type_fl FOREIGN KEY (reimb_type_id)\r\n" + 
					"		REFERENCES ers_reimbursement_type (reimb_type_id)\r\n" + 
					"		ON DELETE CASCADE\r\n" + 
					");\r\n" + 
					"--insert rows\r\n" + 
					"INSERT INTO ers_user_roles VALUES (1, 'f_manager'), (2,'employee');\r\n" + 
					"\r\n" + 
					"INSERT INTO ers_reimbursement_status VALUES(0, 'PENDING'), (1, 'APPROVED'), (2,'DENIED');\r\n" + 
					"\r\n" + 
					"INSERT INTO ers_reimbursement_type VALUES (1, 'LODGING'), (2, 'TRAVEL'), (3, 'FOOD'), (4, 'OTHER');\r\n" + 
					"\r\n" + 
					"INSERT INTO ers_users VALUES (DEFAULT, 'sonali', 'test', 'Sonali', 'Najera', 'sonali@gmail.com', 2);\r\n" + 
					"INSERT INTO ers_users VALUES (DEFAULT, 'sonali2', 'testt', 'Sonali', 'Najera', 'sonali@ymail.com', 1);\r\n" + 
					"\r\n" + 
					"INSERT INTO ers_reimbursement \r\n" + 
					"	VALUES (DEFAULT, 100.235, DEFAULT, NULL, NULL, NULL, 1, 5, 0, 1); ";
			Statement s = conn.createStatement();
			s.execute(sql);
		} catch (SQLException e) {
		System.out.println(e);
		}
	}


	@Override
	public void h2DestroyDao() {
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "DROP TABLE ers_reimbursement;"
					+ "DROP TABLE ers_users;"
					+ "DROP TABLE ers_user_roles;"
					+ "DROP TABLE ers_reimbursement_type;"
					+ "DROP TABLE ers_reimbursement_status;";
			Statement s = conn.createStatement();
			s.execute(sql);
		}catch(SQLException e) {
			System.out.println(e);
		}

		
	}
}
