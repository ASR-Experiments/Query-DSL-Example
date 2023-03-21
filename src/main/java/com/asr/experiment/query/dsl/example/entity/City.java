package com.asr.experiment.query.dsl.example.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@Table(
    name = "TBL_CITY",
    uniqueConstraints = {
        @UniqueConstraint(name = "UQ_CITY", columnNames = { "name" })
    })
@AttributeOverride(name = "id", column = @Column(name = "city_id"))
@NoArgsConstructor
public class City extends AbstractBaseEntity {

  static final String CITY_FOR_STATES_TABLE = "TBL_CITY_FOR_STATES";

  @ToString.Include
  @NotBlank @NotEmpty @NotNull
  @Setter(AccessLevel.NONE)
  @Column(nullable = false, updatable = false)
  String name;

  @Builder.Default
  @ManyToMany
  @JoinTable(name = City.CITY_FOR_STATES_TABLE,
             joinColumns = @JoinColumn(name = "city_id"),
             inverseJoinColumns = @JoinColumn(name = "state_id"),
             uniqueConstraints = {
                 @UniqueConstraint(name = "UQ_CITY_AND_STATE_COMBINATION", columnNames = { "city_id", "state_id" })
             })
  private Set<State> states = new LinkedHashSet<>();

}
