package fr.xebia.training.domain.model;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.UUID;

import fr.xebia.training.Application;

@Table(keyspace = Application.KEYSPACE, name = "smartphones")
public class Smartphone {

  @PartitionKey
  private UUID id;

  @NotEmpty
  private String name;

  @NotEmpty
  private String constructor;

  @NotEmpty
  private String model;


  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getConstructor() {
    return constructor;
  }

  public void setConstructor(String constructor) {
    this.constructor = constructor;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }
}
