package com.kits.ecommerce.services.impl;

import com.kits.ecommerce.config.AppConstants;
import com.kits.ecommerce.dtos.PageDto;
import com.kits.ecommerce.dtos.ProductDto;
import com.kits.ecommerce.dtos.UserDto;
import com.kits.ecommerce.entities.Product;
import com.kits.ecommerce.entities.Role;
import com.kits.ecommerce.entities.User;
import com.kits.ecommerce.exeptions.ConflictException;
import com.kits.ecommerce.exeptions.ResoureNotFoundException;
import com.kits.ecommerce.exeptions.UserAlreadyExistsException;
import com.kits.ecommerce.repositories.RoleRepo;
import com.kits.ecommerce.repositories.UserRepo;
import com.kits.ecommerce.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        User user = new ModelMapper().map(userDto, User.class);
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email is already registered");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        this.userRepo.save(user);

        // tra ve idsau khi tao
        userDto.setUserId(user.getUserId());


    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResoureNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.convertToUserDto(updatedUser);
        return userDto1;
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResoureNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

    @Override
    public UserDto findByEmail(String email) { // java8, optinal
        User user = userRepo.findByEmail(email);
        if (user == null)
            throw new NoResultException();
        return new ModelMapper().map(user, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto registerUser(UserDto userDto) {

        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new ConflictException("Email is already registered");

        }

        User user = modelMapper.map(userDto, User.class);

        Role role = roleRepo.findById(AppConstants.USER_ID).get();
        user.getRoles().add(role);
        user.setAvatar("default.png");
//        user.setPassword();
        user.setBirthdate(userDto.getBirthdate());


        User registeredUser = userRepo.save(user);


        userDto = modelMapper.map(registeredUser, UserDto.class);

        return userDto;

    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResoureNotFoundException("User", "Id", userId));
        return this.convertToUserDto(user);
    }

    @Override
    public PageDto<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        if (pageNumber != null && pageSize != null) {
            Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
//        if(sortDir.equalsIgnoreCase("asc")) {
//             sort = Sort.by(sortBy).ascending();
//        }else {
//            sort = Sort.by(sortBy).descending();
//        }
            Pageable p = PageRequest.of(pageNumber, pageSize, sort);
            // nếu muốn sort từ cao đến thấp thêm .descending()


            Page<User> pageUser = this.userRepo.findAll(p);
            List<User> allUsers = pageUser.getContent();
//        List<Product> allProducts = this.productRepo.findAll();
            List<UserDto> userDtos = allUsers.stream().map((user) -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

            PageDto<UserDto> userPageResponse = new PageDto<>();
            userPageResponse.setContents(userDtos);
            userPageResponse.setPageNumber(pageUser.getNumber());
            userPageResponse.setPageSize(pageUser.getSize());
            userPageResponse.setTotalElements(pageUser.getTotalElements());
            userPageResponse.setTotalPages(pageUser.getTotalPages());
            userPageResponse.setLastPage(pageUser.isLast());
            return userPageResponse;

        } else {

            List<User> allUsers = this.userRepo.findAll();
            List<UserDto> userDtos = allUsers.stream().map((user) -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
            PageDto<UserDto> userPageResponse = new PageDto<>();
            userPageResponse.setContents(userDtos);
            return userPageResponse;

        }

    }


//    @Override
//    public List<UserDto> searchUsers(String keyword) {
//        List<User> users = this.userRepo.searchByEmail("%" + keyword +"%");
//        List<UserDto> userDtos = users.stream().map((user) -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
//        return userDtos;
//    }

    private User convertToUser(UserDto userDto) {
        //cach 2
        User user = this.modelMapper.map(userDto, User.class);
        //cach1
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    private UserDto convertToUserDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        //cach 1
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
