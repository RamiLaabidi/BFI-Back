package com.bfi.authentification.Mappers;

import com.bfi.authentification.DTO.UpdateUserDTO;
import com.bfi.authentification.DTO.UserDTO;
import com.bfi.authentification.entities.User;
import org.modelmapper.ModelMapper;

public class UserMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static UpdateUserDTO convertToDto(User user) {
        return modelMapper.map(user, UpdateUserDTO.class);
    }
    public static UserDTO convertUserToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public static User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
