package fr.xebia.training.domain.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Address {

  @NotEmpty
  private String street;
  @NotEmpty
  private String city;

  private int zipCode;

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public int getZipCode() {
    return zipCode;
  }

  public void setZipCode(int zipCode) {
    this.zipCode = zipCode;
  }

  public static class Builder {

    private Address tmp = new Address();

    public Builder street(String street){
      tmp.street = street;
      return this;
    }

    public Builder city(String city){
      tmp.city = city;
      return this;
    }

    public Builder zipCode(int zipCode){
      tmp.zipCode = zipCode;
      return this;
    }

    public Address build(){
      // TODO: validate tmp before returning
      return tmp;
    }
  }

}
