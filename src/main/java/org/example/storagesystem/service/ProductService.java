package org.example.storagesystem.service;

import lombok.RequiredArgsConstructor;
import org.example.storagesystem.dto.LocationNameDto;
import org.example.storagesystem.dto.ProductModelDto;
import org.example.storagesystem.dto.ProductSkuDto;
import org.example.storagesystem.model.LocationModel;
import org.example.storagesystem.model.ProductModel;
import org.example.storagesystem.repository.LocationRepository;
import org.example.storagesystem.repository.ProductRepository;
import org.example.storagesystem.service.serviceInterfaceImpl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ProductService implements ProductServiceImpl {

    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;

    @Override
    public ProductModel saveProduct(ProductModelDto productModelDto) {

        if (!productRepository.findBySku(productModelDto.getProductSKU()).isPresent()) {

            ProductModel productModel = new ProductModel();
            productModel.setTitle(productModelDto.getTitle());
            productModel.setDescription(productModelDto.getDescription());
            productModel.setPrice(productModelDto.getPrice());
            productModel.setProductSKU(productModelDto.getProductSKU());
            productModel.setQuantity(productModelDto.getQuantity());
            productModel.setLocation(productModelDto.getLocation());

            productRepository.save(productModel);

            return productModel;
        } else {

            Optional<ProductModel> existingProduct = Optional.empty();

            Optional<ProductModel> existingProductCheck = productRepository.findBySku(productModelDto.getProductSKU());
            if (existingProductCheck.isPresent()) {
                existingProduct = existingProductCheck;
            }

            if (existingProductCheck.isPresent()) {
                ProductModel productModel = existingProduct.get();
                int newQuantity = productModelDto.getQuantity() + productModel.getQuantity();
                productModel.setQuantity(newQuantity);
                productRepository.save(productModel);
                return productModel;
            }

        }
        return null;
    }

    @Override
    public ProductModel transferProduct(ProductSkuDto productSkuDto, LocationNameDto locationNameDto) {

        LocationModel fromLocation = validationPosition(locationNameDto);

        ProductModel product = validationProductInPosition(productSkuDto);

        LocationNameDto toLocationDto = new LocationNameDto();
        toLocationDto.setPosition("2");
        LocationModel toLocation = validationPosition(toLocationDto);

        if(fromLocation.getId().equals(toLocation.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Posição deve ser diferente");
        }

        fromLocation.getProduct().remove(product);
        locationRepository.save(fromLocation);

        toLocation.getProduct().add(product);
        locationRepository.save(toLocation);

        product.setLocation((Set<LocationModel>) toLocation);
        productRepository.save(product);



        return product;
    }

    private LocationModel validationPosition(LocationNameDto locationNameDto) {

        var location = locationRepository.findByPositionName(locationNameDto.getPosition());

        if(location.isPresent()) {
            return location.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Localização não encontrada.");

        }
    }

    private ProductModel validationProductInPosition(ProductSkuDto productSkuDto) {
        var product = productRepository.findBySku(productSkuDto.getProductSKU());
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado.");

        }
    }

}
