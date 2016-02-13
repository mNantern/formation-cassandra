package fr.xebia.training.domain.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class Data extends InputData {

  @NotNull
  private UUID id;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Data{" +
           "id=" + id +
           ", smartphoneId=" + getSmartphoneId() +
           ", type=" + getType() +
           ", eventTime='" + getEventTime() + '\'' +
           ", value='" + getValue() + '\'' +
           '}';
  }
}
