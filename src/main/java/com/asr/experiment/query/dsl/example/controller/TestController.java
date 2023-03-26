package com.asr.experiment.query.dsl.example.controller;

import com.asr.experiment.query.dsl.example.entity.City;
import com.asr.experiment.query.dsl.example.entity.CityAndStateRelation;
import com.asr.experiment.query.dsl.example.entity.Country;
import com.asr.experiment.query.dsl.example.entity.State;
import com.asr.experiment.query.dsl.example.entity.State.StateBuilder;
import com.asr.experiment.query.dsl.example.repository.CityRepository;
import com.asr.experiment.query.dsl.example.repository.CountryRepository;
import com.asr.experiment.query.dsl.example.repository.StateRepository;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to test basic functionality
 */
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TestController {

  CountryRepository countryRepository;

  StateRepository stateRepository;

  CityRepository cityRepository;

  /**
   * API to create a state with its relations with country and city
   * @param stateName   [Mandatory] Name of the new state
   * @param countryName [Optional] Name of the new or existing country
   * @param cityNames   [Optional] Name of the new or existing city
   * @return Basic (not all) details of the newly created state
   */
  @Transactional
  @PostMapping("/state")
  public ResponseEntity<Object> newState(@RequestParam String stateName,
                                         @RequestParam(required = false) String countryName,
                                         @RequestParam(required = false) Set<String> cityNames) {
    if (!StringUtils.hasText(stateName)) {
      return ResponseEntity.badRequest().build();
    }

    StateBuilder<?, ?> stateBuilder = State.builder().name(stateName);

    if (StringUtils.hasText(countryName)) {
      Optional<Country> foundCountry = countryRepository.findByName(countryName);
      Country country = foundCountry.orElse(Country.builder().name(countryName).build());
      stateBuilder.country(country);
    }

    State newState;
    if (!CollectionUtils.isEmpty(cityNames)) {
      Set<CityAndStateRelation> collectedCities = cityNames.stream().filter(StringUtils::hasText)
                                                           .map(cityName -> City.builder().name(cityName).build()).map(
              city -> CityAndStateRelation.builder().city(city).build()).collect(Collectors.toSet());

      if (!collectedCities.isEmpty()) {
        stateBuilder.cityList(collectedCities);
      }

      newState = stateBuilder.build();
      Set<CityAndStateRelation> cityList = newState.getCityList();
      if (!ObjectUtils.isEmpty(cityList)) {
        cityList.forEach(cityAndStateRelation -> cityAndStateRelation.setState(newState));
      }
    } else {
      newState = stateBuilder.build();
    }

    State savedState = stateRepository.save(newState);

    return ResponseEntity.status(HttpStatus.CREATED).body(copyState(savedState));
  }

  @Transactional
  @DeleteMapping("/city")
  public ResponseEntity<Object> deleteCity(@RequestParam String cityName) {

    Optional<City> city = cityRepository.findByName(cityName);

    if (city.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    City returnCity = this.copyCity(city.get());

    cityRepository.delete(city.get());

    return ResponseEntity.accepted().body(returnCity);
  }

  @Transactional
  @DeleteMapping("/state")
  public ResponseEntity<Object> deleteState(@RequestParam String stateName) {

    Optional<State> state = stateRepository.findByName(stateName);

    if (state.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    State returnState = copyState(state.get());

    stateRepository.deleteByName(stateName);

    return ResponseEntity.accepted().body(returnState);
  }

  @Transactional
  @DeleteMapping("/country")
  public ResponseEntity<Object> deleteCountry(@RequestParam String countryName) {

    Optional<Country> country = countryRepository.findByName(countryName);

    if (country.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Country returnState = copyCountry(country.get());

    countryRepository.deleteByName(countryName);

    return ResponseEntity.accepted().body(returnState);
  }

  private City copyCity(City city) {
    return City.builder().name(city.getName()).id(city.getId()).build();
  }

  private State copyState(State savedState) {
    StateBuilder<?, ?> returnState = State.builder().name(savedState.getName()).id(savedState.getId());
    if (savedState.getCountry() != null) {
      returnState.country(copyCountry(savedState.getCountry()));
    }
    return returnState.build();
  }

  private Country copyCountry(Country savedCountry) {
    return Country.builder().name(savedCountry.getName()).id(savedCountry.getId()).build();
  }

  @GetMapping("/all/country")
  public List<Country> getCountry(@QuerydslPredicate(root = Country.class) Predicate predicate) {
    List<Country> countries = new ArrayList<>();
    for (Country country : countryRepository.findAll(predicate)) {
      countries.add(copyCountry(country));
    }

    return countries;
  }

}
