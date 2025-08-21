package com.api.outsera;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/producers")
public class ProducerController {

    private final ProducerIntervalService service;

    public ProducerController(ProducerIntervalService service) {
        this.service = service;
    }

    @GetMapping("/intervals")
    public ResponseEntity<Map<String, List<IntervalDTO>>> getIntervals() {
        return ResponseEntity.ok(service.computeIntervals());
    }
}
