package com.kits.ecommerce.services;

import com.kits.ecommerce.dtos.PageDto;
import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.dtos.UserDto;
import com.kits.ecommerce.entities.User;


import java.util.List;



public interface UserService {

   void createUser(UserDto userDto);
   UserDto updateUser(UserDto user,Integer userId);
   UserDto getUserById(Integer userId);
   PageDto<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
   void deleteUser(Integer userId);

   UserDto findByEmail(String email) ;

   UserDto updateAvatar(UserDto userDto, Integer userId);

   UserDto registerUser(UserDto userDto);

   void updatePassword(UserDto userDTO);


   int count();


}
