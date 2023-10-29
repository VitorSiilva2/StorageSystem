package org.example.storagesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductConsultPositionDto {
    private String title;
    private int quantity;
    private String productSKU;
}
