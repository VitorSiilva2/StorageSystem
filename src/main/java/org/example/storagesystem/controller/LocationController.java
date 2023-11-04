package org.example.storagesystem.controller;

import org.example.storagesystem.dto.LocationConsultModelDto;
import org.example.storagesystem.dto.ProductConsultPositionDto;
import org.example.storagesystem.model.LocationModel;
import org.example.storagesystem.model.ProductModel;
import org.example.storagesystem.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public LocationModel locationModel(@RequestBody LocationModel locationModel) {
        LocationModel newPosition = locationService.createPosition(locationModel);
        return newPosition;
    }

    @GetMapping(value = "/consult/{id}")
    public Optional<LocationConsultModelDto> locationModel(@PathVariable Integer id) {
        LocationModel locationModel = locationService.findPosition(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found."));

        return Optional.ofNullable(LocationConsultModelDto.builder()
                .position(locationModel.getPosition())
                .product(converterProductDto(locationModel.getProduct()))
                .build());
    }

    private Set<ProductConsultPositionDto> converterProductDto(Set<ProductModel> productModel) {
        return productModel.stream()
                .map(productModelConverter -> ProductConsultPositionDto.builder()
                        .title(productModelConverter.getTitle())
                        .productSKU(productModelConverter.getProductSKU())
                        .quantity(productModelConverter.getQuantity())
                        .build())
                .collect(Collectors.toSet());
    }

    @GetMapping(value = "/consult/test/{position}")
    public Optional<LocationConsultModelDto> locationModelConsult(@PathVariable String position) {
        LocationModel locationModel = locationService.findByPositionName(position)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found."));

        return Optional.ofNullable(LocationConsultModelDto.builder()
                .position(locationModel.getPosition())
                .product(converterProductDto(locationModel.getProduct()))
                .build());
    }


}
