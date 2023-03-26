package com.asr.experiment.query.dsl.example.repository;

import com.asr.experiment.query.dsl.example.entity.Country;
import com.asr.experiment.query.dsl.example.entity.QCountry;
import com.querydsl.core.types.dsl.StringPath;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@Tag(name = "Country Controller", description = "CRUD operations for Country")
@RepositoryRestResource
public interface CountryRepository extends
    JpaRepository<Country, UUID>,
    QuerydslPredicateExecutor<Country>,
    QuerydslBinderCustomizer<QCountry> {

  @Override
  default void customize(QuerydslBindings bindings, QCountry root) {
    bindings.including(root.createdDate);
    bindings.including(root.lastModifiedDate);
    bindings.including(root.version);
    bindings.including(root.name);
    bindings.excluding(root.states);
    // Pass
  }

  Optional<Country> findByName(String countryName);

  @Modifying
  void deleteByName(String countryName);
}