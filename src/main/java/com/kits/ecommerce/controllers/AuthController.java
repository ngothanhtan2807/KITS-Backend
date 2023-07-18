package com.kits.ecommerce.controllers;

import com.kits.ecommerce.dtos.*;
import com.kits.ecommerce.services.UserService;
import com.kits.ecommerce.services.impl.JwtTokenService;
import com.kits.ecommerce.utils.CookieUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

//su dung http://localhost:8080/swagger-ui/index.html  de xem api doc

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    UserService userService;

   @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")

    public ResponseEntity<ResponseFetchMe> me(Principal p) {
        String username = p.getName();
        UserDto userDto = userService.findByEmail(username);

        ResponseFetchMe res = new ResponseFetchMe(userDto.getUserId(),userDto.getName()
                ,userDto.getEmail(),userDto.getAvatar(),userDto.getRoles().get(0).getRoleName());

        return new ResponseEntity<ResponseFetchMe>(res, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> registerHandler(@Valid @RequestBody UserDto user) {
        String encodedPass = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPass);

        UserDto userDto = userService.registerUser(user);

        String token = jwtTokenService.createToken(userDto.getEmail());


        return ResponseEntity.ok(new JwtAuthResponse(token,true));
    }


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody @Valid JwtAuthRequest jwtAuthRequest) {


        UsernamePasswordAuthenticationToken authCredentials = new UsernamePasswordAuthenticationToken(
                jwtAuthRequest.getEmail(), jwtAuthRequest.getPassword());

        authenticationManager.authenticate(authCredentials);

        String token =jwtTokenService.createToken(jwtAuthRequest.getEmail());

        return ResponseEntity.ok(new JwtAuthResponse(token,true));

    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {

        // Cách 1: Xoá token khỏi client bằng cookie
        CookieUtil.clearTokenCookie(request);



        // Cách 2: Xoá token khỏi client bằng giải phóng token khỏi local storage hoặc session storage
        // localStorage.removeItem('jwtToken');
        // sessionStorage.removeItem('jwtToken');

        return ResponseEntity.ok("Logged out successfully");
    }


}
