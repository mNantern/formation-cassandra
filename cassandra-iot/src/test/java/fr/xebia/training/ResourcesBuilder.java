package fr.xebia.training;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import fr.xebia.training.domain.model.Address;
import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.Smartphone;
import fr.xebia.training.domain.model.Type;
import fr.xebia.training.domain.model.User;

public class ResourcesBuilder {

  public static List<Data> createListData(UUID smartphoneId) {
    List<Data> input = new ArrayList<>();

    Data data1 = new Data.Builder()
        .id(UUID.randomUUID())
        .eventTime(Instant.parse("2016-02-04T12:00:00.000Z"))
        .smartphoneId(smartphoneId)
        .type(Type.BRIGHTNESS)
        .value("34")
        .build();

    Data data2 = new Data.Builder()
        .id(UUID.randomUUID())
        .eventTime(Instant.parse("2016-02-03T17:00:00.000Z"))
        .smartphoneId(smartphoneId)
        .type(Type.ACCELEROMETER)
        .value("0.0392266;2.9812214;9.610517")
        .build();

    input.add(data1);
    input.add(data2);

    return input;
  }

  public static List<Data> createListData(UUID smartphoneId, int listSize) {
    List<Data> input = new ArrayList<>();
    Random random = new Random();
    Instant startDate = Instant.parse("2016-02-04T12:00:00.000Z");

    for (int i = 0; i < listSize; i++) {
      startDate = startDate.plusSeconds(60);
      String value = String.valueOf(random.nextInt(100));

      Data data = new Data.Builder()
          .id(UUID.randomUUID())
          .eventTime(startDate)
          .smartphoneId(smartphoneId)
          .type(Type.BRIGHTNESS)
          .value(value)
          .build();

      input.add(data);
    }

    return input;
  }

  public static User createUser(String username, UUID smartphoneId) {

    Map<UUID, String> smartphonesId = new HashMap<>();
    smartphonesId.put(smartphoneId, "MySmartphone");

    Map<String, Address> addressesMap = new HashMap<>();
    addressesMap.put("Xebia", createAddress());

    return new User.Builder()
        .firstname("integration")
        .lastname("test")
        .password("s3cr3t")
        .username(username)
        .smartphonesId(smartphonesId)
        .addresses(addressesMap)
        .build();
  }

  private static Address createAddress() {
    return new Address.Builder()
        .city("PARIS")
        .zipCode(75008)
        .street("156, boulevard Haussmann")
        .build();
  }

  public static Smartphone createSmartphone(UUID id) {
    Smartphone smartphone = new Smartphone();
    smartphone.setId(id);
    smartphone.setName("MySmartphone");
    smartphone.setConstructor("SAMSUNG");
    smartphone.setModel("GALAXY S6");
    smartphone.setOwner("jdoe@gmail.com");
    return smartphone;
  }

  public static List<Smartphone> createSmartphone(int listSize) {
    List<Smartphone> smartphones = new ArrayList<>();
    String name = "MySmartphone";
    String constructor = "SAMSUNG";
    String model = "GALAXY S6";
    String owner = "jdoe@gmail.com";

    for (int i = 0; i < listSize; i++) {
      Smartphone smartphone = new Smartphone();
      smartphone.setId(UUID.randomUUID());
      smartphone.setName(name + i);
      smartphone.setConstructor(constructor);
      smartphone.setModel(model);
      smartphone.setOwner(owner);

      smartphones.add(smartphone);
    }

    return smartphones;
  }

}