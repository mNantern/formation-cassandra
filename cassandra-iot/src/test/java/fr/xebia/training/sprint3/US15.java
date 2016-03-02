package fr.xebia.training.sprint3;

import org.junit.Test;

import java.util.List;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.Smartphone;
import fr.xebia.training.repository.SmartphonesRepository;

import static fr.xebia.training.ResourcesBuilder.createSmartphone;
import static org.assertj.core.api.Assertions.assertThat;

public class US15 extends BaseTest {

  private static final String CQL_US04 = "cql/US04.cql";
  private static final String CQL_US08 = "cql/US08.cql";
  private static final String CQL_US15 = "cql/US15.cql";
  private static final String CQL_US16 = "cql/US16.cql";

  private SmartphonesRepository smartphonesRepository;

  @Test
  public void testReadByConstructor() {
    //GIVEN
    loadCQLs();
    smartphonesRepository = new SmartphonesRepository(session);
    insertSmartphones(5);

    //WHEN
    List<Smartphone> smartphonesSamsung = smartphonesRepository.readByConstructor("SAMSUNG");
    List<Smartphone> smartphonesApple = smartphonesRepository.readByConstructor("APPLE");

    //THEN
    assertThat(smartphonesSamsung).isNotEmpty().hasSize(5);
    assertThat(smartphonesSamsung).extracting("constructor").containsOnly("SAMSUNG");
    assertThat(smartphonesApple).isNullOrEmpty();
  }


  private void insertSmartphones(int nbSmartphone) {
    List<Smartphone> smartphones = createSmartphone(nbSmartphone);
    smartphones.stream().forEach(smartphone -> {
      smartphonesRepository.create(smartphone);
    });
  }

  private void loadCQLs() {
    loadCQL(CQL_US04);
    loadCQL(CQL_US08, false, false);
    loadCQL(CQL_US15, false, false);
    loadCQL(CQL_US16, false, false);
  }

}
