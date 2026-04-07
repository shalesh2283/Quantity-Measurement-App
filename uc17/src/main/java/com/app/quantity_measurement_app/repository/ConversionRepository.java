package com.app.quantity_measurement_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.quantity_measurement_app.model.Conversion;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {
}