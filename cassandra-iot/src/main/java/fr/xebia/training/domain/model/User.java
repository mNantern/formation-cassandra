package fr.xebia.training.domain.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Map;
import java.util.Set;

public class User {

  @NotEmpty
  @Email
  private String username;

  @NotEmpty
  private String firstname;

  @NotEmpty
  private String lastname;

  @NotEmpty
  private String password;

  private Set<String> smartphonesId;

  @NotEmpty
  private Map<String,Address> addresses;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getSmartphonesId() {
    return smartphonesId;
  }

  public void setSmartphonesId(Set<String> smartphonesId) {
    this.smartphonesId = smartphonesId;
  }

  public Map<String, Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(
      Map<String, Address> adresses) {
    this.addresses = adresses;
  }

  @Override
  public String toString() {
    return "User{" +
           "username='" + username + '\'' +
           ", firstname='" + firstname + '\'' +
           ", lastname='" + lastname + '\'' +
           ", password='" + password + '\'' +
           ", smartphonesId=" + smartphonesId +
           ", addresses=" + addresses +
           '}';
  }

  public static class Builder {

    private User tmp = new User();

    public Builder username(String username){
      tmp.username = username;
      return this;
    }

    public Builder firstname(String firstname){
      tmp.firstname = firstname;
      return this;
    }

    public Builder lastname(String lastname){
      tmp.lastname = lastname;
      return this;
    }

    public Builder password(String password){
      tmp.password = password;
      return this;
    }

    public Builder smartphonesId(Set<String> smartphonesId){
      tmp.smartphonesId = smartphonesId;
      return this;
    }

    public Builder addresses(Map<String,Address> addresses){
      tmp.addresses = addresses;
      return this;
    }

    public User build(){
      // TODO: validate tmp before returning
      return tmp;
    }
  }


}
