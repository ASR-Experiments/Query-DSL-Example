package com.asr.experiment.query.dsl.example.repository;

import com.asr.experiment.query.dsl.example.entity.State;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface StateRepository extends JpaRepository<State, UUID> {

  @Modifying
  void deleteByName(String stateName);

  Optional<State> findByName(String stateName);
}