package com.upgrad.ublog.services;

import com.upgrad.ublog.dto.UserDTO;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO) throws Exception;
    UserDTO findByEmail(String emailId) throws Exception;
}
