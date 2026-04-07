package com.app.quantity_measurement_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.app.quantity_measurement_app.model.Conversion;
import com.app.quantity_measurement_app.repository.ConversionRepository;

@Service
public class QuantityService {

    @Autowired
    private ConversionRepository repo;

    public double convert(double value, String from, String to) {

        from = from.toLowerCase();
        to = to.toLowerCase();

        double result;

        if(from.equals("inch") && to.equals("feet")) result = value / 12;
        else if(from.equals("feet") && to.equals("inch")) result = value * 12;

        else if(from.equals("gallon") && to.equals("litre")) result = value * 3.785;
        else if(from.equals("litre") && to.equals("gallon")) result = value / 3.785;

        else if(from.equals(to)) result = value;

        else throw new RuntimeException("Invalid conversion");

        Conversion c = new Conversion(value, from, to, result);
        repo.save(c);

        return result;
    }

    public List<Conversion> getAllHistory() {
        return repo.findAll();
    }
}