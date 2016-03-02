package fr.xebia.training.sprint3;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.ResultPage;
import fr.xebia.training.repository.DataRepository;

import static fr.xebia.training.ResourcesBuilder.createListData;
import static org.assertj.core.api.Assertions.assertThat;

public class US12 extends BaseTest {

  private static final String CQL_US01 = "cql/US01.cql";
  private static final String CQL_US16 = "cql/US16.cql";
  public static final int LIST_SIZE = 25;
  private static DataRepository dataRepository;
  private static UUID smartphoneId;

  @Before
  public void setup() {
    loadCQL(CQL_US01);
    loadCQL(CQL_US16, false, false);
    dataRepository = new DataRepository(session);
    smartphoneId = UUID.randomUUID();
    List<Data> input = createListData(smartphoneId, LIST_SIZE);
    dataRepository.insert(input);
  }


  @Test
  public void testDataBySmartphoneId() {
    //GIVEN

    //WHEN
    ResultPage<Data> resultPage1 = dataRepository.getBySmartphoneId(smartphoneId, null);
    ResultPage<Data> resultPage2 = dataRepository.getBySmartphoneId(smartphoneId,
                                                                    resultPage1.getPagingState());

    //THEN
    assertThat(resultPage1).isNotNull();
    assertThat(resultPage1.getPagingState()).isNotEmpty();
    assertThat(resultPage1.getResults()).isNotEmpty().hasSize(DataRepository.FETCH_SIZE);
    assertThat(resultPage2).isNotNull();
    assertThat(resultPage2.getPagingState()).isNullOrEmpty();
    assertThat(resultPage2.getResults()).isNotEmpty()
        .hasSize(LIST_SIZE - DataRepository.FETCH_SIZE);
  }

  @Test
  public void testDataBySmartphoneIdAndStartDate() {
    //GIVEN
    Instant startDate = Instant.parse("2010-02-04T12:00:00.000Z");

    //WHEN
    ResultPage<Data> resultPage1 = dataRepository.getBySmartphoneIdStartDate(smartphoneId,
                                                                             startDate,
                                                                             null);
    ResultPage<Data> resultPage2 = dataRepository
        .getBySmartphoneIdStartDate(smartphoneId, startDate, resultPage1.getPagingState());

    //THEN
    assertThat(resultPage1).isNotNull();
    assertThat(resultPage1.getPagingState()).isNotEmpty();
    assertThat(resultPage1.getResults()).isNotEmpty().hasSize(DataRepository.FETCH_SIZE);
    assertThat(resultPage2).isNotNull();
    assertThat(resultPage2.getPagingState()).isNullOrEmpty();
    assertThat(resultPage2.getResults()).isNotEmpty()
        .hasSize(LIST_SIZE - DataRepository.FETCH_SIZE);
  }

  @Test
  public void testDataBySmartphoneIdAndEndDate() {
    //GIVEN
    Instant endDate = Instant.parse("2020-02-04T12:00:00.000Z");

    //WHEN
    ResultPage<Data> resultPage1 = dataRepository.getBySmartphoneIdEndDate(smartphoneId,
                                                                           endDate,
                                                                           null);
    ResultPage<Data> resultPage2 = dataRepository
        .getBySmartphoneIdEndDate(smartphoneId, endDate, resultPage1.getPagingState());

    //THEN
    assertThat(resultPage1).isNotNull();
    assertThat(resultPage1.getPagingState()).isNotEmpty();
    assertThat(resultPage1.getResults()).isNotEmpty().hasSize(DataRepository.FETCH_SIZE);
    assertThat(resultPage2).isNotNull();
    assertThat(resultPage2.getPagingState()).isNullOrEmpty();
    assertThat(resultPage2.getResults()).isNotEmpty()
        .hasSize(LIST_SIZE - DataRepository.FETCH_SIZE);
  }

  @Test
  public void testDataBySmartphoneIdAndStartDateAndEndDate() {
    //GIVEN
    Instant startDate = Instant.parse("2010-02-04T12:00:00.000Z");
    Instant endDate = Instant.parse("2020-02-04T12:00:00.000Z");

    //WHEN
    ResultPage<Data> resultPage1 = dataRepository.getBySmartphoneIdStartEndDate(smartphoneId,
                                                                                startDate,
                                                                                endDate,
                                                                                null);
    ResultPage<Data> resultPage2 = dataRepository
        .getBySmartphoneIdStartEndDate(smartphoneId, startDate, endDate,
                                       resultPage1.getPagingState());

    //THEN
    assertThat(resultPage1).isNotNull();
    assertThat(resultPage1.getPagingState()).isNotEmpty();
    assertThat(resultPage1.getResults()).isNotEmpty().hasSize(DataRepository.FETCH_SIZE);
    assertThat(resultPage2).isNotNull();
    assertThat(resultPage2.getPagingState()).isNullOrEmpty();
    assertThat(resultPage2.getResults()).isNotEmpty()
        .hasSize(LIST_SIZE - DataRepository.FETCH_SIZE);
  }
}
