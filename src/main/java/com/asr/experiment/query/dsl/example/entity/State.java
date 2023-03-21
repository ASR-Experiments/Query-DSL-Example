package com.asr.experiment.query.dsl.example.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TBL_STATE", uniqueConstraints = { @UniqueConstraint(name = "UQ_STATE", columnNames = { "name" }) })
@AttributeOverride(name = "id", column = @Column(name = "state_id"))
@NoArgsConstructor
public class State extends AbstractBaseEntity {

  @ToString.Include
  @Setter(AccessLevel.NONE)
  @Column(nullable = false, updatable = false)
  String name;

  @ManyToOne(optional = false)
  @JoinColumn(name = "country_id", nullable = false)
  private Country country;

  @Builder.Default
  @ManyToMany(mappedBy = "states", cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE,
      CascadeType.REFRESH,
      CascadeType.DETACH })
  private Set<City> cities = new LinkedHashSet<>();

}
