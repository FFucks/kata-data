package com.example.consumer.controller;

import com.example.consumer.entity.TopCity;
import com.example.consumer.repository.TopCityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {

    private final TopCityRepository repository;

    public ResultController(TopCityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/top-city")
    public List<TopCity> getTopCity() {
        return repository.findAll();
    }
}
