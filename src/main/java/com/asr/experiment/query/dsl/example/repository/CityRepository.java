package com.asr.experiment.query.dsl.example.repository;

import com.asr.experiment.query.dsl.example.entity.City;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface CityRepository extends JpaRepository<City, UUID> {
  Optional<City> findByName(String cityName);
}