package org.example.storagesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.storagesystem.model.ProductModel;

import javax.swing.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationConsultModelDto {

    private String position;
    private Set<ProductConsultPositionDto> product;
}
