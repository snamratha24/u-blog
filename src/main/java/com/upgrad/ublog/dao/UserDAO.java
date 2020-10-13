package com.upgrad.ublog.dao;

import com.upgrad.ublog.dto.UserDTO;

import java.sql.SQLException;

public interface UserDAO {
    UserDTO create(UserDTO userDTO) throws SQLException;
    UserDTO findByEmail(String emailId) throws SQLException;

    public static UserDAOImpl getInstance() {
        return new UserDAOImpl();
    }
}
