package com.davi.template.controllers;

import com.davi.template.entity.PartnerEntity;

import com.davi.template.service.PartnerService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<List<PartnerEntity>> getAllPartners() {
        List<PartnerEntity> partners = partnerService.getAllPartners();
        return ResponseEntity.ok(partners);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerEntity> getPartnerById(@PathVariable String id) {
        PartnerEntity partner = partnerService.getPartnerById(id);
        if (partner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(partner);
    }

    @PostMapping
    public ResponseEntity<PartnerEntity> createPartner(@RequestBody PartnerEntity partner) {
        PartnerEntity createdPartner = partnerService.createPartner(partner);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPartner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartnerEntity> updatePartner(@PathVariable String id, @RequestBody PartnerEntity partnerDetails) {
        PartnerEntity updatedPartner = partnerService.updatePartner(id, partnerDetails);
        if (updatedPartner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPartner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable String id) {
        boolean deleted = partnerService.deletePartner(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
