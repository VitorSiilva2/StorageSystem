package org.example.storagesystem.controller;

import jakarta.validation.constraints.NotEmpty;
import org.example.storagesystem.dto.LocationNameDto;
import org.example.storagesystem.dto.ProductModelDto;
import org.example.storagesystem.dto.ProductSkuDto;
import org.example.storagesystem.model.LocationModel;
import org.example.storagesystem.model.ProductModel;
import org.example.storagesystem.service.LocationService;
import org.example.storagesystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.RuntimeErrorException;
import java.util.Set;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private LocationService locationService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductModel saveProduct(@RequestBody ProductModelDto productModelDto) {
        Set<LocationModel> locations = productModelDto.getLocation();

        for (LocationModel location : locations) {
            if (!locationService.findPosition(location.getId()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Localização não encontrada: " + location.getPosition());
            }

        }
        ProductModel productModel = productService.saveProduct(productModelDto);
        return productModel;

    }

    @RequestMapping(method = RequestMethod.POST, value = "/transfer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void transferProduct(@RequestBody ProductSkuDto productSkuDto, LocationNameDto locationNameDto) {
        productService.transferProduct(productSkuDto, locationNameDto);
    }
}
