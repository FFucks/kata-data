package com.example.consumer.repository;

import com.example.consumer.entity.TopCity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopCityRepository extends JpaRepository<TopCity, Long> {
}
