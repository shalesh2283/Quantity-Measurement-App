package com.app.quantity_measurement_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.app.quantity_measurement_app.dto.ConversionRequest;
import com.app.quantity_measurement_app.dto.ConversionResponse;
import com.app.quantity_measurement_app.service.QuantityService;
import com.app.quantity_measurement_app.model.Conversion;
import java.util.*;

@RestController
@RequestMapping("/quantity")
public class QuantityController {

    @Autowired
    private QuantityService service;

    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convert(@Valid @RequestBody ConversionRequest request) {

        double result = service.convert(
                request.getValue(),
                request.getFromUnit(),
                request.getToUnit()
        );

        ConversionResponse response =
                new ConversionResponse(result, "Conversion successful");

        return ResponseEntity.ok(response);
    }
    @GetMapping("/history")
    public ResponseEntity<List<Conversion>> getHistory() {
        return ResponseEntity.ok(service.getAllHistory());
    }
}