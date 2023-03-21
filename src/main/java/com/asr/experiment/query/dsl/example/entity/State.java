package com.asr.experiment.query.dsl.example.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;

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

  @ManyToOne(
      cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH },
      optional = false)
  @JoinColumn(
      name = "country_id",
      nullable = false)
  private Country country;

  @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CityAndStateRelation> cityList = new LinkedHashSet<>();
}
