package fr.xebia.training.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.validator.constraints.NotEmpty;

import java.time.Instant;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InputData {

  @NotNull
  private UUID smartphoneId;

  @Valid
  private Type type;

  @NotEmpty
  private Instant eventTime;

  @NotEmpty
  private String value;

  public UUID getSmartphoneId() {
    return smartphoneId;
  }

  public void setSmartphoneId(UUID smartphoneId) {
    this.smartphoneId = smartphoneId;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Instant getEventTime() {
    return eventTime;
  }

  public void setEventTime(Instant eventTime) {
    this.eventTime = eventTime;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "InputData{" +
           "smartphoneId=" + smartphoneId +
           ", type=" + type +
           ", eventTime='" + eventTime + '\'' +
           ", value='" + value + '\'' +
           '}';
  }
}
