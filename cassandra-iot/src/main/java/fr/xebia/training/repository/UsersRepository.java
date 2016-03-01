package fr.xebia.training.repository;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.xebia.training.domain.exceptions.ConflictException;
import fr.xebia.training.domain.exceptions.NotFoundException;
import fr.xebia.training.domain.model.Address;
import fr.xebia.training.domain.model.User;

@Repository
public class UsersRepository {

  public static final String INSERT_USER =
      "INSERT INTO users (username, firstname, lastname, pass, smartphones, addresses) "
      + "VALUES (?, ?, ?, ?, ?, ?) IF NOT EXISTS;";
  public static final String SELECT_USER = "SELECT * FROM users WHERE username=?;";
  public static final String DELETE_USER = "DELETE FROM users WHERE username=?;";
  public static final String UPDATE_USER =
      "UPDATE users SET firstname = ?, lastname = ?, pass = ?, smartphones = ?, addresses = ? "
      + "WHERE username = ?;";
  public static final String ADD_SMARTPHONE =
      "UPDATE users SET smartphones = smartphones + ? WHERE username = ?;";

  private Session session;

  private PreparedStatement insertUserStmt;
  private PreparedStatement selectUserStmt;
  private PreparedStatement deleteUserStmt;
  private PreparedStatement updateUserStmt;
  private PreparedStatement addSmartphoneStmt;

  @Autowired
  public UsersRepository(Session session) {
    this.session = session;
    prepareStatements();
  }

  private void prepareStatements() {
    insertUserStmt = session.prepare(INSERT_USER);
    selectUserStmt = session.prepare(SELECT_USER);
    deleteUserStmt = session.prepare(DELETE_USER);
    updateUserStmt = session.prepare(UPDATE_USER);
    addSmartphoneStmt = session.prepare(ADD_SMARTPHONE);
  }

  public User insert(User user) {
    // US03: insertion d'un utilisateur
    ResultSet result = session.execute(insertUserStmt.bind(user.getUsername(),
                                                           user.getFirstname(),
                                                           user.getLastname(),
                                                           user.getPassword(),
                                                           user.getSmartphonesId(),
                                                           userToUDTValueMap(user)));
    //US10 : interdire la création d'un utilisateur si celui-ci existe déjà
    if(!result.wasApplied()){
      throw new ConflictException();
    }

    return user;
  }

  private Map<String, UDTValue> userToUDTValueMap(User user) {
    UserType addressUDT = session.getCluster().getMetadata()
        .getKeyspace(session.getLoggedKeyspace())
        .getUserType("address");

    Map<String,UDTValue> udtValueMap = new HashMap<>();
    user.getAddresses().forEach( (k, v) -> {
      UDTValue address = addressUDT.newValue()
          .setString("street",v.getStreet())
          .setString("city", v.getCity())
          .setInt("zip", v.getZipCode());
      udtValueMap.put(k,address);
    });
    return udtValueMap;
  }

  public User read(String username) {
    // US03: lecture d'un utilisateur
    Row row = session.execute(selectUserStmt.bind(username)).one();
    if(row == null){
      throw new NotFoundException();
    }

    Map<String, UDTValue> udtValueMap =  row.getMap("addresses", String.class, UDTValue.class);
    Map<String, Address> addresses = UDTValueMapToAddresses(udtValueMap);

    return new User.Builder()
        .username(row.getString("username"))
        .firstname(row.getString("firstname"))
        .lastname(row.getString("lastname"))
        .password(row.getString("pass"))
        .smartphonesId(row.getMap("smartphones", UUID.class, String.class))
        .addresses(addresses)
        .build();
  }

  private Map<String, Address> UDTValueMapToAddresses(Map<String, UDTValue> udtValueMap) {
    Map<String, Address> addresses = new HashMap<>();
    udtValueMap.forEach((k, v) -> {
      Address address = new Address.Builder()
          .street(v.getString("street"))
          .city(v.getString("city"))
          .zipCode(v.getInt("zip"))
          .build();
      addresses.put(k,address);
    });
    return addresses;
  }

  public void delete(String username) {
    // US03: suppression d'un utilisateur
    session.execute(deleteUserStmt.bind(username));
  }

  public void update(String username, User user) {
    // US03: mise à jour d'un utilisateur
    session.execute(updateUserStmt.bind(user.getFirstname(),
                                        user.getLastname(),
                                        user.getPassword(),
                                        user.getSmartphonesId(),
                                        userToUDTValueMap(user),
                                        username));
  }

  public void addSmartphone(String owner, UUID smartphoneId, String smartphoneName) {
    //US09 : ajout d'un smartphone à l'utilisateur
    //US11 : ajout du nom du smartphone en plus de l'id
    Map<UUID,String> toAdd = new HashMap<>();
    toAdd.put(smartphoneId, smartphoneName);
    session.execute(addSmartphoneStmt.bind(toAdd, owner));
  }
}
