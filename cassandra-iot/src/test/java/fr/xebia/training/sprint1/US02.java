package fr.xebia.training.sprint1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.Type;
import fr.xebia.training.repository.DataRepository;

import static fr.xebia.training.ResourcesBuilder.createListData;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class US02 extends BaseTest {

  public static final String CQL_US02 = "cql/US01.cql";

  @Test
  public void testEmptyTable() {
    //GIVEN
    loadCQL(CQL_US02);
    DataRepository dataRepository = new DataRepository(session);

    //WHEN
    Collection<Data> results = dataRepository.getBySmartphoneId(UUID.randomUUID());

    //THEN
    assertThat(results).isEmpty();
  }

  @Test
  public void testGetBySmartphoneId() {
    //GIVEN
    loadCQL(CQL_US02);
    DataRepository dataRepository = new DataRepository(session);
    UUID smartphoneId = UUID.randomUUID();
    dataRepository.insert(createListData(smartphoneId));

    //WHEN
    List<Data> results = new ArrayList<>(dataRepository.getBySmartphoneId(smartphoneId));

    //THEN
    assertThat(results.size()).isEqualTo(2);
    assertThat(results).extracting("type", "value", "smartphoneId")
        .contains(tuple(Type.BRIGHTNESS, "34", smartphoneId),
                  tuple(Type.ACCELEROMETER, "0.0392266;2.9812214;9.610517", smartphoneId));
  }
}
