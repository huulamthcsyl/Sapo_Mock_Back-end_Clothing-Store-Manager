package sapo.com.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import sapo.com.model.entity.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ProductRequest {
    @Valid
    @NotBlank(message = "Product name is required")
    private String name;
    private Long categoryId;
    private Long brandId;
    private String description;
    private Set<String> imagePath;
    @Valid
    @NotEmpty(message = "Set of variant is required")
    private Set<VariantRequest> variants;

    //Do not have brand, category, variant
    public Product transferToProduct(){
        Product product= new Product();
        product.setName(this.name);
        product.setDescription(this.description);
        Set<ImagePath> imagePaths = this.imagePath.stream()
                .map(path -> {
                    ImagePath imagePath = new ImagePath();
                    imagePath.setPath(path);
                    imagePath.setProduct(product);  // Set the product reference in imagePath
                    return imagePath;
                }).collect(Collectors.toSet());
        product.setImagePath(imagePaths);
        Set<Variant> variants = this.variants.stream()
                .map(variant -> {
                    Variant variantItem = variant.transferToVariant();
                    variantItem.setProduct(product);
                    return variantItem;
                }).collect(Collectors.toSet());
        product.setVariants(variants);
        return product;
    }
}
