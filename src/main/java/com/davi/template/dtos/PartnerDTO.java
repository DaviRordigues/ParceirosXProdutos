package com.davi.template.dtos;

import com.davi.template.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerDTO {
	private String id;
	private String name;
	private List<ProductEntity> products;
}
