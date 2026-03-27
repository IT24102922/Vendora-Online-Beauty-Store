package com.group02.vendora.controller;

import com.group02.vendora.dto.AddNoteDTO;
import com.group02.vendora.dto.DeliveryDTO;
import com.group02.vendora.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/deliveryagent")
@CrossOrigin(origins = "*")
public class DeliveryAgentController {

    private final DeliveryService deliveryService;

    public DeliveryAgentController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/deliveries")
    public ResponseEntity<List<DeliveryDTO>> getMyDeliveries(Principal principal) {
        return ResponseEntity.ok(deliveryService.getMyAssignedDeliveries(principal.getName()));
    }

    @PostMapping("/deliveries/{id}/accept")
    public ResponseEntity<DeliveryDTO> accept(
            @PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(deliveryService.accept(id, principal.getName()));
    }

    @PostMapping("/deliveries/{id}/reject")
    public ResponseEntity<DeliveryDTO> reject(
            @PathVariable Long id, Principal principal,
            @RequestBody AddNoteDTO dto) {
        return ResponseEntity.ok(deliveryService.reject(id, principal.getName(), dto));
    }

    @PostMapping("/deliveries/{id}/pickup")
    public ResponseEntity<DeliveryDTO> pickup(
            @PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(deliveryService.pickup(id, principal.getName()));
    }

    @PostMapping("/deliveries/{id}/complete")
    public ResponseEntity<DeliveryDTO> complete(
            @PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(deliveryService.complete(id, principal.getName()));
    }

    @PostMapping("/deliveries/{id}/fail")
    public ResponseEntity<DeliveryDTO> fail(
            @PathVariable Long id, Principal principal,
            @RequestBody AddNoteDTO dto) {
        return ResponseEntity.ok(deliveryService.fail(id, principal.getName(), dto));
    }

    @PostMapping("/deliveries/{id}/pickup-return")
    public ResponseEntity<DeliveryDTO> pickupReturn(
            @PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(deliveryService.pickupReturn(id, principal.getName()));
    }

    @PostMapping("/deliveries/{id}/complete-return")
    public ResponseEntity<DeliveryDTO> completeReturn(
            @PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(deliveryService.completeReturn(id, principal.getName()));
    }
}