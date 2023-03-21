package com.asr.experiment.query.dsl.example.repository;

import com.asr.experiment.query.dsl.example.entity.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class CountryRepositoryTest {

  @Autowired
  CountryRepository countryRepository;

  @Test
  void testSave_whenNewCountry() {
    Country newCountry = Country.builder().name("NewCountry").build();

    Country savedCountry = countryRepository.save(newCountry);

    Assertions.assertEquals(newCountry.getName(), savedCountry.getName());
    Assertions.assertNotNull(savedCountry.getId());
  }
}