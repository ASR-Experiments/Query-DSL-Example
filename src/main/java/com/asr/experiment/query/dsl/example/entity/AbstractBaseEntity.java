package com.asr.experiment.query.dsl.example.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import java.util.Date;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
public class AbstractBaseEntity {

  public static final String COLUMN_VERSION_NAME = "version";

  @ToString.Include
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  @Schema(description = "Unique id for the Entity", accessMode = AccessMode.READ_ONLY)
  private UUID id;

  @Setter(AccessLevel.NONE)
  @Version
  @Schema(description = "Latest version for the Entity", accessMode = AccessMode.READ_ONLY)
  @Column(name = COLUMN_VERSION_NAME)
  private Integer version;

  @Setter(AccessLevel.NONE)
  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  @Column(nullable = false, updatable = false)
  @Schema(description = "Time when entity was created", accessMode = AccessMode.READ_ONLY)
  private Date createdDate;

  @Setter(AccessLevel.NONE)
  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  @Schema(description = "Latest Time when Entity was updated", accessMode = AccessMode.READ_ONLY)
  private Date lastModifiedDate;

}