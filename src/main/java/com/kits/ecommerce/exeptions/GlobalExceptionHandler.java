//package com.kits.ecommerce.exeptions;
//
//import com.kits.ecommerce.dtos.ApiError;
//import com.kits.ecommerce.dtos.ResponseDTO;
//import io.jsonwebtoken.MalformedJwtException;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.kits.ecommerce.dtos.ApiResponse;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//
//
//import java.sql.SQLIntegrityConstraintViolationException;
//import java.util.HashMap;
//
//import java.util.Map;
//
//@RestControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//    /* if id not found in service Controller then orElseThrow method throw
//    here i will give response
//
//
//     */
//
//    // log, slf4j
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//    @ResponseStatus(code = HttpStatus.CONFLICT)
//    public ResponseDTO<String> duplicate(Exception e) {
//        log.info("INFO",e);
//        return ResponseDTO.<String>builder().status(409).msg("Duplicate Data").build();
//
//    }
//
//
//    @ExceptionHandler(ResoureNotFoundException.class)
//    public ResponseEntity<ApiResponse> HandelResourceNotFoundException(ResoureNotFoundException ex) {
//        String message =ex.getMessage();
//        ApiResponse apiResponse = new ApiResponse(message,false);
//        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
//
//    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String,String>> HandleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
//        Map<String,String> resp = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError)error).getField();
//            String message = error.getDefaultMessage();
//            resp.put(fieldName,message);
//
//        });
//
//        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
//
//    }
//
//    @ExceptionHandler(UserAlreadyExistsException.class)
//    public ResponseEntity< ApiResponse> HandleUserAlreadyExistsException(UserAlreadyExistsException e) {
//
//        String message = e.getMessage();
//
//        ApiResponse res = new   ApiResponse(message, false);
//
//        return new ResponseEntity< ApiResponse>(res, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ApiResponse> HandleIntegrityException(DataIntegrityViolationException e) {
//        ApiResponse res = new ApiResponse(e.getMessage(), false);
//
//        return new ResponseEntity<ApiResponse>(res, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ConflictException.class)
//    public ResponseEntity<Object> HandleConflictException(ConflictException ex) {
//        // Tạo một đối tượng ApiError chứa thông báo lỗi
//        ApiError apiError = new ApiError(HttpStatus.CONFLICT, "Conflict", ex.getMessage());
//        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException ex) {
//        ApiResponse res = new ApiResponse("Email or password is not valid", false);
//        // Kiểm tra và xử lý thông tin email và mật khẩu
//
//        return new ResponseEntity<ApiResponse>(res, HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<ApiResponse> handleMalformedJwtException(MalformedJwtException ex) {
//        ApiResponse res = new ApiResponse("Invalid or malformed JWT", false);
//        return new ResponseEntity<ApiResponse>(res, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
//        ApiResponse res = new ApiResponse("lỗi", false);
//        return new ResponseEntity<ApiResponse>(res, HttpStatus.FORBIDDEN);
//    }
//
//
//}
