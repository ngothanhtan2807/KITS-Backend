package com.kits.ecommerce.controllers.PublicApi;

import com.kits.ecommerce.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/public/session")
public class UserPublicController {
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSession(final HttpServletRequest request, final HttpServletResponse response) {
//        Cart cart = (Cart) request.getSession().getAttribute("cart");
//
        request.getSession().invalidate();
        return new ResponseEntity<>(new ApiResponse("Delete success", true), HttpStatus.OK);
    }
}
