package org.example.storagesystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.storagesystem.dto.LocationNameDto;
import org.example.storagesystem.dto.ProductModelDto;
import org.example.storagesystem.dto.ProductSkuDto;
import org.example.storagesystem.dto.TransferLocationDto;
import org.example.storagesystem.model.LocationModel;
import org.example.storagesystem.model.ProductModel;
import org.example.storagesystem.repository.LocationRepository;
import org.example.storagesystem.repository.ProductRepository;
import org.example.storagesystem.service.serviceInterfaceImpl.ProductServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

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
    @Transactional
    public ProductSkuDto transferProduct(TransferLocationDto transferLocationDto) {

        LocationNameDto fromLocation = transferLocationDto.getLocationFrom();
        ProductSkuDto product = transferLocationDto.getSku();
        LocationNameDto toLocation = transferLocationDto.getLocationTo();

        Optional<LocationModel> fromLocationModel = locationRepository.findByPositionName(fromLocation.getPosition());
        Optional<LocationModel> toLocationModel = locationRepository.findByPositionName(toLocation.getPosition());
        Optional<ProductModel> productModel = productRepository.findBySku(product.getProductSKU());

        if (fromLocationModel.isPresent() && toLocationModel.isPresent() && productModel.isPresent()) {

            LOGGER.debug("Iniciando transferProduct...");


            LocationModel from = fromLocationModel.get();
            LocationModel to = toLocationModel.get();
            ProductModel productSku = productModel.get();

            productSku.getLocation().remove(from);
            productSku.getLocation().add(to);


            locationRepository.save(to);
            LOGGER.debug("Localização 'to' salva com sucesso.");

            locationRepository.save(from);
            LOGGER.debug("Localização 'from' salva com sucesso.");

            LOGGER.debug("finalizando transferProduct...");


            return product;

        } else {
            throw new EntityNotFoundException("Não foi possível encontrar a localização ou o produto.");
        }
    }

    /*private LocationModel validationPosition(LocationNameDto locationNameDto) {
        var location = locationRepository.findByPositionName(locationNameDto.getLocationNameDtoFrom().getPosition());
        if(location.isPresent()) {
            return location.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Localização não encontrada.");
        }
    }*/

    /*private ProductModel validationProductInPosition(ProductSkuDto productSkuDto) {
        var product = productRepository.findBySku(productSkuDto.getProductSKU());
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado.");

        }
    }*/

}
