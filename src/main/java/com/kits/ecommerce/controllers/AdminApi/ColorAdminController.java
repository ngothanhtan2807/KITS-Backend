package com.kits.ecommerce.controllers.AdminApi;

import com.kits.ecommerce.dtos.ApiResponse;
import com.kits.ecommerce.dtos.ColorDto;
import com.kits.ecommerce.entities.Color;
import com.kits.ecommerce.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/colors")
@CrossOrigin
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

    @PostMapping("/addColors")
    public ResponseEntity<?>  addColors(@RequestBody List<Color> colors) {

        // TODO: Xử lý logic để thêm nhiều màu sắc vào trong cơ sở dữ liệu
           colorService.addColorsService(colors);
        // Trả về thông báo thành công nếu thêm thành công
        return new ResponseEntity(new ApiResponse("Add all success!!!", true), HttpStatus.OK);
    }

    @DeleteMapping("/deleteColors")
    public ResponseEntity<?> deleteColors(@RequestBody List<Integer> colorIds) {
        colorService.deleteColorsService(colorIds);
        return new ResponseEntity(new ApiResponse("Delete all success!!!", true), HttpStatus.OK);
    }
}
