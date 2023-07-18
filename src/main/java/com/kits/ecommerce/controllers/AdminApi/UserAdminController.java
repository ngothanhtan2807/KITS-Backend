package com.kits.ecommerce.controllers.AdminApi;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kits.ecommerce.config.AppConstants;
import com.kits.ecommerce.dtos.ApiResponse;
import com.kits.ecommerce.dtos.PageDto;
import com.kits.ecommerce.dtos.ResponseDTO;
import com.kits.ecommerce.dtos.UserDto;
import com.kits.ecommerce.entities.User;
import com.kits.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/")
public class UserAdminController {
    @Autowired
    private UserService userService;

//    @Value("${user.image.path}")
//    private  String UPLOAD_FOLDER;

    @Autowired
    private Cloudinary cloudinary;

    private static final Logger logger = LoggerFactory.getLogger(UserAdminController.class);
    //GET user get all
    @GetMapping("users")
    public ResponseEntity<PageDto<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber",required = false) Integer pageNumber,
            //pageNumber bắt đầu từ 0 trang 0 lấy bao nhiêu phần tử PageSize
            @RequestParam(value="pageSize",required = false) Integer pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_USERS_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir //tăng ha
    ) {
        PageDto<UserDto> allPageUsers= this.userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);


        return new ResponseEntity<PageDto<UserDto>>(allPageUsers, HttpStatus.OK);
    }

    @PostMapping("users")

    public ResponseDTO< UserDto> add(@Valid @ModelAttribute UserDto u) throws IllegalStateException, IOException {

        Map r = this.cloudinary.uploader().upload(u.getFile().getBytes(), ObjectUtils.asMap("resource_type","auto"));


        String img = (String) r.get("secure_url");

        u.setAvatar(img);

        userService.createUser(u);
        return ResponseDTO.<UserDto>builder().status(200).data(u).build();
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<UserDto> getUserBy(@PathVariable("userId") Integer uId) {
        return ResponseEntity.ok(this.userService.getUserById(uId));
    }

    @DeleteMapping("users//{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uId) {
        this.userService.deleteUser(uId);
        return new ResponseEntity(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
    }



    //POST -create user
//    @PostMapping("/")
//    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
//           UserDto createUserDto = this.userService.createUser(userDto);
//           return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
//    }

    //PUT -update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uId) {
        UserDto updatedUser = this.userService.updateUser(userDto,uId);
        return ResponseEntity.ok(updatedUser);
    }
    //DELETE delete user
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uId) {
//        this.userService.deleteUser(uId);
//        return new ResponseEntity(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
//    }
//
//    //GET user get all
//    @GetMapping("/")
//    public ResponseEntity<PageDto<UserDto>> getAllUsers(
//            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
//            //pageNumber bắt đầu từ 0 trang 0 lấy bao nhiêu phần tử PageSize
//            @RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
//            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_USERS_BY,required = false) String sortBy,
//            @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir //tăng ha
//    ) {
//        PageDto<UserDto> allPageUsers= this.userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
//        return new ResponseEntity<PageDto<UserDto>>(allPageUsers, HttpStatus.OK);
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserDto> getAllUsers(@PathVariable("userId") Integer uId) {
//        return ResponseEntity.ok(this.userService.getUserById(uId));
//    }

//    @PostMapping("/users/avatar/upload/{userId}")
//    public ResponseEntity<UserDto> uploadProductImage(
//            @RequestParam("image") MultipartFile image,
//            @PathVariable Integer userId
//    ) throws IOException {
//        UserDto userDto= this.userService.getUserById(userId);
//
//
//        String fileName =  this.fileService.uploadImage(path,image);
//        userDto.setAvatar(fileName);
//        UserDto updateUser = this.userService.updateUser(userDto,userId);
//        return new ResponseEntity<UserDto>(updateUser,HttpStatus.OK);
//
//    }

    //search
//    @GetMapping("/search/{keywords}")
//    public ResponseEntity<List<UserDto>> searchUserByEmail(
//            @PathVariable("keywords") String keywords
//    ) {
//        List<UserDto> result = this.userService.searchUsers(keywords);
//        return new ResponseEntity<List<UserDto>>(result,HttpStatus.OK);
//
//    }





}
