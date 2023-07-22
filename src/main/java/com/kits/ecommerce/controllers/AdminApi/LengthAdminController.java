package com.kits.ecommerce.controllers.AdminApi;

import com.kits.ecommerce.dtos.ApiResponse;
import com.kits.ecommerce.dtos.CatalogDto;
import com.kits.ecommerce.dtos.LengthDto;
import com.kits.ecommerce.entities.Length;
import com.kits.ecommerce.services.LengthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/lengths")
public class LengthAdminController {
    @Autowired
    LengthService lengthService;

    @GetMapping("/")
    public ResponseEntity<List<LengthDto>> getAll(){
        return ResponseEntity.ok(lengthService.getAllLengths());
    }
    @GetMapping("/{id}")
    public ResponseEntity<LengthDto>getLength(@PathVariable("id") Integer id){
        return ResponseEntity.ok(lengthService.getLengthById(id));
    }
    @PostMapping("/")
    public ResponseEntity<LengthDto>createLength(@RequestBody LengthDto lengthDto){
        return ResponseEntity.ok(lengthService.createLength(lengthDto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<LengthDto>updateCatalog(@PathVariable("id")Integer id, @RequestBody LengthDto lengthDto){
        return ResponseEntity.ok(lengthService.updateLength(lengthDto, id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLength(@PathVariable("id")Integer id){
        lengthService.deleteLength(id);
        return new ResponseEntity(new ApiResponse("Length deleted Successfully",true), HttpStatus.OK);
    }
    @PostMapping("/addLengths")
    public ResponseEntity<?>  addLengths(@RequestBody List<Length> lengths) {

        // TODO: Xử lý logic để thêm nhiều catalog vào trong cơ sở dữ liệu
        lengthService.addLengthsService(lengths);
        // Trả về thông báo thành công nếu thêm thành công
        return new ResponseEntity(new ApiResponse("Add all success!!!", true), HttpStatus.OK);
    }

    @DeleteMapping("/deleteLengths")
    public ResponseEntity<?> deleteLengths(@RequestBody List<Integer> ids) {
        lengthService.deleteLengthsService(ids);
        return new ResponseEntity(new ApiResponse("Delete all success!!!", true), HttpStatus.OK);
    }
}
