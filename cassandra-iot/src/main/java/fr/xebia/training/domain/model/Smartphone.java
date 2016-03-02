package fr.xebia.training.domain.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.UUID;

//US04 : annoter convenablement la classe Smartphone
public class Smartphone {

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
