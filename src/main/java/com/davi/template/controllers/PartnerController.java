package com.davi.template.controllers;

import com.davi.template.dtos.PartnerDTO;
import com.davi.template.dtos.requests.PartnerRequestDTO;
import com.davi.template.service.PartnerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partners")
@AllArgsConstructor
public class PartnerController {
	
	private final PartnerService partnerService;

	@GetMapping
	//TODO: 0 e 10 sao default, voce nao precisa declaralos caso passar essas quantidades, @PageableDefault também nao se faz necessário
	public ResponseEntity<List<PartnerDTO>> getAllPartners(
			@PageableDefault(page = 0, size = 10) Pageable pageable) {
		List<PartnerDTO> partners = partnerService.getAllPartners(pageable);
		return ResponseEntity.ok(partners);
	}
	@GetMapping("/{id}")
	public ResponseEntity<PartnerDTO> getPartnerById(@PathVariable String id) {
		return ResponseEntity.ok(partnerService.getPartnerById(id));
	}
	@PostMapping
	public ResponseEntity<PartnerDTO> createPartner(@RequestBody PartnerRequestDTO partnerRequestDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(partnerService.createPartner(partnerRequestDTO));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PartnerDTO> updatePartner(@PathVariable String id,
	                                                @RequestBody PartnerRequestDTO partnerRequestDTO) {
		return ResponseEntity.ok(partnerService.updatePartner(id, partnerRequestDTO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePartner(@PathVariable String id) {
		partnerService.deletePartner(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/bulk-create")
	public ResponseEntity<Void> createBulkPartners() {
		partnerService.createBulkPartners();
		return ResponseEntity.ok().build();
	}
}
