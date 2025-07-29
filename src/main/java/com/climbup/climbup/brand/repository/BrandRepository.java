package com.climbup.climbup.brand.repository;

import com.climbup.climbup.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT b FROM Brand b LEFT JOIN FETCH b.gyms")
    List<Brand> findAllWithGyms();

    Optional<Brand> findByName(String name);
}