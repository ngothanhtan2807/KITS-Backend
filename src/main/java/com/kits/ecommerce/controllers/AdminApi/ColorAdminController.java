package com.kits.ecommerce.controllers.AdminApi;

import com.kits.ecommerce.dtos.ApiResponse;
import com.kits.ecommerce.dtos.ColorDto;
import com.kits.ecommerce.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/colors")
public class ColorAdminController {
    @Autowired
    ColorService colorService;

    @GetMapping("/")
    public ResponseEntity<List<ColorDto>>getAllColor(){
        return ResponseEntity.ok(colorService.getAllColor());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ColorDto> getOne(@PathVariable("id")Integer id){
        return ResponseEntity.ok(colorService.getColorById(id));
    }
    @PostMapping("/")
    public ResponseEntity<ColorDto>createColor(@RequestBody ColorDto colorDto){
        return ResponseEntity.ok(colorService.createColor(colorDto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ColorDto>updateColor(@PathVariable("id")Integer id, @RequestBody ColorDto colorDto){
        return ResponseEntity.ok(colorService.updateColor(colorDto, id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteColor(@PathVariable("id")Integer id){
        colorService.deleteColor(id);
        return new ResponseEntity(new ApiResponse("Delete success!!!", true), HttpStatus.OK);
    }
}
