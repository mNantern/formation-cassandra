package fr.xebia.training.sprint2;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.Data;
import fr.xebia.training.repository.DataRepository;

import static fr.xebia.training.ResourcesBuilder.createListData;
import static org.assertj.core.api.Assertions.assertThat;

public class US07 extends BaseTest {

  private static final String CQL_US01 = "cql/US01.cql";
  private static final String CQL_US16 = "cql/US16.cql";
  private static DataRepository dataRepository;
  private static UUID smartphoneId;

  @Before
  public void setup() {
    loadCQL(CQL_US01);
    loadCQL(CQL_US16, false, false);
    dataRepository = new DataRepository(session);
    smartphoneId = UUID.randomUUID();
    List<Data> input = createListData(smartphoneId);
    dataRepository.insert(input);
  }

  @Test
  public void testGetDataOrder() {
    //GIVEN

    //WHEN
    List<Data> results = dataRepository.getBySmartphoneId(smartphoneId, null).getResults();

    //THEN
    assertThat(results).extracting("eventTime").containsExactly(
        Instant.parse("2016-02-04T12:00:00Z"),
        Instant.parse("2016-02-03T17:00:00Z"));
  }

  @Test
  public void testGetDataStartDateOrder() {
    //GIVEN
    Instant startDate = Instant.parse("2010-02-04T12:00:00Z");

    //WHEN
    List<Data> results = dataRepository
        .getBySmartphoneIdStartDate(smartphoneId, startDate, null)
        .getResults();

    //THEN
    assertThat(results).extracting("eventTime").containsExactly(
        Instant.parse("2016-02-04T12:00:00Z"),
        Instant.parse("2016-02-03T17:00:00Z"));
  }

  @Test
  public void testGetDataEndDateOrder() {
    //GIVEN
    Instant endDate = Instant.parse("2020-02-04T12:00:00Z");

    //WHEN
    List<Data> results = dataRepository
        .getBySmartphoneIdEndDate(smartphoneId, endDate, null)
        .getResults();

    //THEN
    assertThat(results).extracting("eventTime").containsExactly(
        Instant.parse("2016-02-04T12:00:00Z"),
        Instant.parse("2016-02-03T17:00:00Z"));
  }

  @Test
  public void testGetDataStartEndDateOrder() {
    //GIVEN
    Instant startDate = Instant.parse("2010-02-04T12:00:00Z");
    Instant endDate = Instant.parse("2020-02-04T12:00:00Z");

    //WHEN
    List<Data> results = dataRepository
        .getBySmartphoneIdStartEndDate(smartphoneId, startDate, endDate, null)
        .getResults();

    //THEN
    assertThat(results).extracting("eventTime").containsExactly(
        Instant.parse("2016-02-04T12:00:00Z"),
        Instant.parse("2016-02-03T17:00:00Z"));
  }

}
