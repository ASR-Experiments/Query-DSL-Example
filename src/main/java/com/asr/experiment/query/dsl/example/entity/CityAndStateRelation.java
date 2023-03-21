package com.asr.experiment.query.dsl.example.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@SuperBuilder
@Getter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = CityAndStateRelation.TABLE_NAME)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CityAndStateRelation {

  public static final String TABLE_NAME = "TBL_CITY_FOR_STATES";

  @ToString.Include
  @EmbeddedId
  CityAndStateCombo cityAndStateCombo;

  @Setter(AccessLevel.NONE)
  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  @Column(nullable = false, updatable = false)
  @Schema(description = "Time when entity was created", accessMode = AccessMode.READ_ONLY)
  private Date createdDate;

  @MapsId("cityId")
  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = CityAndStateCombo.CITY_COL, nullable = false)
  private City city;

  @MapsId("stateId")
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
  @JoinColumn(name = CityAndStateCombo.STATE_COL, nullable = false)
  private State state;

  public void setCity(City city) {
    this.city = city;
    if (this.cityAndStateCombo == null) {
      this.cityAndStateCombo = new CityAndStateCombo();
    }
    if (this.city.getId() != null) {
      this.cityAndStateCombo.cityId = this.city.getId();
    }
  }

  public void setState(State state) {
    this.state = state;
    if (this.cityAndStateCombo == null) {
      this.cityAndStateCombo = new CityAndStateCombo();
    }
    if (this.state.getId() != null) {
      this.cityAndStateCombo.stateId = this.state.getId();
    }
  }
}
