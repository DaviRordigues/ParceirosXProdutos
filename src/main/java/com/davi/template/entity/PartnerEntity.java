package com.davi.template.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "PARTNERS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerEntity {
	@Id
	@Indexed
	private String id;
	private String name;
	private List<ProductEntity> products;
}
