package com.asr.experiment.query.dsl.example.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.UUID;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Embeddable
public class CityAndStateCombo {

  public static final String STATE_COL = "state_id";

  public static final String CITY_COL = "city_id";

  @ToString.Include
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = CityAndStateCombo.STATE_COL, nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  @Schema(description = "Unique id for the State", accessMode = AccessMode.READ_ONLY)
  UUID stateId;

  @ToString.Include
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = CityAndStateCombo.CITY_COL, nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  @Schema(description = "Unique id for the City", accessMode = AccessMode.READ_ONLY)
  UUID cityId;
}
