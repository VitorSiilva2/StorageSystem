package org.example.storagesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.storagesystem.model.LocationModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModelDto {

    private String title;
    private String description;
    private BigDecimal price;
    private String productSKU;
    private Integer quantity;
    private Set<LocationModel> location;

}
