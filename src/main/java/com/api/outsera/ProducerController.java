package com.api.outsera;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtores")
public class ProducerController {

    private final ProdutorIntervaloservice service;

    public ProducerController(ProdutorIntervaloservice service) {
        this.service = service;
    }

    @GetMapping("/intervalos")
    public ResponseEntity<Map<String, List<IntervalDTO>>> getintervalos() {
        return ResponseEntity.ok(service.computeintervalos());
    }
}
