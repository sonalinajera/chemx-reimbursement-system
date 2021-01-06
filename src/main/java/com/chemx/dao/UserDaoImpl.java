package com.chemx.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chemx.model.User;

public class UserDaoImpl implements UserDao {
	
	private static String DB_URL = "jdbc:postgresql://" + System.getenv("TRAINING_DB_URL") + "/" + System.getenv("TRAINING_DB_CHEMX");
	private static String DB_USERNAME = System.getenv("TRAINING_DB_USERNAME");
	private static String DB_PASSWORD = System.getenv("TRAINING_DB_MACPASS");
	
	
	static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Static block has failed me");
        }
    }

	
	
	public  User verifyValidUser(String tryUsername, String tryPassword) {
		
		User loggedIn = null; 
		
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			String sql = "select u.user_id, u.username, u.password, " + 
					"u.first_name, u. last_name, u.email, " + 
					"r.role_title as user_role " + 
					"from chemx_users u " + 
					"inner join chemx_user_roles r " + 
					"on u.user_role_id = r.role_id " + 
					"and u.username = ? " + 
					"and u.\"password\" = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, tryUsername);
			ps.setString(2, tryPassword);
			 
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				
				
				
				int userId = rs.getInt("user_id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				String role = rs.getString("user_role");
				String username = rs.getString("username");
				String password = rs.getString("password");
				loggedIn = new User(userId, firstName, lastName, username, password, email, role);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(loggedIn);
		return loggedIn;
		
		
	}
	

}
