package com.Abdo_Fahmi.Recipe_Bank.service;

import com.Abdo_Fahmi.Recipe_Bank.model.user.User;
import com.Abdo_Fahmi.Recipe_Bank.model.user.UserRegistrationDTO;
import com.Abdo_Fahmi.Recipe_Bank.model.user.UserDTO;

public interface IUserService {
    UserDTO registerUser(UserRegistrationDTO user);
    void deleteUserById(String id);
    UserDTO updateUser(String id, UserDTO user);
    UserDTO findUserById(String id);
    UserDTO findUserByName(String name);
}