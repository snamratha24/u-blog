package com.upgrad.ublog.dao;

import com.upgrad.ublog.db.DatabaseConnection;
import com.upgrad.ublog.dto.UserDTO;

import java.sql.*;

/**
 * TODO: 6.5. Implement the UserDAO interface and implement this class using the Singleton pattern.
 *  (Hint: Should have a private no-arg Constructor, a private static instance attribute of type
 *  UserDAOImpl and a public static getInstance() method which returns the instance attribute.)
 * TODO: 6.6. findByEmail() method should take email id as an input parameter and
 *  return the user details corresponding to the email id from the UBLOG_USERS table defined
 *  in the database. (Hint: You should get the connection using the DatabaseConnection class)
 * TODO: 6.7. create() method should take user details as input and insert these details
 *  into the UBLOG_USERS table. Return the same UserDTO object which was passed as an input
 *  argument. (Hint: You should get the connection using the DatabaseConnection class)
 */

public class UserDAOImpl implements UserDAO{

    UserDAOImpl() {
    }

    private static UserDAOImpl instance;

    public static UserDAOImpl getInstance() {
        return instance;
    }

    @Override
    public UserDTO create(UserDTO userDTO) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
        String userEmailId = userDTO.getEmailId();
        String userPassword = userDTO.getPassword();
        int userId = userDTO.getUserId();
        String query = "INSERT INTO UBLOG_USERS(id, email_id, password)" + "VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1,userId);
        statement.setString(2,userEmailId);
        statement.setString(3,userPassword);
        statement.executeUpdate();
        return userDTO;

//        String insertQuery = "INSERT INTO ublog_users (id, EMAIL_ID, PASSWORD) VALUES ('" + userDTO.getUserId() + "', '" + userDTO.getEmailId() + "','" + userDTO.getPassword() + "')";
//
//        Statement statement = connection.createStatement();
//        statement.executeUpdate(insertQuery);
//
//        System.out.println("USER INSERTED");
//        return null;
    }

    @Override
    public UserDTO findByEmail(String emailId) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
//        System.out.println(connection);
//        DatabaseConnection.getConnection();

        String selectQuery = "select * from ublog_users WHERE EMAIL_ID ='" + emailId + "'" ;
        Statement statement = connection.createStatement();
//        System.out.println(selectQuery);
//        statement.executeQuery(selectQuery);
        ResultSet resultSet = statement.executeQuery(selectQuery);
        UserDTO userDTO = new UserDTO();
        if (resultSet.next()) {
            userDTO.setEmailId(resultSet.getString("email_id"));
            userDTO.setPassword(resultSet.getString("password"));
            userDTO.setUserId(resultSet.getInt("id"));
        }
//        System.out.println("PRINTING THIS METHOD");
//        System.out.println(userDTO);
        return userDTO;
        //        UserDAO userDAO = new UserDAOImpl();
//        UserDTO temp = new UserDTO();
//
//        while (resultSet.next()){
//            System.out.println("ACTUAL RESULTS");
//
////            System.out.println(
////                    resultSet.getInt("ID") + "," + resultSet.getString("EMAIL_ID") + "," + resultSet.getString("PASSWORD")
//			temp.setUserId(resultSet.getInt("ID"));
//			temp.setEmailId(resultSet.getString("EMAIL_ID"));
//			temp.setPassword(resultSet.getString("PASSWORD"));
//			userDAO.create(temp);
//        }
//
//        return temp;
    }


/*    public static void main(String[] args) {
		try {
			UserDAO userDAO = new UserDAOImpl();
			UserDTO temp = new UserDTO();
			temp.setUserId(1);
			temp.setEmailId("new_user@new.user");
			temp.setPassword("newuserpass");
			userDAO.create(temp);
			System.out.println(userDAO.findByEmail("new@temp.temp"));
		} catch (Exception e) {
			System.out.println("FAILED");
		}

		 // Following should be the desired output of the main method.
		 // UserDTO{userId=11, emailId='temp@temp.temp', password='temp'}
	}*/


}
