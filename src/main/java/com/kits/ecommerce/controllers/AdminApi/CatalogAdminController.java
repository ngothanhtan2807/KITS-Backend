package com.kits.ecommerce.controllers.AdminApi;
import com.kits.ecommerce.dtos.ApiResponse;
import com.kits.ecommerce.dtos.CatalogDto;
import com.kits.ecommerce.services.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/catalogs")
public class CatalogAdminController {
    @Autowired
    CatalogService catalogService;

    @GetMapping("/")
    public ResponseEntity<List<CatalogDto>> getAll(){
        return ResponseEntity.ok(catalogService.getAllCatalogs());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CatalogDto>getCatalog(@PathVariable("id") Integer id){
        return ResponseEntity.ok(catalogService.getCatalogById(id));
    }
    @PostMapping("/")
    public ResponseEntity<CatalogDto>createCatalog(@RequestBody CatalogDto catalogDto){
        return ResponseEntity.ok(catalogService.createCatalog(catalogDto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CatalogDto>updateCatalog(@PathVariable("id")Integer id, @RequestBody CatalogDto catalogDto){
        return ResponseEntity.ok(catalogService.updateCatalog(catalogDto, id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCatalog(@PathVariable("id")Integer id){
        catalogService.deleteCatalog(id);
        return new ResponseEntity(new ApiResponse("User deleted Successfully",true), HttpStatus.OK);
    }
}
