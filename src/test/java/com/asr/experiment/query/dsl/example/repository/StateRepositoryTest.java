//package com.asr.experiment.query.dsl.example.repository;
//
//import com.asr.experiment.query.dsl.example.config.DefaultJpaTestConfiguration;
//import com.asr.experiment.query.dsl.example.entity.Country;
//import com.asr.experiment.query.dsl.example.entity.State;
//import jakarta.persistence.EntityManager;
//import java.util.Optional;
//import java.util.UUID;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.annotation.DirtiesContext.MethodMode;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.util.ObjectUtils;
//
//@ActiveProfiles("local")
//@DataJpaTest
//@ContextConfiguration(classes = DefaultJpaTestConfiguration.class)
////@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
//class StateRepositoryTest {
//
//  private static Logger LOGGER;
//
//  @BeforeAll
//  static void beforeAll() {
//    LOGGER = LoggerFactory.getLogger(StateRepositoryTest.class);
//  }
//
//  @Autowired
//  CountryRepository countryRepository;
//
//  @Autowired
//  StateRepository stateRepository;
//
//  @Autowired
//  EntityManager entityManager;
//
//  private Country currentCountry;
//
//  @BeforeEach
//  void setUp() {
//    Country newCountry = Country.builder().name(UUID.randomUUID().toString()).build();
//    currentCountry = countryRepository.save(newCountry);
//  }
//
//  @AfterEach
//  void tearDown() {
//    countryRepository.delete(currentCountry);
//  }
//
//  @Test
//  void testCreateState() {
//
//    State newState = State.builder().name("State1_" + UUID.randomUUID()).country(currentCountry).build();
//    State savedState = stateRepository.saveAndFlush(newState);
//
//    Assertions.assertEquals(newState.getName(), savedState.getName());
//    Assertions.assertEquals(newState.getCountry(), savedState.getCountry());
//    Assertions.assertNotNull(newState.getId());
//    Assertions.assertTrue(ObjectUtils.isEmpty(savedState.getCities()));
//  }
//
//  @Test
//  void testDeleteState_whenStateIsDeleted_thenKeepCountry() {
//
//    State newState = State.builder().name("State2_" + UUID.randomUUID()).country(currentCountry).build();
//    State savedState = stateRepository.saveAndFlush(newState);
//
//    UUID id = savedState.getId();
//    entityManager.remove(savedState);
//    entityManager.flush();
//
//    Optional<State> state = stateRepository.findById(id);
//    Optional<Country> existingCountry = countryRepository.findById(currentCountry.getId());
//
//    Assertions.assertTrue(state.isEmpty());
//    Assertions.assertTrue(existingCountry.isPresent());
//  }
//
//  @Test
//  void testDeleteState_whenCountryIsDeleted_thenDeleteState() {
//    Country newCountry = Country.builder().name(UUID.randomUUID().toString()).build();
//    currentCountry = countryRepository.saveAndFlush(newCountry);
//    LOGGER.info("=====> After Saving Country");
//
//    State newState = State.builder().name("State3_" + UUID.randomUUID()).country(currentCountry).build();
//    State savedState = stateRepository.saveAndFlush(newState);
//    LOGGER.info("=====> After Saving City");
//
//    UUID id = savedState.getId();
//    entityManager.remove(currentCountry);
//    entityManager.flush();
//    LOGGER.info("=====> After Deleting Country");
//
//    Optional<State> state = stateRepository.findById(id);
//    Optional<Country> existingCountry = countryRepository.findById(currentCountry.getId());
//    LOGGER.info("=====> After Finding Country");
//
//    Assertions.assertTrue(state.isEmpty());
//    Assertions.assertTrue(existingCountry.isEmpty());
//  }
//}