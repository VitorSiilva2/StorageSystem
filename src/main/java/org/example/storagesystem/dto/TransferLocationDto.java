package org.example.storagesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferLocationDto {

        private ProductSkuDto sku;
        private LocationNameDto locationFrom;
        private LocationNameDto locationTo;
}
