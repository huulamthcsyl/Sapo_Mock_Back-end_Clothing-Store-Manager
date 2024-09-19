package sapo.com.controller.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapo.com.exception.DataConflictException;
import sapo.com.exception.ResourceNotFoundException;
import sapo.com.model.dto.request.BrandRequest;
import sapo.com.model.dto.request.CategoryRequest;
import sapo.com.model.dto.response.BrandResponse;
import sapo.com.model.dto.response.CategoryResponse;
import sapo.com.model.dto.response.ResponseObject;
import sapo.com.service.impl.BrandServiceImpl;
import sapo.com.service.impl.CategoryServiceImpl;

import java.util.Set;

@RestController
@RequestMapping("/v1/products/brands")
public class BrandController {

    private static final Logger log = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private BrandServiceImpl brandService ;

    @GetMapping
    public ResponseEntity<ResponseObject> getListOfBrands(@RequestParam Long page, @RequestParam Long limit, @RequestParam String query){
        try{
            Set<BrandResponse> brands = brandService.getListOfBrands(page,limit,query);
            return new ResponseEntity<>(new ResponseObject("Lấy danh sách nhãn hiệu thành công", brands), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            log.error("Error: ",e);
            return new ResponseEntity<>(new ResponseObject(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error("Error: ",e);
            return new ResponseEntity<>(new ResponseObject(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBrandById(@PathVariable Long id){
        try{
            BrandResponse brand = brandService.getBrandById(id);
            return new ResponseEntity<>(new ResponseObject("Lấy thông tin nhãn hiệu thành công", brand), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            log.error("Error: ",e);
            return new ResponseEntity<>(new ResponseObject(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error("Error: ",e);
            return new ResponseEntity<>(new ResponseObject(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<ResponseObject> updateBrand(@PathVariable Long id,@RequestBody BrandRequest brandRequest){
        try{
            BrandResponse brand = brandService.updateBrand(id,brandRequest);
            return new ResponseEntity<>(new ResponseObject("Cập nhập thông tin nhãn hiệu thành công", brand), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            log.error("Error: ",e);
            return new ResponseEntity<>(new ResponseObject(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }catch(DataConflictException e){
            log.error("Error: ",e);
            return new ResponseEntity<>(new ResponseObject(e.getMessage(), null), HttpStatus.CONFLICT);
        }catch(Exception e){
            log.error("Error: ",e);
            return new ResponseEntity<>(new ResponseObject(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNewBrand(@RequestBody BrandRequest brandRequest){
        try{
            BrandResponse brand = brandService.createNewBrand(brandRequest);
            return new ResponseEntity<>(new ResponseObject("Tạo nhãn hiệu mới thành công", brand), HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            log.error("Error: ",e);
            return new ResponseEntity<>(new ResponseObject(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }catch(DataConflictException e){
            log.error("Error: ",e);
            return new ResponseEntity<>(new ResponseObject(e.getMessage(), null), HttpStatus.CONFLICT);
        }catch(Exception e){
            log.error("Error: ",e);
            return new ResponseEntity<>(new ResponseObject(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrandById(@PathVariable Long id){
        try{
            Boolean checkk = brandService.deleteBrandById(id);
            if(checkk)
                return new ResponseEntity<>("Xóa nhãn hiệu thành công", HttpStatus.OK);
            else
                return new ResponseEntity<>("Có lỗi khi xóa nhãn hiệu", HttpStatus.BAD_REQUEST);
        }catch(ResourceNotFoundException e){
            log.error("Error: ",e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            log.error("Error: ",e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
