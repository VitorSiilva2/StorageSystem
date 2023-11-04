package org.example.storagesystem.service.serviceInterfaceImpl;

import org.example.storagesystem.dto.ProductModelDto;
import org.example.storagesystem.dto.ProductSkuDto;
import org.example.storagesystem.dto.TransferLocationDto;
import org.example.storagesystem.model.ProductModel;

public interface ProductServiceImpl {

    ProductModel saveProduct(ProductModelDto productModelDto);

    ProductSkuDto transferProduct(TransferLocationDto transferLocationDto);

}
