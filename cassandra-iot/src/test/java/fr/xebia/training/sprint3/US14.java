package fr.xebia.training.sprint3;

import org.junit.Test;

import java.util.List;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.ResultPage;
import fr.xebia.training.domain.model.Smartphone;
import fr.xebia.training.repository.SmartphonesRepository;

import static fr.xebia.training.ResourcesBuilder.createSmartphone;
import static org.assertj.core.api.Assertions.assertThat;

public class US14 extends BaseTest {

  private static final String CQL_US04 = "cql/US04.cql";
  private static final String CQL_US08 = "cql/US08.cql";
  private static final String CQL_US15 = "cql/US15.cql";
  private static final String CQL_US16 = "cql/US16.cql";

  private SmartphonesRepository smartphonesRepository;

  @Test
  public void testReadAll() {
    //GIVEN
    loadCQLs();
    smartphonesRepository = new SmartphonesRepository(session);
    insertSmartphones(25);

    //WHEN
    ResultPage<Smartphone> page1 = smartphonesRepository.readAll(null);
    ResultPage<Smartphone> page2 = smartphonesRepository.readAll(page1.getPagingState());

    //THEN
    assertThat(page1).isNotNull();
    assertThat(page1.getPagingState()).isNotEmpty();
    assertThat(page1.getResults()).isNotEmpty().hasSize(20);
    assertThat(page2).isNotNull();
    assertThat(page2.getPagingState()).isNullOrEmpty();
    assertThat(page2.getResults()).isNotEmpty().hasSize(5);
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
