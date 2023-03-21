package com.asr.experiment.query.dsl.example.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@Table(
    name = "TBL_COUNTRY",
    uniqueConstraints = {
        @UniqueConstraint(name = "UQ_COUNTRY", columnNames = { "name" })
    })
@AttributeOverride(name = "id", column = @Column(name = "country_id"))
@NoArgsConstructor
public class Country extends AbstractBaseEntity {
  @ToString.Include
  @Setter(AccessLevel.NONE)
  @Column(nullable = false, updatable = false)
  String name;

  @Builder.Default
  @OneToMany(
      mappedBy = "country",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Set<State> states = new LinkedHashSet<>();

}
