package fr.xebia.training.sprint3;

import org.junit.Test;

import java.util.List;
import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.ResultPage;
import fr.xebia.training.domain.model.Smartphone;
import fr.xebia.training.repository.DataRepository;
import fr.xebia.training.repository.SmartphonesRepository;

import static fr.xebia.training.ResourcesBuilder.createListData;
import static fr.xebia.training.ResourcesBuilder.createSmartphone;
import static org.assertj.core.api.Assertions.assertThat;

public class US16 extends BaseTest {

  private static final String CQL_US01 = "cql/US01.cql";
  private static final String CQL_US04 = "cql/US04.cql";
  private static final String CQL_US08 = "cql/US08.cql";
  private static final String CQL_US15 = "cql/US15.cql";
  private static final String CQL_US16 = "cql/US16.cql";

  private SmartphonesRepository smartphonesRepository;
  private DataRepository dataRepository;


  @Test
  public void testReadAllCounters() {
    //GIVEN
    loadCQLs();
    smartphonesRepository = new SmartphonesRepository(session);
    dataRepository = new DataRepository(session);
    List<Smartphone> smartphones = createSmartphone(2);
    smartphones.forEach(smartphone -> smartphonesRepository.create(smartphone));
    UUID smartphone1Id = smartphones.get(0).getId();
    UUID smartphone2Id = smartphones.get(1).getId();
    List<Data> dataSmartphone1 = createListData(smartphone1Id, 4);
    dataRepository.insert(dataSmartphone1);
    List<Data> dataSmartphone2 = createListData(smartphone2Id, 12);
    dataRepository.insert(dataSmartphone2);

    //WHEN
    ResultPage<Smartphone> resultPage = smartphonesRepository.readAll(null);

    //THEN
    assertThat(resultPage).isNotNull();
    assertThat(resultPage.getPagingState()).isNullOrEmpty();
    assertThat(resultPage.getResults()).isNotEmpty().hasSize(2);
    Smartphone firstSmartphone = resultPage.getResults().get(0);
    Smartphone secondSmartphone = resultPage.getResults().get(1);

    if (firstSmartphone.getId().equals(smartphone1Id)) {
      assertThat(firstSmartphone.getDataCount()).isEqualTo(4);
      assertThat(secondSmartphone.getDataCount()).isEqualTo(12);
    } else {
      assertThat(firstSmartphone.getDataCount()).isEqualTo(12);
      assertThat(secondSmartphone.getDataCount()).isEqualTo(4);
    }
  }

  private void loadCQLs() {
    loadCQL(CQL_US01);
    loadCQL(CQL_US04, false, false);
    loadCQL(CQL_US08, false, false);
    loadCQL(CQL_US15, false, false);
    loadCQL(CQL_US16, false, false);
  }

}
