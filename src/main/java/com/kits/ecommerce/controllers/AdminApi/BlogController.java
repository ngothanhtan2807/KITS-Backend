package com.kits.ecommerce.controllers.AdminApi;

import com.kits.ecommerce.dtos.BlogDto;
import com.kits.ecommerce.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import com.kits.ecommerce.dtos.ApiResponse;
@RestController
@RequestMapping("/api/admin/blogs")
public class BlogController {

    @Autowired
    BlogService blogService;
    @GetMapping("")
    public ResponseEntity<List<BlogDto>> getAll(){
        return ResponseEntity.ok(blogService.getAllBlogs());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BlogDto>getBlog(@PathVariable("id") Integer id){
        return ResponseEntity.ok(blogService.getBlogById(id));
    }
    @PostMapping("")
    public ResponseEntity<BlogDto>createBlog(@RequestBody BlogDto catalogDto){
        return ResponseEntity.ok(blogService.createBlog(catalogDto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<BlogDto>updateBlog(@PathVariable("id")Integer id, @RequestBody BlogDto catalogDto){
        return ResponseEntity.ok(blogService.updateBlog(catalogDto, id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable("id")Integer id){
        blogService.deleteBlog(id);
        return new ResponseEntity(new ApiResponse("User deleted Successfully",true), HttpStatus.OK);
    }

}