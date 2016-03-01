package fr.xebia.training.sprint2;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.Type;
import fr.xebia.training.repository.DataRepository;

import static fr.xebia.training.ResourcesBuilder.createListData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class US06 extends BaseTest {


  private static final String CQL_US01 = "cql/US01.cql";
  private static DataRepository dataRepository;
  private static UUID smartphoneId;

  @Before
  public void setup() {
    loadCQL(CQL_US01);
    dataRepository = new DataRepository(session);
    smartphoneId = UUID.randomUUID();
    List<Data> input = createListData(smartphoneId);
    dataRepository.insert(input);
  }

  @Test
  public void testSearchWithStartDate() {
    //GIVEN
    //startDate est exclue
    Instant startDate = Instant.parse("2016-02-03T17:00:00.000Z");

    //WHEN
    List<Data> dataList = dataRepository.getBySmartphoneIdStartDate(smartphoneId, startDate);

    //THEN
    assertThat(dataList.size()).isEqualTo(1);
    assertThat(dataList).extracting("smartphoneId", "type", "eventTime", "value")
        .containsExactly(tuple(smartphoneId, Type.BRIGHTNESS,
                               Instant.parse("2016-02-04T12:00:00.000Z"),
                               "34"));
  }

  @Test
  public void testSearchWithEndDate() {
    //GIVEN
    //endDate est inclue
    Instant endDate = Instant.parse("2016-02-04T12:00:00.000Z");

    //WHEN
    List<Data> dataList = dataRepository.getBySmartphoneIdEndDate(smartphoneId, endDate);

    //THEN
    assertThat(dataList.size()).isEqualTo(2);
    assertThat(dataList).extracting("smartphoneId", "type", "eventTime", "value")
        .containsOnly(tuple(smartphoneId, Type.BRIGHTNESS,
                            Instant.parse("2016-02-04T12:00:00.000Z"),
                            "34"),
                      tuple(smartphoneId, Type.ACCELEROMETER,
                            Instant.parse("2016-02-03T17:00:00.000Z"),
                            "0.0392266;2.9812214;9.610517")
        );
  }

  @Test
  public void testSearchWithStartEndDate() {
    //GIVEN
    Instant startDate = Instant.parse("2016-02-03T17:00:00.000Z");
    Instant endDate = Instant.parse("2016-02-04T12:00:00.000Z");

    //WHEN
    List<Data> dataList = dataRepository.getBySmartphoneIdStartEndDate(smartphoneId, startDate,
                                                                       endDate);

    //THEN
    assertThat(dataList.size()).isEqualTo(1);
    assertThat(dataList).extracting("smartphoneId", "type", "eventTime", "value")
        .containsExactly(tuple(smartphoneId, Type.BRIGHTNESS,
                               Instant.parse("2016-02-04T12:00:00.000Z"),
                               "34"));
  }

  @Test
  public void testSearchWithStartEndDateNoResult() {
    //GIVEN
    Instant startDate = Instant.parse("2015-02-03T17:00:00.000Z");
    Instant endDate = Instant.parse("2015-02-04T12:00:00.000Z");

    //WHEN
    List<Data> dataList = dataRepository.getBySmartphoneIdStartEndDate(smartphoneId,
                                                                       startDate, endDate);

    //THEN
    assertThat(dataList.size()).isEqualTo(0);
  }


}
