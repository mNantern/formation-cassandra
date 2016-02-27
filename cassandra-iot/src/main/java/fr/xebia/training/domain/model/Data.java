package fr.xebia.training.domain.model;

import java.time.Instant;
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

  public static class Builder {

    private Data tmp = new Data();

    public Builder id(UUID uuid){
      tmp.id = uuid;
      return this;
    }

    public Builder smartphoneId(UUID smartphoneId){
      tmp.setSmartphoneId(smartphoneId);
      return this;
    }

    public Builder type(Type type){
      tmp.setType(type);
      return this;
    }

    public Builder eventTime(Instant eventTime){
      tmp.setEventTime(eventTime);
      return this;
    }

    public Builder value(String value){
      tmp.setValue(value);
      return this;
    }

    public Data build(){
      // TODO: validate tmp before returning
      return tmp;
    }
  }
}
